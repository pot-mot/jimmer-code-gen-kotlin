package top.potmot.core.database.generate.mysql

import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

private val MYSQL_TABLE_DEFINE_BUILDER = MysqlTableDefineBuilder()

class MysqlTableDefineGenerator: TableDefineGenerator() {
    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        MYSQL_TABLE_DEFINE_BUILDER.let {
            buildString {
                tables.forEach {table ->
                    appendLine(it.dropTable(table.name) + ";")
                }

                appendLine()

                tables.forEach {table ->
                    val createTable = it.createTable(table)
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
