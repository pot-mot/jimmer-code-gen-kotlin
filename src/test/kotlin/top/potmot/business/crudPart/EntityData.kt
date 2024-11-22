package top.potmot.business.crudPart

import top.potmot.business.testEntity

private val canNothingEntity = testEntity.copy(
    canEdit = false,
    canQuery = false,
    canAdd = false,
    canDelete = false
)

val addOnlyEntity = canNothingEntity.copy(
    canAdd = true,
)

val editOnlyEntity = canNothingEntity.copy(
    canEdit = true,
)

val queryOnlyEntity = canNothingEntity.copy(
    canQuery = true,
)

val deleteOnlyEntity = canNothingEntity.copy(
    canDelete = true,
)
