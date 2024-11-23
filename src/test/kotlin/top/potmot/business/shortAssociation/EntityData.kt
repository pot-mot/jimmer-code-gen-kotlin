package top.potmot.business.shortAssociation

import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.business.testEntity
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_idProperties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity
import top.potmot.enumeration.AssociationType

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

val shortAssociationEntity = testEntity.copy(
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
)

private val shortAssociationProperty = baseProperty.copy(
    name = "shortAssociationProperty",
    type = shortAssociationEntity.name,
    associationType = AssociationType.MANY_TO_ONE,
    typeEntity = TargetOf_typeEntity(
        id = shortAssociationEntity.id,
        packagePath = shortAssociationEntity.packagePath,
        name = shortAssociationEntity.name,
        comment = shortAssociationEntity.comment,
        idProperties = listOf(
            TargetOf_typeEntity.TargetOf_idProperties(idProperty.toEntity())
        ),
        shortViewProperties = listOf(
            TargetOf_typeEntity.TargetOf_shortViewProperties(label1Property.toEntity()),
            TargetOf_typeEntity.TargetOf_shortViewProperties(label2Property.toEntity()),
        )
    )
)

val shortAssociationTargetEntity = testEntity.copy(
    properties = listOf(
        shortAssociationProperty
    )
)

private val shortAssociationIdViewProperty = baseProperty.copy(
    id = shortAssociationProperty.id,
    name = shortAssociationProperty.name + "Id",
    associationType = AssociationType.MANY_TO_ONE,
    idView = true,
    idViewTarget = shortAssociationProperty.name
)

val shortAssociationTargetIdViewEntity = testEntity.copy(
    properties = listOf(
        shortAssociationProperty,
        shortAssociationIdViewProperty
    )
)