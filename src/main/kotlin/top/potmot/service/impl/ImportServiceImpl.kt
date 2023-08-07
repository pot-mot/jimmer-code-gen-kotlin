package top.potmot.service.impl

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenEntityRepository
import top.potmot.dao.GenTableRepository
import top.potmot.dao.GenTypeMappingRepository
import top.potmot.dao.MetadataDao
import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTable
import top.potmot.model.base.Identifiable
import top.potmot.service.ImportService
import top.potmot.util.convert.tableToEntity
import java.util.*

@Service
class ImportServiceImpl(
    @Autowired val metadataDao: MetadataDao,
    @Autowired val genTableRepository: GenTableRepository,
    @Autowired val genEntityRepository: GenEntityRepository,
    @Autowired val genTypeMappingRepository: GenTypeMappingRepository
) : ImportService {
    override fun previewTables(tablePattern: String?): List<GenTable> {
        return metadataDao.getTables(tablePattern)
    }

    override fun previewEntity(table: GenTable): GenEntity {
        val typeMappings = genTypeMappingRepository.findAll()
        return tableToEntity(table, typeMappings)
    }

    override fun previewEntityById(tableId: Long): Optional<GenEntity> {
        val table = genTableRepository.findById(tableId)
        return if (table.isPresent) {
            Optional.of(previewEntity(table.get()))
        } else {
            Optional.empty<GenEntity>()
        }
    }

    override fun previewEntities(tables: Iterable<GenTable>): List<GenEntity> {
        val typeMappings = genTypeMappingRepository.findAll()
        return tables.map {
            tableToEntity(it, typeMappings)
        }
    }

    override fun previewEntities(tablePattern: String?): List<GenEntity> {
        return previewEntities(previewTables(tablePattern))
    }

    override fun previewEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>> {
        val tables = listToOptionalList(genTableRepository.findByIds(tableIds), tableIds)
        val typeMappings = genTypeMappingRepository.findAll()
        return tables.map {
            if (it.isPresent) {
                Optional.of(tableToEntity(it.get(), typeMappings))
            } else {
                Optional.empty<GenEntity>()
            }
        }
    }

    @Transactional
    fun keepTableExist(table: GenTable): GenTable {
        return if (ImmutableObjects.isLoaded(table, "id")) {
            table
        } else {
            genTableRepository.save(table)
        }
    }

    @Transactional
    override fun importEntity(table: GenTable): GenEntity {
        val typeMappings = genTypeMappingRepository.findAll()
        val entity = tableToEntity(keepTableExist(table), typeMappings)
        return genEntityRepository.save(entity)
    }

    @Transactional
    override fun importEntityById(tableId: Long): Optional<GenEntity> {
        val table = getTable(tableId)
        return if (table.isPresent) {
            Optional.of(importEntity(table.get()))
        } else {
            Optional.empty<GenEntity>()
        }
    }

    @Transactional
    override fun importEntities(tables: Iterable<GenTable>): List<GenEntity> {
        val typeMappings = genTypeMappingRepository.findAll()
        return tables.map {
            val entity = tableToEntity(keepTableExist(it), typeMappings)
            genEntityRepository.save(entity)
        }
    }

    @Transactional
    override fun importEntities(tablePattern: String?): List<GenEntity> {
        return importEntities(previewTables(tablePattern))
    }

    @Transactional
    override fun importEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>> {
        val entities = importEntities(genTableRepository.findByIds(tableIds))
        return listToOptionalList(entities, tableIds)
    }

    override fun getTable(tableId: Long, tableFetcher: Fetcher<GenTable>?): Optional<GenTable> {
        return if (tableFetcher != null) {
            genTableRepository.findById(tableId, tableFetcher)
        } else {
            genTableRepository.findById(tableId)
        }
    }

    override fun getColumn(table: GenTable, columnId: Long): Optional<GenColumn> {
        return if (ImmutableObjects.isLoaded(table, "columns")) {
            val result = table.columns.filter { it.id == columnId }
            if (result.isEmpty()) {
                Optional.empty<GenEntity>()
            }
            Optional.ofNullable(result[0])
        } else {
            Optional.empty()
        }
    }

    override fun getColumn(table: GenTable, columnName: String): Optional<GenColumn> {
        return if (ImmutableObjects.isLoaded(table, "columns")) {
            val result = table.columns.filter { it.columnName == columnName }
            if (result.isEmpty()) {
                Optional.empty<GenEntity>()
            }
            Optional.ofNullable(result[0])
        } else {
            Optional.empty()
        }
    }

    override fun getTables(tableIds: Iterable<Long>, tableFetcher: Fetcher<GenTable>?): List<Optional<GenTable>> {
        val tables = genTableRepository.findByIds(tableIds, tableFetcher)
        return listToOptionalList(tables, tableIds)
    }

    override fun removeTableById(tableId: Long): Boolean {
        return genTableRepository.deleteById(tableId, DeleteMode.PHYSICAL) == 1
    }

    override fun removeTable(table: GenTable): Boolean {
        return genTableRepository.delete(table, DeleteMode.PHYSICAL) == 1
    }

    override fun removeTablesByIds(tableIds: Iterable<Long>): Int {
        return genTableRepository.deleteByIds(tableIds, DeleteMode.PHYSICAL)
    }

    override fun removeTables(tables: Iterable<GenTable>): Int {
        return genTableRepository.deleteAll(tables, DeleteMode.PHYSICAL)
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
        return listToOptionalList(entities, entityIds)
    }

    override fun getProperty(entity: GenEntity, propertyId: Long): Optional<GenProperty> {
        return if (ImmutableObjects.isLoaded(entity, "properties")) {
            val result = entity.properties.filter { it.id == propertyId }
            if (result.isEmpty()) {
                Optional.empty<GenEntity>()
            }
            Optional.ofNullable(result[0])
        } else {
            Optional.empty()
        }
    }

    override fun getProperty(entity: GenEntity, propertyName: String): Optional<GenProperty> {
        return if (ImmutableObjects.isLoaded(entity, "properties")) {
            val result = entity.properties.filter { it.propertyName == propertyName }
            if (result.isEmpty()) {
                Optional.empty<GenEntity>()
            }
            Optional.ofNullable(result[0])
        } else {
            Optional.empty()
        }
    }

    fun <T : Identifiable<Long>> listToOptionalList(list: List<T>, ids: Iterable<Long>): List<Optional<T>> {
        val result = mutableListOf<Optional<T>>()

        for (id in ids) {
            val entity = list.find { it.id == id }
            result += Optional.ofNullable(entity)
        }

        return result
    }
}
