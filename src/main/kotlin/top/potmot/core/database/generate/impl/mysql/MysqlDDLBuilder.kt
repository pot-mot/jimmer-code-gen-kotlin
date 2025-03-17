package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.builder.DDLBuilder
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.entity.dto.GenTableGenerateView

object MysqlDDLBuilder : DDLBuilder(
    MysqlIdentifierProcessor,
    MysqlColumnTypeDefiner,
) {
    private fun defaultParams(comment: String): String =
        """
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '${comment}'
  ROW_FORMAT = Dynamic"""

    override fun createTable(
        table: GenTableGenerateView,
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

    override fun columnStringify(column: GenTableGenerateView.TargetOf_columns): String =
        listOf(
            super.columnStringify(column),
            if (column.autoIncrement) " AUTO_INCREMENT" else "",
            if (column.comment.isNotBlank()) " COMMENT '${column.comment}'" else ""
        ).joinToString("")
}
