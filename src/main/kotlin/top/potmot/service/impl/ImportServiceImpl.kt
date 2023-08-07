package top.potmot.service.impl

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenEntityRepository
import top.potmot.dao.GenTableRepository
import top.potmot.dao.MetadataDao
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.service.ImportService
import top.potmot.util.convert.tableToEntity
import java.util.*

@Service
class ImportServiceImpl(
    @Autowired val metadataDao: MetadataDao,
    @Autowired val genTableRepository: GenTableRepository,
    @Autowired val genEntityRepository: GenEntityRepository
) : ImportService {
    override fun previewTables(tablePattern: String?): List<GenTable> {
        return metadataDao.getTables(tablePattern)
    }

    override fun getTable(tableId: Long, tableFetcher: Fetcher<GenTable>?): Optional<GenTable> {
        return if (tableFetcher != null) {
            genTableRepository.findById(tableId, tableFetcher)
        } else {
            genTableRepository.findById(tableId)
        }
    }

    override fun getTables(tableIds: Iterable<Long>, tableFetcher: Fetcher<GenTable>?): List<Optional<GenTable>> {
        val tables = genTableRepository.findByIds(tableIds, tableFetcher)
        val result = mutableListOf<Optional<GenTable>>()

        for (tableId in tableIds) {
            val table = tables.find { it.id == tableId }
            result += Optional.ofNullable(table)
        }

        return result
    }

    override fun importTable(table: GenTable): GenTable {
        return genTableRepository.save(table)
    }

    @Transactional
    override fun importTables(tablePattern: String?): List<GenTable> {
        return importTables(previewTables(tablePattern))
    }

    @Transactional
    override fun importTables(tables: Iterable<GenTable>): List<GenTable> {
        return importTables(tables)
    }

    @Transactional
    fun importTables(tables: List<GenTable>): List<GenTable> {
        return tables.map {
            genTableRepository.save(it)
        }
    }

    override fun removeTable(tableId: Long): Boolean {
        return genTableRepository.deleteById(tableId, DeleteMode.PHYSICAL) == 1
    }

    override fun removeTable(table: GenTable): Boolean {
        return genTableRepository.delete(table, DeleteMode.PHYSICAL) == 1
    }

    override fun removeTables(tableIds: Iterable<Long>): Int {
        return genTableRepository.deleteByIds(tableIds, DeleteMode.PHYSICAL)
    }

    override fun removeTables(tables: Iterable<GenTable>): Int {
        return genTableRepository.deleteAll(tables, DeleteMode.PHYSICAL)
    }

    override fun previewEntity(tableId: Long): Optional<GenEntity> {
        val table = genTableRepository.findById(tableId)
        return if (table.isPresent) {
            Optional.ofNullable(tableToEntity(table.get()))
        } else {
            Optional.empty<GenEntity>()
        }
    }

    override fun previewEntity(table: GenTable): GenEntity {
        return tableToEntity(table)
    }

    override fun previewEntities(tablePattern: String?): List<GenEntity> {
        return previewTables(tablePattern).map {
            tableToEntity(it)
        }
    }

    override fun previewEntities(tableIds: Iterable<Long>): List<Optional<GenEntity>> {
        return tableIds.map {
            previewEntity(it)
        }
    }

    override fun previewEntities(tables: Iterable<GenTable>): List<GenEntity> {
        return tables.map {
            tableToEntity(it)
        }
    }

    override fun getEntity(entityId: Long, entityFetcher: Fetcher<GenEntity>?): Optional<GenEntity> {
        return if (entityFetcher != null) {
            genEntityRepository.findById(entityId, entityFetcher)
        } else {
            genEntityRepository.findById(entityId)
        }
    }

    override fun getEntities(entityIds: Iterable<Long>, entityFetcher: Fetcher<GenEntity>?): List<Optional<GenEntity>> {
        val entities = genEntityRepository.findByIds(entityIds, entityFetcher)
        val result = mutableListOf<Optional<GenEntity>>()

        for (entityId in entityIds) {
            val entity = entities.find { it.id == entityId }
            result += Optional.ofNullable(entity)
        }

        return result
    }

    override fun importEntity(tableId: Long): Optional<GenEntity> {
        val table = getTable(tableId)
        return if (table.isPresent) {
            val entity = tableToEntity(table.get())
            Optional.ofNullable(
                genEntityRepository.save(entity)
            )
        } else {
            Optional.empty<GenEntity>()
        }
    }

    override fun importEntity(table: GenTable): GenEntity {
        return genEntityRepository.save(tableToEntity(table))
    }

    override fun importEntities(tablePattern: String?): List<GenEntity> {
        return importEntities(previewTables(tablePattern))
    }

    override fun importEntities(tableIds: Iterable<Long>): List<Optional<GenEntity>> {
        return tableIds.map {
            importEntity(it)
        }
    }

    override fun importEntities(tables: Iterable<GenTable>): List<GenEntity> {
        return tables.map {
            importEntity(it)
        }
    }

    @Transactional
    fun clearTables() {
        genTableRepository.deleteAll()
    }
}
