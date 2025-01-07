package top.potmot.core.entity.convert.merge

import org.babyfish.jimmer.kt.isLoaded
import top.potmot.core.entity.meta.AssociationPropertyPair
import top.potmot.core.entity.meta.AssociationPropertyPairInterface
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.copy
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenPropertyInput

/**
 * 对已存在的实体和转换出的实体进行合并
 * 保留原实体的id，注释，备注，业务配置
 */
fun mergeExistAndConvertEntity(
    existEntity: GenEntityDetailView,
    convertEntity: GenEntityInput,
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
}

fun mergeExistAndConvertProperty(
    existProperty: GenEntityDetailView.TargetOf_properties,
    convertProperty: GenEntityInput.TargetOf_properties,
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
    override val associationProperty: GenPropertyInput,
    override val idView: GenPropertyInput? = null,
    val existProperties: Collection<GenEntityDetailView.TargetOf_properties>? = null,
): AssociationPropertyPairInterface {
    private val GenEntityDetailView.TargetOf_properties.associationMatchKey
        get() = "$columnId $typeTableId $associationType $mappedBy $idView $idViewTarget"

    private val GenEntityInput.TargetOf_properties.associationMatchKey
        get() = "$columnId $typeTableId $associationType $mappedBy $idView $idViewTarget"

    fun mergeExist(): AssociationPropertyPair {
        if (!existProperties.isNullOrEmpty()) {
            val sourcePropertyMatchKeyMap = existProperties.groupBy { it.associationMatchKey }
            val associationMatchedProperties = sourcePropertyMatchKeyMap[associationProperty.associationMatchKey]
            if (associationMatchedProperties?.size == 1) {
                val mergedAssociationProperty =
                    GenPropertyInput(mergeExistAndConvertProperty(associationMatchedProperties[0], associationProperty))

                if (idView != null) {
                    val idViewMatchedProperties =
                        sourcePropertyMatchKeyMap[idView.associationMatchKey] ?: sourcePropertyMatchKeyMap[idView.copy(
                            idViewTarget = mergedAssociationProperty.name
                        ).associationMatchKey]
                    if (idViewMatchedProperties?.size == 1) {
                        val mergedIdView =
                            GenPropertyInput(mergeExistAndConvertProperty(idViewMatchedProperties[0], idView))
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

// 根据 在"合并已存在的数据"时变化的关联属性名称，处理实体中的一些属性
fun produceAssociationPropertyNameChange(
    entities: Collection<GenEntity>,
    mergeExistChangedAssociationPropertyNameMap: Map<String, String>,
): List<GenEntity> =
    entities.map { entity ->
        if (isLoaded(entity, GenEntity::properties)) {
            entity.copy {
                properties.map { property ->
                    if (isLoaded(property, GenProperty::mappedBy) && isLoaded(property, GenProperty::typeTableId) && property.mappedBy != null && property.typeTableId != null) {
                        property.copy {
                            val changedPropertyName = mergeExistChangedAssociationPropertyNameMap["${property.typeTableId} ${property.mappedBy}"]

                            if (changedPropertyName != null) {
                                mappedBy = changedPropertyName
                            }
                        }
                    } else {
                        property
                    }
                }
            }
        } else {
            entity
        }
    }
