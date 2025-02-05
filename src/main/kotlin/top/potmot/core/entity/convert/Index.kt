package top.potmot.core.entity.convert

import top.potmot.core.entity.convert.association.aggregateOtherSideLeafTableAssociations
import top.potmot.core.entity.convert.association.convertAssociationProperties
import top.potmot.core.entity.convert.association.handleDuplicateName
import top.potmot.core.entity.convert.association.reverseOneToMany
import top.potmot.core.entity.convert.association.reverseReversedOneToOne
import top.potmot.core.entity.convert.base.TypeMapping
import top.potmot.core.entity.convert.base.convertBaseProperties
import top.potmot.core.entity.convert.base.tableToEntity
import top.potmot.core.entity.convert.merge.AssociationPropertyPairWaitMergeExist
import top.potmot.core.entity.convert.merge.mergeExistAndConvertEntity
import top.potmot.core.entity.convert.type.getPropertyType
import top.potmot.core.entity.meta.TableAssociationMeta
import top.potmot.core.entity.meta.getAssociationMeta
import top.potmot.core.entity.meta.toAssociationMetaIdMap
import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException

data class ConvertResult(
    val insertEntities: List<GenEntityInput>,
    val updateEntities: List<GenEntityInput>,
)

fun convertTableToEntities(
    modelId: Long,
    tableIdMap: Map<Long, GenTableConvertView>,
    columnIdMap: Map<Long, GenTableConvertView.TargetOf_columns>,
    associations: Collection<GenAssociationConvertView>,
    tableIdEntityMap: Map<Long, GenEntityDetailView>,
    typeMappings: Collection<GenTypeMappingView>,
): ConvertResult {
    val insertEntities = mutableListOf<GenEntityInput>()
    val updateEntities = mutableListOf<GenEntityInput>()

    val associationMetaIdMap = associations.toAssociationMetaIdMap(
        tableIdMap, columnIdMap, tableIdEntityMap,
    )

    // 记录在"合并已存在的数据"时变化的关联属性名称
    val mergeExistChangedAssociationPropertyNameMap = mutableMapOf<String, String>()

    tableIdMap.values.forEach { table ->
        val associationMeta = table
            .getAssociationMeta(associationMetaIdMap)
            .reverseOneToMany()
            .reverseReversedOneToOne()
            .aggregateOtherSideLeafTableAssociations(tableIdEntityMap)

        val existEntity = tableIdEntityMap[table.id]

        val convertEntity = table.toGenEntityInput(
            modelId,
            typeMappings,
            associationMeta,
            existEntity,
            mergeExistChangedAssociationPropertyNameMap,
        )

        if (existEntity != null) {
            updateEntities += convertEntity
        } else {
            insertEntities += convertEntity
        }
    }

    return ConvertResult(
        produceAssociationPropertyNameChange(insertEntities, mergeExistChangedAssociationPropertyNameMap),
        produceAssociationPropertyNameChange(updateEntities, mergeExistChangedAssociationPropertyNameMap)
    )
}

// 根据 在"合并已存在的数据"时变化的关联属性名称，处理实体中的一些属性
private fun produceAssociationPropertyNameChange(
    entities: Collection<GenEntityInput>,
    mergeExistChangedAssociationPropertyNameMap: Map<String, String>,
): List<GenEntityInput> =
    entities.map { entity ->
        entity.copy(
            properties = entity.properties.map { property ->
                val changedPropertyName =
                    mergeExistChangedAssociationPropertyNameMap["${property.typeTableId} ${property.mappedBy}"]
                if (changedPropertyName != null) {
                    property.copy(mappedBy = changedPropertyName)
                } else {
                    property
                }
            }
        )
    }

/**
 * 转换 table 为 entity
 *
 * 流程如下：
 *  tableToEntity -> baseEntity
 *      映射基本实体信息
 *  convertBaseProperties -> Map<columnId, BaseProperty>
 *      将列映射成基础属性
 *  convertAssociationProperties -> Map<columnId, AssociationPropertyMeta>
 *      基于列和基础属性转换出关联属性，并存储在List中
 *  handleDuplicateName -> List<Property>
 *      处理属性重名
 *
 * 最终将 associationProperty 中的数据填充到 baseEntity 中
 */
@Throws(ConvertException::class, ColumnTypeException::class)
private fun GenTableConvertView.toGenEntityInput(
    modelId: Long?,
    typeMappings: Collection<GenTypeMappingView>,
    associationMeta: TableAssociationMeta,
    existEntity: GenEntityDetailView?,
    mergeExistChangedAssociationPropertyNameMap: MutableMap<String, String>,
): GenEntityInput {
    val baseEntity = tableToEntity(this, modelId).let {
        if (existEntity != null) {
            GenEntityInput(mergeExistAndConvertEntity(existEntity, it))
        } else {
            it
        }
    }

    val typeMapping: TypeMapping = { typeMeta -> getPropertyType(typeMeta, typeMappings) }

    val basePropertyMap = convertBaseProperties(
        this,
        existEntity,
        typeMapping,
    )

    val propertiesMap = convertAssociationProperties(
        basePropertyMap,
        typeMapping,
        associationMeta
    )

    propertiesMap.values.forEach {
        it.associationPropertyPairs.replaceAll { pair ->
            // 同步等待合并的属性名称，并记录这些名称以用作后续同步 mappedBy 等其他信息上
            if (pair is AssociationPropertyPairWaitMergeExist) {
                val mergedPair = pair.mergeExist()

                if (pair.associationProperty.name != mergedPair.associationProperty.name) {
                    mergeExistChangedAssociationPropertyNameMap["$id ${pair.associationProperty.name}"] =
                        mergedPair.associationProperty.name
                }

                val idView = pair.idView
                val mergedIdView = mergedPair.idView
                if (idView != null && mergedIdView != null && idView.name != mergedIdView.name) {
                    mergeExistChangedAssociationPropertyNameMap["$id ${idView.name}"] = mergedIdView.name
                }

                mergedPair
            } else {
                pair
            }
        }
    }

    val properties =
        handleDuplicateName(
            this,
            propertiesMap
        )
            // 将 list Properties 后置
            .let { properties ->
                val (listProperties, notListProperties) = properties.partition { it.listType }
                notListProperties + listProperties
            }

    val propertyNames = properties.map { it.name }

    val sortKeyProperties = properties.mapIndexed { index, property ->
        property.copy(
            orderKey = index.toLong()
        )
    }
    val minOrderKey = 0
    val maxOrderKey = properties.size - 1

    val unMergeProperties = existEntity?.properties
        ?.filter {
            it.associationType == null && it.columnId == null && it.name !in propertyNames
        }
        ?.sortedBy { it.orderKey }
        ?.mapIndexed { index, it ->
            // 处理这些 unMergeProperties 的 orderKey，使它们不和 mergedProperties 冲突
            GenEntityInput.TargetOf_properties(it.toEntity {
                typeTable = null
                if (orderKey >= minOrderKey) {
                    orderKey = (maxOrderKey + index + 1).toLong()
                }
            })
        }

    return baseEntity.copy(
        properties = listOfNotNull(
            sortKeyProperties,
            unMergeProperties
        )
            .flatten()
            .sortedBy { it.orderKey }
    )
}
