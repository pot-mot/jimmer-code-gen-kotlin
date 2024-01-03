package top.potmot.core.database.generate.mysql

import top.potmot.core.database.generate.TableDefineBuilder
import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.core.database.generate.ColumnTypeDefiner
import top.potmot.core.database.generate.getColumnTypeDefiner
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

class MysqlTableDefineGenerator: TableDefineGenerator() {
    override fun stringify(table: GenTableAssociationsView): String =
        table.mysqlTableStringify()

    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        tables.mysqlTablesStringify()

    private fun GenTableAssociationsView.mysqlTableStringify(): String =
        MysqlTableDefineBuilder().apply {
            line(dropTable(name))
            separate()
            lines(createTable(this@mysqlTableStringify))
            separate()
            lines(associationsStringify())
        }.build()

    private fun Collection<GenTableAssociationsView>.mysqlTablesStringify(): String =
        MysqlTableDefineBuilder().apply {
            forEach {
                line(dropTable(it.name))
            }

            separate()

            forEach {
                lines(createTable(it))
                separate()
            }

            forEach {
                lines(it.associationsStringify())
                separate()
            }
        }.build()

    class MysqlTableDefineBuilder : TableDefineBuilder() {
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
            otherLines: List<String>,
            append: String,
            withIndex: Boolean
        ): String =
            super.createTable(
                table,
                otherLines,
                append = append + defaultParams(table.comment),
                withIndex
            )

        override fun createMappingTable(
            meta: MappingTableMeta,
            otherLines: List<String>,
            append: String,
        ): String {
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
