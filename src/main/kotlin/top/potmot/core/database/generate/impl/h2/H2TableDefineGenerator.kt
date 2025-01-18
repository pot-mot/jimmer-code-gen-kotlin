package top.potmot.core.database.generate.impl.h2

import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

object H2TableDefineGenerator : TableDefineGenerator() {
    private val builder = H2TableDefineBuilder

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

            val commentLines = builder.commentLines(table)
            appendLines(commentLines) { "$it;" }
            if (commentLines.isNotEmpty()) appendLine()
        }

        val associationStrings = tables.flatMap { table ->
            builder.associationsStringify(table)
        }
        appendLines(associationStrings.distinct()) { "$it;\n" }
    }
}
