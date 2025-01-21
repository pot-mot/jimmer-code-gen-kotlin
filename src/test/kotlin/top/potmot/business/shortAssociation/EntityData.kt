package top.potmot.business.shortAssociation

import top.potmot.business.baseEntity
import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.enumeration.AssociationType

private const val shortAssociationEntityId = -1L

private const val shortAssociationTargetEntityId = -2L

private const val shortAssociationTargetIdViewEntityId = -3L

private const val label1PropertyId = -1L

private const val label2PropertyId = -2L

private val label1Property = baseProperty.copy(
    id = label1PropertyId,
    name = "label1",
    comment = "label1 comment",
    inShortAssociationView = true,
    inOptionView = true,
)

private val label2Property = baseProperty.copy(
    id = label2PropertyId,
    name = "label2",
    comment = "label2 comment",
    inShortAssociationView = true,
    inOptionView = true,
)

val shortAssociationEntity = baseEntity.copy(
    id = shortAssociationEntityId,
    name = "ShortAssociationEntity",
    comment = "shortAssociationEntityComment",
    remark = "shortAssociationEntityRemark",
    properties = listOf(
        idProperty,
        label1Property,
        label2Property
    )
).let {
    EntityBusiness(it, mapOf(shortAssociationEntityId to it), emptyMap())
}

private val shortAssociationProperty = baseProperty.copy(
    name = "shortAssociationProperty",
    type = shortAssociationEntity.name,
    associationType = AssociationType.MANY_TO_ONE,
    typeEntityId = shortAssociationEntityId
)

val shortAssociationTargetEntity = baseEntity.copy(
    id = shortAssociationTargetEntityId,
    properties = listOf(
        idProperty,
        shortAssociationProperty
    )
).let {
    EntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}

private val shortAssociationIdViewProperty = baseProperty.copy(
    id = shortAssociationEntityId,
    name = shortAssociationProperty.name + "Id",
    comment = shortAssociationProperty.comment + " Id View",
    associationType = AssociationType.MANY_TO_ONE,
    idView = true,
    idViewTarget = shortAssociationProperty.name
)

val shortAssociationTargetIdViewEntity = baseEntity.copy(
    id = shortAssociationTargetIdViewEntityId,
    properties = listOf(
        idProperty,
        shortAssociationProperty,
        shortAssociationIdViewProperty
    )
).let {
    EntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}