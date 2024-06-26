package top.potmot.core.database.generate.impl.postgres

import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

object PostgresTableDefineGenerator : TableDefineGenerator() {
    override fun stringify(
        tables: Collection<GenTableAssociationsView>,
    ): String =
        PostgresTableDefineBuilder.let { builder ->
            buildString {
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

                tables.forEach { table ->
                    appendLines(builder.associationsStringify(table)) { "$it;\n" }
                }
            }
        }
}
