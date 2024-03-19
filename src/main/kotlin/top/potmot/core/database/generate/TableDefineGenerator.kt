package top.potmot.core.database.generate

import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateTableDefineException
import top.potmot.model.dto.GenTableAssociationsView

abstract class TableDefineGenerator {
    protected open fun formatFileName(name: String): String =
        "$name.sql"

    protected abstract fun stringify(
        tables: Collection<GenTableAssociationsView>,
    ): String

    @Throws(ColumnTypeException::class, GenerateTableDefineException::class)
    fun generate(
        tables: Collection<GenTableAssociationsView>,
        withSingleTable: Boolean = true
    ): List<Pair<String, String>> =
        listOf(
            formatFileName("all-tables") to stringify(tables)
        ).let { list ->
            if (withSingleTable) {
                list + tables.map { table ->
                    formatFileName(table.name) to stringify(listOf(table))
                }.distinct().sortedBy { it.first }
            } else {
                list
            }
        }
}
