package top.potmot.service

import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import java.util.*

interface EntityGenerateService {
    fun previewEntity(table: GenTable): GenEntity

    fun previewEntityById(tableId: Long): Optional<GenEntity>

    fun previewEntities(tables: Iterable<GenTable>): List<GenEntity>

    fun previewEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>>
}
