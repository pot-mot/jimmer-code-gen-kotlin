package top.potmot.core.database.build

import top.potmot.core.meta.MappingTableMeta
import top.potmot.core.meta.columnType.ColumnTypeDefiner
import top.potmot.core.meta.columnType.getColumnTypeDefiner
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

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
