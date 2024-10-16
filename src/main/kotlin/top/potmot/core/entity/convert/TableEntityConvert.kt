package top.potmot.core.entity.convert

import top.potmot.context.getContextOrGlobal
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.entity.dto.share.ColumnTypeMeta
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView

typealias TypeMapping = (column: ColumnTypeMeta) -> String

/**
 * 转换 table 为 entity
 *
 * 流程如下：
 *  tableToEntity(): baseEntity
 *      映射基本实体信息
 *  convertBaseProperties(): Map<columnId, BaseProperty>
 *      将列映射成基础属性
 *  convertAssociationProperties(): Map<columnId, AssociationPropertyMeta>
 *      基于列和基础属性转换出关联属性，并存储在List中
 *  handleDuplicateName(): List<Property>
 *      处理属性重名
 *  sortProperties(): List<Property>
 *      整理属性顺序
 *
 * 最终将 associationProperty 中的数据填充到 baseEntity 中
 */
@Throws(ConvertEntityException::class, ColumnTypeException::class)
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
        propertiesMap
    ).let {
        sortProperties(it)
    }

    return baseEntity.copy(
        properties = associationProperties
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
        name = snakeToUpperCamel(genTable.name),
        comment = genTable.comment.clearTableComment(),
        remark = genTable.remark,
        packagePath = context.packagePath,
        superEntityIds = emptyList(),
        properties = emptyList()
    )
}
