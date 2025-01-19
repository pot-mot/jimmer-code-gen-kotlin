package top.potmot.business.existValid

import top.potmot.business.entityIdMap
import top.potmot.business.testEntity
import top.potmot.business.idViewTestEntity
import top.potmot.core.business.property.EntityBusiness

val toOneAndEnumEntity = testEntity.copy(
    indexes = listOf(toOneUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap)
}

val toOneDuplicateAndEnumEntity = testEntity.copy(
    indexes = listOf(toOneUniqueIndex, toOneUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap)
}

val toManyEntity = testEntity.copy(
    indexes = listOf(toManyUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap)
}

val toOneIdViewAndEnumEntity = idViewTestEntity.copy(
    indexes = listOf(toOneIdViewUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap)
}

val toOneIdViewDuplicateAndEntity = idViewTestEntity.copy(
    indexes = listOf(toOneIdViewUniqueIndex, toOneIdViewUniqueIndex, enumUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap)
}

val toManyIdViewEntity = idViewTestEntity.copy(
    indexes = listOf(toManyIdViewUniqueIndex)
).let {
    EntityBusiness(it, entityIdMap)
}