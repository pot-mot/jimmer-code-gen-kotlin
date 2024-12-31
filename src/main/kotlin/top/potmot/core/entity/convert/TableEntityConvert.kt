package top.potmot.core.entity.convert

import top.potmot.context.getContextOrGlobal
import top.potmot.core.entity.convert.association.convertAssociationProperties
import top.potmot.core.entity.convert.association.handleDuplicateName
import top.potmot.core.entity.convert.base.convertBaseProperties
import top.potmot.core.entity.convert.business.initPropertyBusinessConfig
import top.potmot.core.entity.convert.type.getPropertyType
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException
import top.potmot.entity.dto.share.ColumnTypeMeta
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.tableNameToEntityName

typealias TypeMapping = (column: ColumnTypeMeta) -> String

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
        properties = businessConfigInitProperties.setOrderKey()
    )
}

/**
 * 表到实体转换
 */
private fun tableToEntity(
    genTable: GenTableConvertView,
    modelId: Long?,
): GenEntityInput {
    val context = getContextOrGlobal()

    return GenEntityInput(
        tableId = genTable.id,
        modelId = modelId,
        author = context.author,
        name = tableNameToEntityName(genTable.name),
        comment = genTable.comment.clearTableComment(),
        remark = genTable.remark,
        packagePath = "${context.packagePath}.entity",
        superEntityIds = emptyList(),
        properties = emptyList(),
        canAdd = true,
        canQuery = true,
        canEdit = true,
        canDelete = true,
        hasPage = true,
    )
}

private fun List<GenPropertyInput>.setOrderKey() =
    mapIndexed { index, property ->
        property.copy(
            orderKey = index.toLong()
        )
    }