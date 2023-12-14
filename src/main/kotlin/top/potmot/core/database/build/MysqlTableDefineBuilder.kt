package top.potmot.core.database.build

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
        append: String
    ): String =
        super.createTable(
            table,
            otherLines,
            append = append + defaultParams(table.comment)
        )

    override fun createMappingTable(
        sourceTableName: String,
        sourceTableComment: String,
        sourceColumnName: String,
        targetTableName: String,
        targetTableComment: String,
        targetColumnName: String,
        columnType: String,
        lines: List<String>,
        append: String,
        mappingTableName: String,
        mappingTableComment: String,
        mappingSourceColumnName: String,
        mappingTargetColumnName: String
    ): String {
        return super.createMappingTable(
            sourceTableName,
            sourceTableComment,
            sourceColumnName,
            targetTableName,
            targetTableComment,
            targetColumnName,
            columnType,
            lines,
            append + defaultParams(mappingTableComment),
            mappingTableName,
            mappingTableComment,
            mappingSourceColumnName,
            mappingTargetColumnName
        )
    }

    override fun typeStringify(
        type: String,
        typeCode: Int,
        displaySize: Long,
        numericPrecision: Long,
        fullType: String,
        partOfPk: Boolean,
        partOfUniqueIdx: Boolean,
        partOfFk: Boolean,
        autoIncrement: Boolean,
        mappingTable: Boolean,
    ): String =
        if (typeCode == Types.LONGVARCHAR || typeCode == Types.LONGNVARCHAR) {
            "LONGTEXT"
        } else if (type.uppercase().startsWith("ENUM")) {
            "VARCHAR(50)"
        } else {
            fullType
        }

    override fun GenTableAssociationsView.TargetOf_columns.columnStringify(): String {
        val sb = StringBuilder()

        sb.append(name.escape())
            .append(' ')
            .append(typeStringify())

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
