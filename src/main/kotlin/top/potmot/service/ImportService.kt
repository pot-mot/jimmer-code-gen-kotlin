package top.potmot.service

import top.potmot.model.GenTable

/**
 * 导入服务
 * 用于 向 MainDataSource 即本项目数据源 中导入 来自 GenDataSource 的数据库表，并生成对应的实体信息
 * example:
 * table author --importTable--> GenTable author --importEntity--> GenEntity Author
 * importTable 完成 metadata 至 GenTable 的处理，包括键和索引的处理
 * importEntity 完成 GenTable 至 GenEntity 的处理，包括数据库类型到业务类型的映射
 */
interface ImportService {
    fun importTable(tablePattern: String): Boolean

    fun importTable(table: GenTable): Boolean

    fun importTables(tablePatterns: Iterable<String>): Int

    fun importTables(tables: Iterable<GenTable>): Int

    fun importEntity(tableId: Long): Boolean

    fun importEntity(tablePattern: String): Boolean

    fun importEntity(table: GenTable): Boolean

    fun importEntities(tableIds: Iterable<Long>): Int

    fun importEntities(tablePatterns: Iterable<String>): Int

    fun importEntities(tables: Iterable<GenTable>): Int
}
