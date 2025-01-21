package top.potmot.business.crudPart

import top.potmot.business.entityIdMap
import top.potmot.business.enumIdMap
import top.potmot.business.testEntity
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.emptyAssociationPath

private val canNothingEntity = testEntity.copy(
    canEdit = false,
    canQuery = false,
    canAdd = false,
    canDelete = false
)

val addOnlyEntity = canNothingEntity.copy(
    canAdd = true,
).let {
    EntityBusiness(emptyAssociationPath, it, entityIdMap, enumIdMap)
}

val editOnlyEntity = canNothingEntity.copy(
    canEdit = true,
).let {
    EntityBusiness(emptyAssociationPath, it, entityIdMap, enumIdMap)
}

val queryOnlyEntity = canNothingEntity.copy(
    canQuery = true,
).let {
    EntityBusiness(emptyAssociationPath, it, entityIdMap, enumIdMap)
}

val deleteOnlyEntity = canNothingEntity.copy(
    canDelete = true,
).let {
    EntityBusiness(emptyAssociationPath, it, entityIdMap, enumIdMap)
}
