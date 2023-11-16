package top.potmot.core.template.table

import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

fun Collection<GenTableAssociationsView>.postgreTablesStringify(): String =
    PostgreTableContext().apply {
        forEach {
            line(dropTable(it.name))
        }

        separate()

        forEach {
            lines(createTable(it))
            separate()
            lines(it.commentsStringify())
            separate()
        }

        forEach {
            lines(it.associationsStringify())
            separate()
        }
    }.build()

fun GenTableAssociationsView.postgreTableStringify(): String =
    PostgreTableContext().apply {
        line(dropTable(name))
        separate()
        lines(createTable(this@postgreTableStringify))
        separate()
        lines(commentsStringify())
        separate()
        lines(associationsStringify())
    }.build()


class PostgreTableContext : TableContext() {
    override fun String.escape(): String =
        "\"${removePrefix("\"").removeSuffix("\"")}\""

    override fun GenTableAssociationsView.TargetOf_columns.columnStringify(): String {
        val sb = StringBuilder()

        sb.append(name.escape())
            .append(' ')

        sb.append(typeStringify())

        if (partOfPk) {
            sb.append(" PRIMARY KEY")

            if (!defaultValue.isNullOrBlank()) {
                sb.append(" DEFAULT ").append(defaultValue)
            }
        } else {
            if (typeNotNull) {
                sb.append(" NOT NULL")
            }

            if (!defaultValue.isNullOrBlank()) {
                sb.append(" DEFAULT ").append(defaultValue)
            } else if (!typeNotNull) {
                sb.append(" DEFAULT NULL")
            }
        }

        return sb.toString()
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
        mappingTable: Boolean
    ): String =
        if (autoIncrement && partOfPk && !mappingTable) {
            when (typeCode) {
                Types.TINYINT -> "SMALLSERIAL"
                Types.SMALLINT -> "SMALLSERIAL"
                Types.INTEGER -> "SERIAL"
                Types.BIGINT -> "BIGSERIAL"
                else -> "SERIAL"
            }
        } else {
            type
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
            append,
            mappingTableName,
            mappingTableComment,
            mappingSourceColumnName,
            mappingTargetColumnName
        ) + "\n\n${createTableComment(mappingTableName, mappingTableComment)}"
    }
}
