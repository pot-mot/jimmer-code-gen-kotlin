package top.potmot.service

import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import java.util.*

interface TableToEntityMapService {
    fun mapEntity(table: GenTable): GenEntity

    fun mapEntityById(tableId: Long): Optional<GenEntity>

    fun mapEntities(tables: Iterable<GenTable>): List<GenEntity>

    fun mapEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>>
}
