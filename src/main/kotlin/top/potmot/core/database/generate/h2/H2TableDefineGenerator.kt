package top.potmot.core.database.generate.h2

import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

object H2TableDefineGenerator : TableDefineGenerator() {
    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        H2TableDefineBuilder.let {
            buildString {
                tables.forEach { table ->
                    appendLine(it.dropTable(table.name) + ";")
                }

                appendLine()

                tables.forEach { table ->
                    val createTable = it.createTable(table)
                    appendBlock("$createTable;")
                    if (createTable.isNotBlank()) appendLine()

                    val indexLines = it.indexLines(table)
                    appendLines(indexLines) { "$it;"}
                    if (indexLines.isNotEmpty()) appendLine()

                    val commentLines = it.commentLines(table)
                    appendLines(commentLines) { "$it;" }
                    if (commentLines.isNotEmpty()) appendLine()
                }

                tables.forEach {table ->
                    appendLines(it.associationsStringify(table)) { "$it;\n"}
                }
            }
        }
}
