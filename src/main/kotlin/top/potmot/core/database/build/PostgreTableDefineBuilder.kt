package top.potmot.core.database.build

import top.potmot.core.meta.MappingTableMeta
import top.potmot.model.GenColumn
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

class PostgreTableDefineBuilder : TableDefineBuilder() {
    override fun String.escape(): String =
        "\"${removePrefix("\"").removeSuffix("\"")}\""

    override fun GenTableAssociationsView.TargetOf_columns.columnStringify(): String {
        val sb = StringBuilder()

        sb.append(name.escape())
            .append(' ')

        sb.append(getDatabaseTypeString(this.toEntity()))

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

        return sb.toString()
    }

    override fun getDatabaseTypeString(
        column: GenColumn,
        mappingTable: Boolean,
    ): String =
        if (column.autoIncrement && column.partOfPk && !mappingTable) {
            when (column.typeCode) {
                Types.TINYINT -> "SMALLSERIAL"
                Types.SMALLINT -> "SMALLSERIAL"
                Types.INTEGER -> "SERIAL"
                Types.BIGINT -> "BIGSERIAL"
                else -> "SERIAL"
            }
        } else {
            column.type
        }

    private fun createTableComment(name: String, comment: String): String? =
        if (comment.isNotEmpty()) {
            "COMMENT ON TABLE ${name.escape()} IS '${comment}';"
        } else {
            null
        }

    private fun createColumnComment(tableName: String, columnName: String, comment: String): String? =
        if (comment.isNotEmpty()) {
            "COMMENT ON COLUMN ${tableName.escape()}.${columnName.escape()} IS '${comment}';"
        } else {
            null
        }

    fun GenTableAssociationsView.commentsStringify(): List<String> {
        val list = mutableListOf<String>()

        createTableComment(name, comment)?.let { list += it }

        list += columns.mapNotNull { column ->
            createColumnComment(name, column.name, column.comment)
        }

        return list
    }

    override fun dropTable(name: String, append: String): String =
        "DROP TABLE IF EXISTS ${name.escape()} CASCADE;"

    override fun createMappingTable(
        meta: MappingTableMeta,
        otherLines: List<String>,
        append: String,
    ): String {
        return super.createMappingTable(
            meta,
            otherLines,
            append,
        ) + "\n\n${createTableComment(meta.name, meta.comment)}"
    }
}
