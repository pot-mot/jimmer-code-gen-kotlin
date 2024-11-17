package top.potmot.business.existValid

import top.potmot.business.idViewTestEntity
import top.potmot.business.testEntity

val toOneAndEnumEntity = testEntity.copy(
    indexes = listOf(toOneUniqueIndex, enumUniqueIndex)
)

val toOneDuplicateAndEnumEntity = testEntity.copy(
    indexes = listOf(toOneUniqueIndex, toOneUniqueIndex, enumUniqueIndex)
)

val toManyEntity = testEntity.copy(
    indexes = listOf(toManyUniqueIndex)
)

val toOneIdViewAndEnumEntity = idViewTestEntity.copy(
    indexes = listOf(toOneIdViewUniqueIndex, enumUniqueIndex)
)

val toOneIdViewDuplicateAndEntity = idViewTestEntity.copy(
    indexes = listOf(toOneIdViewUniqueIndex, toOneIdViewUniqueIndex, enumUniqueIndex)
)

val toManyIdViewEntity = idViewTestEntity.copy(
    indexes = listOf(toManyIdViewUniqueIndex)
)