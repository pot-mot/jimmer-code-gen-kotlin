package top.potmot.business.selfAssociation

import top.potmot.business.baseEntity
import top.potmot.business.baseProperty
import top.potmot.business.idProperty
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.enumeration.AssociationType

private const val entityId = -1L

private const val labelPropertyId = -1L
private const val parentPropertyId = -2L
private const val childrenPropertyId = -3L

private const val entityName = "SelfAssociationEntity"
private const val entityComment = "树节点"

private val labelProperty = baseProperty.copy(
    id = labelPropertyId,
    name = "label",
    comment = "标签",
    inShortAssociationView = true,
    inOptionView = true,
)

private val parentProperty = idProperty.copy(
    id = parentPropertyId,
    name = "parent",
    comment = "父节点",
    associationType = AssociationType.MANY_TO_ONE,
    typeEntityId = entityId,
    typeNotNull = false,
    idProperty = false,
    inInsertInput = true,
    inUpdateInput = true,
    inOptionView = true,
)

private val childrenProperty = idProperty.copy(
    id = childrenPropertyId,
    name = "children",
    comment = "子节点",
    associationType = AssociationType.ONE_TO_MANY,
    mappedBy = "parent",
    typeEntityId = entityId,
    listType = true,
    idProperty = false,
    inListView = false,
    inInsertInput = false,
    inUpdateInput = false,
    inOptionView = false,
    inLongAssociationView = false,
    inLongAssociationInput = false,
)

val selfAssociationEntity = baseEntity.copy(
    id = entityId,
    name = entityName,
    comment = entityComment,
    remark = "selfAssociationEntityRemark",
    properties = listOf(
        idProperty,
        labelProperty,
        parentProperty,
        childrenProperty,
    )
).let {
    RootEntityBusiness(it, mapOf(entityId to it), emptyMap())
}
