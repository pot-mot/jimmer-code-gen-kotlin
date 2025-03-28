package top.potmot.core.entity.convert.merge

import top.potmot.core.entity.convert.meta.AssociationPropertyPair
import top.potmot.core.entity.convert.meta.AssociationPropertyPairInterface
import top.potmot.core.entity.convert.EntityView
import top.potmot.core.entity.convert.EntityInput
import top.potmot.core.entity.convert.PropertyInput
import top.potmot.core.entity.convert.PropertyView

/**
 * 对已存在的实体和转换出的实体进行合并
 * 保留原实体的id，注释，备注，业务配置
 */
fun mergeExistAndConvertEntity(
    existEntity: EntityView,
    convertEntity: EntityInput,
) = convertEntity.toEntity {
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

    otherAnnotation = existEntity.otherAnnotation

    canAdd = existEntity.canAdd
    canEdit = existEntity.canEdit
    canQuery = existEntity.canQuery
    canDelete = existEntity.canDelete
    hasPage = existEntity.hasPage
    pageCanAdd = existEntity.pageCanAdd
    pageCanEdit = existEntity.pageCanEdit
    pageCanViewDetail = existEntity.pageCanViewDetail
    pageCanQuery = existEntity.pageCanQuery
    pageCanDelete = existEntity.pageCanDelete
    queryByPage = existEntity.queryByPage
}

fun mergeExistAndConvertProperty(
    existProperty: PropertyView,
    convertProperty: PropertyInput,
) = convertProperty.toEntity {
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

    otherAnnotation = existProperty.otherAnnotation

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
 * 懒合并关联属性和idView对，直到必要合并时才进行合并，用于暴露关联属性在合并前后的差异
 */
data class AssociationPropertyPairWaitMergeExist(
    override val associationProperty: PropertyInput,
    override val idView: PropertyInput? = null,
    val existProperties: Collection<PropertyView>? = null,
) : AssociationPropertyPairInterface {
    private val PropertyView.associationMatchKey
        get() = "$columnId $typeTableId $associationType $mappedBy $idView $idViewTarget"

    private val PropertyInput.associationMatchKey
        get() = "$columnId $typeTableId $associationType $mappedBy $idView $idViewTarget"

    fun mergeExist(): AssociationPropertyPair {
        if (!existProperties.isNullOrEmpty()) {
            val sourcePropertyMatchKeyMap = existProperties.groupBy { it.associationMatchKey }
            val associationMatchedProperties = sourcePropertyMatchKeyMap[associationProperty.associationMatchKey]
            if (associationMatchedProperties?.size == 1) {
                val mergedAssociationProperty =
                    PropertyInput(mergeExistAndConvertProperty(associationMatchedProperties[0], associationProperty))

                if (idView != null) {
                    val idViewMatchedProperties =
                        sourcePropertyMatchKeyMap[idView.associationMatchKey]
                            ?: sourcePropertyMatchKeyMap[idView.copy(idViewTarget = mergedAssociationProperty.name).associationMatchKey]
                            ?: sourcePropertyMatchKeyMap[idView.copy(idViewTarget = associationMatchedProperties[0].name).associationMatchKey]
                    if (idViewMatchedProperties?.size == 1) {
                        val mergedIdView =
                            PropertyInput(mergeExistAndConvertProperty(idViewMatchedProperties[0], idView))
                        return AssociationPropertyPair(
                            mergedAssociationProperty,
                            mergedIdView.copy(idViewTarget = mergedAssociationProperty.name)
                        )
                    } else {
                        return AssociationPropertyPair(
                            mergedAssociationProperty,
                            idView.copy(idViewTarget = mergedAssociationProperty.name)
                        )
                    }
                }

                return AssociationPropertyPair(mergedAssociationProperty)
            }
        }

        return AssociationPropertyPair(associationProperty, idView)
    }
}
