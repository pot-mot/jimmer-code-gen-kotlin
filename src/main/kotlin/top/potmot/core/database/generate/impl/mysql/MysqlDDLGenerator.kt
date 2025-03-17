package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.DDLGenerator
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

object MysqlDDLGenerator : DDLGenerator {
    private val builder = MysqlDDLBuilder

    override fun stringify(
        tables: Iterable<GenTableGenerateView>,
    ) = buildString {
        tables.forEach { table ->
            appendLine(builder.dropTable(table.name) + ";")
        }
        appendLine()

        tables.forEach { table ->
            val createTable = builder.createTable(table)
            appendBlock("$createTable;")
            if (createTable.isNotBlank()) appendLine()

            val indexLines = builder.indexLines(table)
            appendLines(indexLines) { "$it;" }
            if (indexLines.isNotEmpty()) appendLine()
        }

        val associationStrings = tables.flatMap { table ->
            builder.associationsStringify(table)
        }
        appendLines(associationStrings.distinct()) { "$it;\n" }
    }
}
