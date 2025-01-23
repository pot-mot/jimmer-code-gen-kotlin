package top.potmot.business.shortAssociation

import top.potmot.business.baseEntity
import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.core.business.meta.RootEntityBusiness
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
    RootEntityBusiness(it, mapOf(shortAssociationEntityId to it), emptyMap())
}

private val shortAssociationToOneProperty = baseProperty.copy(
    name = "shortAssociationProperty",
    type = shortAssociationEntity.name,
    associationType = AssociationType.MANY_TO_ONE,
    typeEntityId = shortAssociationEntityId
)

val shortAssociationToOneTargetEntity = baseEntity.copy(
    id = shortAssociationTargetEntityId,
    properties = listOf(
        idProperty,
        shortAssociationToOneProperty
    )
).let {
    RootEntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}

private val shortAssociationToOneIdViewProperty = baseProperty.copy(
    id = shortAssociationEntityId,
    name = shortAssociationToOneProperty.name + "Id",
    comment = shortAssociationToOneProperty.comment + " Id View",
    associationType = AssociationType.MANY_TO_ONE,
    idView = true,
    idViewTarget = shortAssociationToOneProperty.name
)

val shortAssociationToOneTargetIdViewEntity = baseEntity.copy(
    id = shortAssociationTargetIdViewEntityId,
    properties = listOf(
        idProperty,
        shortAssociationToOneProperty,
        shortAssociationToOneIdViewProperty
    )
).let {
    RootEntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}

private val shortAssociationToManyProperty = baseProperty.copy(
    name = "shortAssociationProperty",
    type = shortAssociationEntity.name,
    listType = true,
    associationType = AssociationType.MANY_TO_MANY,
    typeEntityId = shortAssociationEntityId
)

val shortAssociationToManyTargetEntity = baseEntity.copy(
    id = shortAssociationTargetEntityId,
    properties = listOf(
        idProperty,
        shortAssociationToManyProperty
    )
).let {
    RootEntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}

private val shortAssociationToManyIdViewProperty = baseProperty.copy(
    id = shortAssociationEntityId,
    name = shortAssociationToManyProperty.name + "Ids",
    comment = shortAssociationToManyProperty.comment + " Id View",
    listType = true,
    associationType = AssociationType.MANY_TO_MANY,
    idView = true,
    idViewTarget = shortAssociationToManyProperty.name
)

val shortAssociationToManyTargetIdViewEntity = baseEntity.copy(
    id = shortAssociationTargetIdViewEntityId,
    properties = listOf(
        idProperty,
        shortAssociationToManyProperty,
        shortAssociationToManyIdViewProperty
    )
).let {
    RootEntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            shortAssociationEntityId to shortAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}