//package top.potmot.service
//
//import top.potmot.model.GenTable
//
///**
// * 生成服务
// */
//interface GenerateService {
//    fun generateTableCreate(tableId: Long): String
//
//    fun generateTableCreate(tableName: String): String
//
//    fun generateTableCreate(table: GenTable): String
//
//    fun generateTablesCreate(tableIds: Iterable<Long>): List<String>
//
//    fun generateTablesCreate(tableNames: Iterable<String>): List<String>
//
//    fun generateTablesCreate(tables: Iterable<GenTable>): List<String>
//
//    fun generateEntity(tableId: Long): String
//
//    fun generateEntity(tableName: String): String
//
//    fun generateEntity(table: GenTable): String
//
//    fun generateEntities(tableIds: Iterable<Long>): String
//
//    fun generateEntities(tableNames: Iterable<String>): String
//
//    fun generateEntities(tables: Iterable<GenTable>): String
//}
