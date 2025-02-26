package top.potmot.business.crudPart

import top.potmot.business.entityIdMap
import top.potmot.business.enumIdMap
import top.potmot.business.testEntity
import top.potmot.core.business.meta.RootEntityBusiness

private val canNothingEntity = testEntity.copy(
    canAdd = false,
    canEdit = false,
    canQuery = false,
    canDelete = false,
    pageCanAdd = false,
    pageCanEdit = false,
    pageCanViewDetail = false,
    pageCanQuery = false,
    pageCanDelete = false,
)

val addOnlyEntity = canNothingEntity.copy(
    canQuery = true,
    canAdd = true,
    pageCanAdd = true,
).let {
    RootEntityBusiness(it, entityIdMap, enumIdMap)
}

val editOnlyEntity = canNothingEntity.copy(
    canQuery = true,
    canEdit = true,
    pageCanEdit = true,
).let {
    RootEntityBusiness(it, entityIdMap, enumIdMap)
}

val queryOnlyEntity = canNothingEntity.copy(
    canQuery = true,
    pageCanQuery = true,
).let {
    RootEntityBusiness(it, entityIdMap, enumIdMap)
}

val deleteOnlyEntity = canNothingEntity.copy(
    canQuery = true,
    canDelete = true,
    pageCanDelete = true,
).let {
    RootEntityBusiness(it, entityIdMap, enumIdMap)
}
