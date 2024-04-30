package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

object MysqlTableDefineGenerator : TableDefineGenerator() {
    override fun stringify(
        tables: Collection<GenTableAssociationsView>,
    ): String =
        MysqlTableDefineBuilder.let { builder ->
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
                }

                tables.forEach { table ->
                    appendLines(builder.associationsStringify(table)) { "$it;\n" }
                }
            }
        }
}
