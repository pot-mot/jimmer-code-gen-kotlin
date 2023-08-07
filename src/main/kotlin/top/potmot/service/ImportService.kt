package top.potmot.service

import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTable
import java.util.Optional

/**
 * 导入服务
 * 用于操作 “向 MainDataSource 即本项目数据源 中导入 来自 GenDataSource 的数据库表，并生成对应的实体信息” 这一流程
 */
interface ImportService {
    fun previewTables(tablePattern: String? = null): List<GenTable>

    fun previewEntity(table: GenTable): GenEntity

    fun previewEntityById(tableId: Long): Optional<GenEntity>

    fun previewEntities(tables: Iterable<GenTable>): List<GenEntity>

    fun previewEntities(tablePattern: String? = null): List<GenEntity>

    fun previewEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>>

    fun importEntity(table: GenTable): GenEntity

    fun importEntityById(tableId: Long): Optional<GenEntity>

    fun importEntities(tables: Iterable<GenTable>): List<GenEntity>

    fun importEntities(tablePattern: String? = null): List<GenEntity>

    fun importEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>>

    fun getTable(tableId: Long, tableFetcher: Fetcher<GenTable>? = null): Optional<GenTable>

    fun getColumn(table: GenTable, columnId: Long): Optional<GenColumn>

    fun getColumn(table: GenTable, columnName: String): Optional<GenColumn>

    fun getTables(page: Pageable, tableFetcher: Fetcher<GenTable>? = null): Page<GenTable>

    fun getTables(tableIds: Iterable<Long>, tableFetcher: Fetcher<GenTable>? = null): List<Optional<GenTable>>

    fun removeTableById(tableId: Long): Boolean

    fun removeTable(table: GenTable): Boolean

    fun removeTablesByIds(tableIds: Iterable<Long>): Int

    fun removeTables(tables: Iterable<GenTable>): Int

    fun getEntity(entityId: Long, entityFetcher: Fetcher<GenEntity>? = null): Optional<GenEntity>

    fun getEntities(page: Pageable, entityFetcher: Fetcher<GenEntity>? = null): Page<GenEntity>

    fun getEntities(entityIds: Iterable<Long>, entityFetcher: Fetcher<GenEntity>? = null): List<Optional<GenEntity>>

    fun getProperty(entity: GenEntity, propertyId: Long): Optional<GenProperty>

    fun getProperty(entity: GenEntity, propertyName: String): Optional<GenProperty>
}
