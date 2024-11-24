package top.potmot.core.database.generate

import top.potmot.core.database.generate.utils.toFlat
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByTables
import top.potmot.enumeration.GenerateTag
import top.potmot.enumeration.TableType
import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateException

private const val allTableFileName = "all-tables"

abstract class TableDefineGenerator {
    protected open fun formatFileName(name: String): String =
        "$name.sql"

    protected abstract fun stringify(
        tables: Iterable<GenTableGenerateView>,
    ): String

    @Throws(ColumnTypeException::class, GenerateException::class)
    fun generate(
        tables: Iterable<GenTableGenerateView>,
    ): List<GenerateFile> {
        val result = mutableListOf<GenerateFile>()

        val flatTablePairs = tables
            .filter { it.type != TableType.SUPER_TABLE }
            .sortedBy { it.name }
            .map { it to it.toFlat() }

        result += createGenerateFileByTables(
            tables,
            "ddl/${formatFileName(allTableFileName)}",
            stringify(flatTablePairs.map { it.second }),
            listOf(GenerateTag.BackEnd, GenerateTag.Table)
        )

        flatTablePairs.forEach { (table, flatTable) ->
            result += GenerateFile(
                table,
                "ddl/${formatFileName(table.name)}",
                stringify(listOf(flatTable)),
                listOf(GenerateTag.BackEnd, GenerateTag.Table)
            )
        }

        return result
    }
}
