package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

object MysqlTableDefineGenerator: TableDefineGenerator() {
    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        MysqlTableDefineBuilder.let {
            buildString {
                tables.forEach {table ->
                    appendLine(it.dropTable(table.name) + ";")
                }

                appendLine()

                tables.forEach {table ->
                    val createTable = MysqlTableDefineBuilder.createTable(table)
                    appendBlock("$createTable;")
                    if (createTable.isNotBlank()) appendLine()

                    val indexLines = it.indexLines(table)
                    appendLines(indexLines) { "$it;"}
                    if (indexLines.isNotEmpty()) appendLine()
                }

                tables.forEach {table ->
                    appendLines(it.associationsStringify(table)) { "$it;\n"}
                }
            }
        }
}
