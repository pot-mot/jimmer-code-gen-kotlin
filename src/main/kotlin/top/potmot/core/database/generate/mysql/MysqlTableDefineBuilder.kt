package top.potmot.core.database.generate.mysql

import top.potmot.core.database.generate.builder.TableDefineBuilder
import top.potmot.core.database.generate.getColumnTypeDefiner
import top.potmot.core.database.generate.getIdentifierFilter
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

class MysqlTableDefineBuilder : TableDefineBuilder(
    DataSourceType.MySQL.getIdentifierFilter(),
    DataSourceType.MySQL.getColumnTypeDefiner()
) {
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
