package top.potmot.business.longAssociation

import top.potmot.business.baseEntity
import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.enumeration.AssociationType

private const val longAssociationEntityId = -1L

private const val longAssociationToOneTargetEntityId = -2L

private const val longAssociationToManyTargetEntityId = -3L

private const val namePropertyId = -1L

private val nameProperty = baseProperty.copy(
    id = namePropertyId,
    name = "name",
    comment = "name",
    inShortAssociationView = true,
    inOptionView = true,
)

val longAssociationEntity = baseEntity.copy(
    id = longAssociationEntityId,
    name = "LongAssociationEntity",
    comment = "longAssociationEntityComment",
    remark = "longAssociationEntityRemark",
    properties = listOf(
        idProperty,
        nameProperty
    )
).let {
    RootEntityBusiness(it, mapOf(longAssociationEntityId to it), emptyMap())
}

private val longAssociationToOneProperty = baseProperty.copy(
    name = "longAssociationToOneProperty",
    type = longAssociationEntity.name,
    associationType = AssociationType.MANY_TO_ONE,
    typeEntityId = longAssociationEntityId,
    longAssociation = true
)

val longAssociationToOneTargetEntity = baseEntity.copy(
    id = longAssociationToOneTargetEntityId,
    properties = listOf(
        idProperty,
        longAssociationToOneProperty
    )
).let {
    RootEntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            longAssociationEntityId to longAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}

private val longAssociationToManyProperty = baseProperty.copy(
    name = "longAssociationToOneProperty",
    type = longAssociationEntity.name,
    associationType = AssociationType.ONE_TO_MANY,
    typeEntityId = longAssociationEntityId,
    longAssociation = true,
    listType = true,
)


val longAssociationToManyTargetEntity = baseEntity.copy(
    id = longAssociationToManyTargetEntityId,
    properties = listOf(
        idProperty,
        longAssociationToManyProperty
    )
).let {
    RootEntityBusiness(
        entity = it,
        entityIdMap = mapOf(
            longAssociationEntityId to longAssociationEntity.entity,
            it.id to it
        ),
        enumIdMap = emptyMap()
    )
}