package top.potmot.core.database.build

import top.potmot.core.meta.MappingTableMeta
import top.potmot.core.meta.columnType.ColumnTypeDefiner
import top.potmot.core.meta.columnType.getColumnTypeDefiner
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

class PostgreTableDefineBuilder : TableDefineBuilder() {
    override fun String.escape(): String =
        "\"${removePrefix("\"").removeSuffix("\"")}\""

    override fun getColumnTypeDefiner(): ColumnTypeDefiner = DataSourceType.PostgreSQL.getColumnTypeDefiner()

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
