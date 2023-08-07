package top.potmot.service

import org.babyfish.jimmer.sql.fetcher.Fetcher
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import java.util.Optional

/**
 * 导入服务
 * 用于 向 MainDataSource 即本项目数据源 中导入 来自 GenDataSource 的数据库表，并生成对应的实体信息
 * example:
 * table author --importTable--> GenTable author --importEntity--> GenEntity Author
 * importTable 完成 metadata 至 GenTable 的处理，包括键和索引的处理
 * importEntity 完成 GenTable 至 GenEntity 的处理，包括数据库类型到业务类型的映射
 */
interface ImportService {
    fun previewTables(tablePattern: String? = null): List<GenTable>

    fun getTable(tableId: Long, tableFetcher: Fetcher<GenTable>? = null): Optional<GenTable>

    fun getTables(tableIds: Iterable<Long>, tableFetcher: Fetcher<GenTable>? = null): List<Optional<GenTable>>

    fun importTable(table: GenTable): GenTable

    fun importTables(tablePattern: String? = null): List<GenTable>

    fun importTables(tables: Iterable<GenTable>): List<GenTable>

    fun removeTable(tableId: Long): Boolean

    fun removeTable(table: GenTable): Boolean

    fun removeTables(tableIds: Iterable<Long>): Int

    fun removeTables(tables: Iterable<GenTable>): Int

    fun previewEntity(tableId: Long): Optional<GenEntity>

    fun previewEntity(table: GenTable): GenEntity

    fun previewEntities(tablePattern: String? = null): List<GenEntity>

    fun previewEntities(tableIds: Iterable<Long>): List<Optional<GenEntity>>

    fun previewEntities(tables: Iterable<GenTable>): List<GenEntity>

    fun getEntity(entityId: Long, entityFetcher: Fetcher<GenEntity>? = null): Optional<GenEntity>

    fun getEntities(entityIds: Iterable<Long>, entityFetcher: Fetcher<GenEntity>? = null): List<Optional<GenEntity>>

    fun importEntity(tableId: Long): Optional<GenEntity>

    fun importEntity(table: GenTable): GenEntity

    fun importEntities(tablePattern: String? = null): List<GenEntity>

    fun importEntities(tableIds: Iterable<Long>): List<Optional<GenEntity>>

    fun importEntities(tables: Iterable<GenTable>): List<GenEntity>

}
