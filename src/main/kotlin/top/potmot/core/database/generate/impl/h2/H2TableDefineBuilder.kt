package top.potmot.core.database.generate.impl.h2

import top.potmot.core.database.generate.builder.TableDefineBuilder
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.entity.dto.GenTableGenerateView

object H2TableDefineBuilder : TableDefineBuilder(
    H2IdentifierProcessor,
    H2ColumnTypeDefiner,
) {
    private fun createTableComment(name: String, comment: String): String? =
        if (comment.isNotEmpty()) {
            "COMMENT ON TABLE ${identifiers.tableName(name)} IS '${comment}'"
        } else {
            null
        }

    private fun createColumnComment(tableName: String, columnName: String, comment: String): String? =
        if (comment.isNotEmpty()) {
            "COMMENT ON COLUMN ${identifiers.tableName(tableName)}.${identifiers.columnName(columnName)} IS '${comment}'"
        } else {
            null
        }

    fun commentLines(table: GenTableGenerateView): List<String> {
        val list = mutableListOf<String>()

        createTableComment(table.name, table.comment)?.let { list += it }

        list += table.columns.mapNotNull { column ->
            createColumnComment(table.name, column.name, column.comment)
        }

        return list
    }

    override fun dropTable(name: String, append: String): String =
        super.dropTable(name, append) + " CASCADE"

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
            append,
        ) + "${createTableComment(meta.name, meta.comment)}"
    }

    override fun columnStringify(column: GenTableGenerateView.TargetOf_columns): String =
        listOf(
            super.columnStringify(column),
            if (column.autoIncrement) " AUTO_INCREMENT" else ""
        ).joinToString("")
}
