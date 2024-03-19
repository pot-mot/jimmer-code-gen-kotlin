package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.builder.TableDefineBuilder
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.model.dto.GenTableAssociationsView

object MysqlTableDefineBuilder : TableDefineBuilder(
    MysqlIdentifierProcessor,
    MysqlColumnTypeDefiner,
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
        fake: Boolean,
        otherLines: List<String>,
        append: String,
    ): List<String> {
        return super.createMappingTable(
            meta,
            fake,
            otherLines,
            append + defaultParams(meta.comment)
        )
    }

    override fun columnStringify(column: GenTableAssociationsView.TargetOf_columns): String =
        listOf(
            super.columnStringify(column),
            if (column.autoIncrement) " AUTO_INCREMENT" else "",
            if (column.comment.isNotBlank()) " COMMENT '${column.comment}'" else ""
        ).joinToString("")
}
