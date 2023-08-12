package top.potmot.service

import top.potmot.model.GenTable

interface TableImportService {
    fun importTable(table: GenTable): GenTable

    fun importTables(tables: List<GenTable>): List<GenTable>
}
