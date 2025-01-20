package top.potmot.business.crudPart

import top.potmot.business.entityIdMap
import top.potmot.business.testEntity
import top.potmot.core.business.meta.EntityBusiness

private val canNothingEntity = testEntity.copy(
    canEdit = false,
    canQuery = false,
    canAdd = false,
    canDelete = false
)

val addOnlyEntity = canNothingEntity.copy(
    canAdd = true,
).let {
    EntityBusiness(it, entityIdMap)
}

val editOnlyEntity = canNothingEntity.copy(
    canEdit = true,
).let {
    EntityBusiness(it, entityIdMap)
}

val queryOnlyEntity = canNothingEntity.copy(
    canQuery = true,
).let {
    EntityBusiness(it, entityIdMap)
}

val deleteOnlyEntity = canNothingEntity.copy(
    canDelete = true,
).let {
    EntityBusiness(it, entityIdMap)
}
