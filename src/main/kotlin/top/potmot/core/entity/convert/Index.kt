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
import top.potmot.core.entity.convert.merge.produceAssociationPropertyNameChange
import top.potmot.core.entity.convert.type.getPropertyType
import top.potmot.core.entity.meta.TableAssociationMeta
import top.potmot.core.entity.meta.getAssociationMeta
import top.potmot.core.entity.meta.toAssociationMetaIdMap
import top.potmot.entity.GenEntity
import top.potmot.entity.copy
import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException

data class ConvertResult(
    val insertEntities: List<GenEntity>,
    val updateEntities: List<GenEntity>,
)

fun convertTableToEntities(
    modelId: Long,
    tableIdMap: Map<Long, GenTableConvertView>,
    columnIdMap: Map<Long, GenTableConvertView.TargetOf_columns>,
    associations: Collection<GenAssociationConvertView>,
    tableIdEntityMap: Map<Long, GenEntityDetailView>,
    typeMappings: Collection<GenTypeMappingView>,
): ConvertResult {
    val insertEntities = mutableListOf<GenEntity>()
    val updateEntities = mutableListOf<GenEntity>()

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

        val convertEntity = table.toGenEntity(
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
        produceAssociationPropertyNameChange(updateEntities, mergeExistChangedAssociationPropertyNameMap),
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
private fun GenTableConvertView.toGenEntity(
    modelId: Long?,
    typeMappings: Collection<GenTypeMappingView>,
    associationMeta: TableAssociationMeta,
    existEntity: GenEntityDetailView?,
    mergeExistChangedAssociationPropertyNameMap: MutableMap<String, String>,
): GenEntity {
    val baseEntity = tableToEntity(this, modelId).let {
        if (existEntity != null) {
            mergeExistAndConvertEntity(existEntity, it)
        } else {
            it.toEntity()
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
            if (pair is AssociationPropertyPairWaitMergeExist) {
                val mergedPair = pair.mergeExist()

                if (pair.associationProperty.name != mergedPair.associationProperty.name) {
                    mergeExistChangedAssociationPropertyNameMap["$id ${pair.associationProperty.name}"] = mergedPair.associationProperty.name
                }
                if (pair.idView != null && mergedPair.idView != null && pair.idView!!.name != mergedPair.idView.name) {
                    mergeExistChangedAssociationPropertyNameMap["$id ${pair.idView!!.name}"] = mergedPair.idView.name
                }

                mergedPair
            } else {
                pair
            }
        }
    }

    val properties = handleDuplicateName(
        this,
        propertiesMap
    )

    val propertyNames = properties.map { it.name }

    val sortKeyProperties = properties.mapIndexed { index, property ->
        property.toEntity {
            orderKey = index.toLong()
        }
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
            it.toEntity {
                if (orderKey >= minOrderKey) {
                    orderKey = (maxOrderKey + index + 1).toLong()
                }
            }
        }

    return baseEntity.copy {
        this.properties = listOfNotNull(
            sortKeyProperties,
            unMergeProperties
        )
            .flatten()
            .sortedBy { it.orderKey }
    }
}
