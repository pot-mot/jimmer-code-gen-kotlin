package top.potmot.core.entity.convert

import top.potmot.core.entity.convert.association.convertAssociationProperties
import top.potmot.core.entity.convert.association.handleDuplicateName
import top.potmot.core.entity.convert.base.TypeMapping
import top.potmot.core.entity.convert.base.convertBaseProperties
import top.potmot.core.entity.convert.base.tableToEntity
import top.potmot.core.entity.convert.business.initPropertyBusinessConfig
import top.potmot.core.entity.convert.merge.mergeExistAndConvertEntity
import top.potmot.core.entity.convert.type.getPropertyType
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException

data class ConvertResult (
    val insertEntities: List<GenEntityInput>,
    val updateEntities: List<GenEntity>
)

fun convertTableToEntities(
    modelId: Long,
    tables: List<GenTableConvertView>,
    tableIdEntityMap: Map<Long, GenEntityDetailView>,
    typeMappings: List<GenTypeMappingView>,
): ConvertResult {
    val insertEntities = mutableListOf<GenEntityInput>()
    val updateEntities = mutableListOf<GenEntity>()

    tables.forEach { table ->
        val existEntity = tableIdEntityMap[table.id]
        val convertEntity = table.toGenEntity(modelId, typeMappings)

        if (existEntity == null) {
            insertEntities += convertEntity
        } else {
            updateEntities += mergeExistAndConvertEntity(existEntity, convertEntity)
        }
    }

    return ConvertResult(
        insertEntities,
        updateEntities
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
 *  initPropertyBusinessConfig -> List<Property>
 *      初始化属性的业务配置
 *
 * 最终将 associationProperty 中的数据填充到 baseEntity 中
 */
@Throws(ConvertException::class, ColumnTypeException::class)
fun GenTableConvertView.toGenEntity(
    modelId: Long?,
    typeMappings: List<GenTypeMappingView>,
): GenEntityInput {
    val baseEntity = tableToEntity(this, modelId)

    val typeMapping: TypeMapping = { typeMeta -> getPropertyType(typeMeta, typeMappings) }

    val basePropertyMap = convertBaseProperties(
        this,
        typeMapping,
    )

    val propertiesMap = convertAssociationProperties(
        this,
        basePropertyMap,
        typeMapping
    )

    val associationProperties = handleDuplicateName(
        this,
        propertiesMap
    )

    val businessConfigInitProperties = initPropertyBusinessConfig(
        this,
        associationProperties
    )

    return baseEntity.copy(
        properties = businessConfigInitProperties.mapIndexed { index, property ->
            property.copy(
                orderKey = index.toLong()
            )
        }
    )
}
