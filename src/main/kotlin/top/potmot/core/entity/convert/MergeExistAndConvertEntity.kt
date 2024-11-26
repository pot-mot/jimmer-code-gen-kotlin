package top.potmot.core.entity.convert

import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenEntityInput

private val GenEntityDetailView.TargetOf_properties.associationMatchKey
    get() = "$columnId $typeTableId $associationType $idView"

private val GenEntityInput.TargetOf_properties.associationMatchKey
    get() = "$columnId $typeTableId $associationType $idView"

private fun GenEntityInput.TargetOf_properties.mergeExistProperty(
    existProperty: GenEntityDetailView.TargetOf_properties,
) = toEntity {
    id = existProperty.id
    if (existProperty.overwriteName) {
        name = existProperty.name
    }
    overwriteName = existProperty.overwriteName
    if (existProperty.overwriteComment) {
        comment = existProperty.comment
    }
    overwriteComment = existProperty.overwriteComment
    remark = existProperty.remark
    orderKey = existProperty.orderKey
    
    otherAnnotation =  existProperty.otherAnnotation

    longAssociation = existProperty.longAssociation

    inListView = existProperty.inListView
    inDetailView = existProperty.inDetailView
    inOptionView = existProperty.inOptionView
    inSpecification = existProperty.inSpecification
    inInsertInput = existProperty.inInsertInput
    inUpdateInput = existProperty.inUpdateInput
    inShortAssociationView = existProperty.inShortAssociationView
    inLongAssociationInput = existProperty.inLongAssociationInput
    inLongAssociationView = existProperty.inLongAssociationView
}

/**
 * 对已存在的实体和转换出的实体进行合并
 * 保留原实体的id，注释，备注，业务配置
 * 根据属性匹配旧属性，并保留旧属性的id，注释，备注，排序键，业务配置
 * 最后拼接 无法映射至当前属性 且 无对应列 的旧属性
 *
 * 匹配规则：
 * （关联属性）1. 对应表与关联类型
 * （普通属性）1. 列ID   2. 列名称
 */
fun mergeExistAndConvertEntity(
    existEntity: GenEntityDetailView,
    convertEntity: GenEntityInput,
): GenEntity =
    convertEntity.toEntity {
        id = existEntity.id
        if (existEntity.overwriteName) {
            name = existEntity.name
        }
        overwriteName = existEntity.overwriteName
        if (existEntity.overwriteComment) {
            comment = existEntity.comment
        }
        overwriteComment = existEntity.overwriteComment
        remark = existEntity.remark

        canAdd = existEntity.canAdd
        canEdit = existEntity.canEdit
        canQuery = existEntity.canQuery
        canDelete = existEntity.canDelete
        hasPage = existEntity.hasPage

        val associationMatchColumnMap =
            existEntity.properties.groupBy { it.associationMatchKey }

        val columnIdMatchColumnMap =
            existEntity.properties.groupBy { it.columnId }

        val nameMatchColumnMap =
            existEntity.properties.associateBy { it.name }

        val mergedConvertProperties = convertEntity.properties.map {
            if (it.associationType != null) {
                val associationMatchExistProperty =
                    associationMatchColumnMap[it.associationMatchKey]?.firstOrNull()

                if (associationMatchExistProperty != null)
                    return@map it.mergeExistProperty(associationMatchExistProperty)
            } else {
                val columnIdMatchExistProperty =
                    columnIdMatchColumnMap[it.columnId]?.firstOrNull()

                if (columnIdMatchExistProperty != null)
                    return@map it.mergeExistProperty(columnIdMatchExistProperty)

                val nameEqualExistProperty = nameMatchColumnMap[it.name]

                if (nameEqualExistProperty != null)
                    return@map it.mergeExistProperty(nameEqualExistProperty)
            }

            it.toEntity()
        }

        val mergedConvertPropertyNameSet = mergedConvertProperties.map { it.name }.toSet()

        val unMergeProperties = existEntity.properties
            .filter {
                it.associationType == null && it.columnId == null &&
                        it.name !in mergedConvertPropertyNameSet
            }
            .map { it.toEntity() }

        properties = listOf(
            mergedConvertProperties,
            unMergeProperties
        )
            .flatten()
            .sortedBy { it.orderKey }
    }