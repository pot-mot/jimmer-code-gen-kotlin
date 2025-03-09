package top.potmot.core.database.generate

import top.potmot.core.database.generate.utils.toFlat
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByTables
import top.potmot.enumeration.GenerateTag
import top.potmot.enumeration.TableType
import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateException

private const val allTableFileName = "all-tables.sql"

interface TableDefineGenerator {
    private fun formatFilePath(table: GenTableGenerateView): String = buildString {
        append("ddl/")
        if (!table.subPackagePath.isNullOrBlank()) {
            append("${table.subPackagePath}/")
        }
        append("${table.name}.sql")
    }


    fun stringify(
        tables: Iterable<GenTableGenerateView>,
    ): String

    @Throws(ColumnTypeException::class, GenerateException::class)
    fun generate(
        tables: Iterable<GenTableGenerateView>,
    ): List<GenerateFile> {
        val result = mutableListOf<GenerateFile>()

        val flatTables = tables
            .filter { it.type != TableType.SUPER_TABLE }
            .sortedBy { it.name }
            .map { it.toFlat() }

        result += createGenerateFileByTables(
            flatTables,
            "ddl/$allTableFileName",
            stringify(flatTables),
            listOf(GenerateTag.BackEnd, GenerateTag.DDL)
        )

        flatTables.forEach {
            result += GenerateFile(
                it,
                formatFilePath(it),
                stringify(listOf(it)),
                listOf(GenerateTag.BackEnd, GenerateTag.DDL)
            )
        }

        return result
    }
}
