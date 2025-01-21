package top.potmot.business.existValid

import top.potmot.business.entityIdMap
import top.potmot.business.enumIdMap
import top.potmot.business.testEntity
import top.potmot.business.idViewTestEntity
import top.potmot.core.business.meta.EntityBusiness

val toOneAndEnumEntity = testEntity.copy(
    indexes = listOf(toOneUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap, enumIdMap)
}

val toOneDuplicateAndEnumEntity = testEntity.copy(
    indexes = listOf(toOneUniqueIndex, toOneUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap, enumIdMap)
}

val toManyEntity = testEntity.copy(
    indexes = listOf(toManyUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap, enumIdMap)
}

val toOneIdViewAndEnumEntity = idViewTestEntity.copy(
    indexes = listOf(toOneIdViewUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap, enumIdMap)
}

val toOneIdViewDuplicateAndEntity = idViewTestEntity.copy(
    indexes = listOf(toOneIdViewUniqueIndex, toOneIdViewUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap, enumIdMap)
}

val toManyIdViewEntity = idViewTestEntity.copy(
    indexes = listOf(toManyIdViewUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap, enumIdMap)
}