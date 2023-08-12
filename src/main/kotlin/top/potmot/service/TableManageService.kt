package top.potmot.service

import top.potmot.model.GenTable

interface TableManageService {
    fun removeTableById(tableId: Long): Boolean

    fun removeTable(table: GenTable): Boolean

    fun removeTablesByIds(tableIds: Iterable<Long>): Int

    fun removeTables(tables: Iterable<GenTable>): Int
}
