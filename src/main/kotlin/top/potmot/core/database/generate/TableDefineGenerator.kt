package top.potmot.core.database.generate

import top.potmot.core.database.generate.utils.toFlat
import top.potmot.enumeration.TableType
import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateTableDefineException
import top.potmot.entity.dto.GenTableGenerateView

abstract class TableDefineGenerator {
    protected open fun formatFileName(name: String): String =
        "$name.sql"

    protected abstract fun stringify(
        tables: Collection<GenTableGenerateView>,
    ): String

    @Throws(ColumnTypeException::class, GenerateTableDefineException::class)
    fun generate(
        tables: Collection<GenTableGenerateView>,
        withSingleTable: Boolean = true
    ): List<Pair<String, String>> {
        val fullTables = tables
            .filter { it.type != TableType.SUPER_TABLE }
            .map { it.toFlat() }

        val result = listOf(
            formatFileName("all-tables") to stringify(fullTables)
        )

        return if (withSingleTable) {
            result + fullTables.map { table ->
                formatFileName(table.name) to stringify(listOf(table))
            }.distinct().sortedBy { it.first }
        } else {
            result
        }
    }
}
