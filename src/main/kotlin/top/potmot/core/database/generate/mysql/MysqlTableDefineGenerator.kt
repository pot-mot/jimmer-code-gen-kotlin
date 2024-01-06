package top.potmot.core.database.generate.mysql

import top.potmot.core.database.generate.TableDefineBuilder
import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.core.database.generate.ColumnTypeDefiner
import top.potmot.core.database.generate.getColumnTypeDefiner
import top.potmot.core.database.generate.getIdentifierFilter
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

class MysqlTableDefineGenerator: TableDefineGenerator() {
    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        tables.mysqlTablesStringify()

    private fun Collection<GenTableAssociationsView>.mysqlTablesStringify(): String =
        MysqlTableDefineBuilder().apply {
            forEach {
                line(dropTable(it.name) + ";")
            }

            separate()

            forEach {
                lines(createTable(it) + ";")
                lines(createIndexes(it)) { "$it;"}
                separate()
            }

            forEach {
                lines(it.associationsStringify()) { "$it;"}
                separate()
            }
        }.build()

    class MysqlTableDefineBuilder : TableDefineBuilder(DataSourceType.MySQL.getIdentifierFilter()) {
        override fun getColumnTypeDefiner(): ColumnTypeDefiner = DataSourceType.MySQL.getColumnTypeDefiner()

        override fun String.escape(): String =
            "`${removePrefix("`").removeSuffix("`")}`"

        private fun defaultParams(comment: String): String =
            """
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '${comment}'
  ROW_FORMAT = Dynamic"""

        override fun createTable(
            table: GenTableAssociationsView,
            otherLines: List<String>
        ): String =
            super.createTable(
                table,
                otherLines
            ) + defaultParams(table.comment)

        override fun createMappingTable(
            meta: MappingTableMeta,
            otherLines: List<String>,
            append: String,
        ): List<String> {
            return super.createMappingTable(
                meta,
                otherLines,
                append + defaultParams(meta.comment)
            )
        }

        override fun columnStringify(column: GenTableAssociationsView.TargetOf_columns): String =
            super.columnStringify(column) + if (column.autoIncrement) " AUTO_INCREMENT" else ""
    }
}
