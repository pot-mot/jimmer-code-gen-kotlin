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
    keepNameComment: Boolean
) = toEntity {
    id = existProperty.id
    if (keepNameComment) {
        name = existProperty.name
        comment = existProperty.comment
    }
    remark = existProperty.remark
    orderKey = existProperty.orderKey

    inListView = existProperty.inListView
    inDetailView = existProperty.inDetailView
    inSpecification = existProperty.inSpecification
    inInsertInput = existProperty.inInsertInput
    inUpdateInput = existProperty.inUpdateInput
    inOptionView = existProperty.inOptionView
    longAssociation = existProperty.longAssociation
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
    keepNameComment: Boolean
): GenEntity =
    convertEntity.toEntity {
        id = existEntity.id
        if (keepNameComment) {
            name = existEntity.name
            comment = existEntity.comment
        }
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
                    return@map it.mergeExistProperty(associationMatchExistProperty, keepNameComment)
            } else {
                val columnIdMatchExistProperty =
                    columnIdMatchColumnMap[it.columnId]?.firstOrNull()

                if (columnIdMatchExistProperty != null)
                    return@map it.mergeExistProperty(columnIdMatchExistProperty, keepNameComment)

                val nameEqualExistProperty = nameMatchColumnMap[it.name]

                if (nameEqualExistProperty != null)
                    return@map it.mergeExistProperty(nameEqualExistProperty, keepNameComment)
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