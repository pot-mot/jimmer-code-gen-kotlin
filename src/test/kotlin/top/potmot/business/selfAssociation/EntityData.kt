package top.potmot.business.selfAssociation

import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.business.testEntity
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_idProperties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity
import top.potmot.enumeration.AssociationType

private const val entityId = -1L

private const val entityName = "SelfAssociationEntity"
private const val entityComment = "树节点"

private val labelProperty = baseProperty.copy(
    name = "label",
    comment = "标签",
    inShortAssociationView = true,
    inOptionView = true,
)

private val typeEntity = TargetOf_typeEntity(
    id = entityId,
    packagePath = testEntity.packagePath,
    name = entityName,
    comment = entityComment,
    idProperties = listOf(
        TargetOf_typeEntity.TargetOf_idProperties(idProperty.toEntity())
    ),
    shortViewProperties = emptyList()
)

private val parentProperty = idProperty.copy(
    name = "parent",
    comment = "父节点",
    associationType = AssociationType.MANY_TO_ONE,
    typeEntity = typeEntity,
    typeNotNull = false,
    idProperty = false,
    inInsertInput = true,
    inUpdateInput = true,
    inOptionView = true,
)

private val childrenProperty = idProperty.copy(
    name = "children",
    comment = "子节点",
    associationType = AssociationType.ONE_TO_MANY,
    mappedBy = "parent",
    typeEntity = typeEntity,
    listType = true,
    idProperty = false,
    inListView = false,
    inInsertInput = false,
    inUpdateInput = false,
    inOptionView = false,
    inLongAssociationView = false,
    inLongAssociationInput = false,
)

val selfAssociationEntity = testEntity.copy(
    id = entityId,
    name = entityName,
    comment = entityComment,
    remark = "selfAssociationEntityRemark",
    idProperties = listOf(
        TargetOf_idProperties(idProperty.toEntity())
    ),
    properties = listOf(
        idProperty,
        labelProperty,
        parentProperty,
        childrenProperty,
    )
)
