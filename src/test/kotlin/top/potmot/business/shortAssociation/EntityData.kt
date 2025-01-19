package top.potmot.business.shortAssociation

import top.potmot.business.baseEntity
import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.core.business.property.EntityBusiness
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_idProperties
import top.potmot.enumeration.AssociationType

private const val shortAssociationEntityId = -1L

private const val shortAssociationTargetEntityId = -2L

private const val shortAssociationTargetIdViewEntityId = -3L

private val label1Property = baseProperty.copy(
    name = "label1",
    comment = "label1",
    inShortAssociationView = true,
    inOptionView = true,
)

private val label2Property = baseProperty.copy(
    name = "label2",
    comment = "label2",
    inShortAssociationView = true,
    inOptionView = true,
)

val shortAssociationEntity = baseEntity.copy(
    id = shortAssociationEntityId,
    name = "ShortAssociationEntity",
    comment = "shortAssociationEntityComment",
    remark = "shortAssociationEntityRemark",
    idProperties = listOf(
        TargetOf_idProperties(idProperty.toEntity())
    ),
    properties = listOf(
        idProperty,
        label1Property,
        label2Property
    )
).let {
    EntityBusiness(it, mapOf(shortAssociationEntityId to it))
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
        it, mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            shortAssociationTargetEntityId to it
        )
    )
}

private val shortAssociationIdViewProperty = baseProperty.copy(
    id = shortAssociationEntityId,
    name = shortAssociationProperty.name + "Id",
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
        it, mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            shortAssociationTargetIdViewEntityId to it
        )
    )
}