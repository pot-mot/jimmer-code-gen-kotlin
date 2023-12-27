package top.potmot.core.database.build

import top.potmot.core.meta.MappingTableMeta
import top.potmot.model.GenColumn
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

class MysqlTableDefineBuilder : TableDefineBuilder() {
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

    override fun getDatabaseTypeString(
        column: GenColumn,
        mappingTable: Boolean,
    ): String =
        if (column.typeCode == Types.LONGVARCHAR || column.typeCode == Types.LONGNVARCHAR) {
            "LONGTEXT"
        } else if (column.type.uppercase().startsWith("ENUM")) {
            "VARCHAR(50)"
        } else {
            column.fullType
        }

    override fun GenTableAssociationsView.TargetOf_columns.columnStringify(): String {
        val sb = StringBuilder()

        sb.append(name.escape())
            .append(' ')
            .append(getDatabaseTypeString(this.toEntity()))

        if (partOfPk) {
            if (autoIncrement) {
                sb.append(" AUTO_INCREMENT")
            }
        }
        if (typeNotNull) {
            sb.append(" NOT NULL")
        }

        if (!partOfPk) {
            if (!defaultValue.isNullOrBlank()) {
                sb.append(" DEFAULT ").append(defaultValue)
            } else if (!typeNotNull) {
                sb.append(" DEFAULT NULL")
            }
        }

        if (comment.isNotEmpty()) {
            sb.append(" COMMENT '${comment}'")
        }

        return sb.toString()
    }
}