package top.potmot.core.database.generate.impl.postgres

import top.potmot.core.database.generate.builder.TableDefineBuilder
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.model.dto.GenTableAssociationsView

object PostgresTableDefineBuilder : TableDefineBuilder(
    PostgresIdentifierProcessor,
    PostgresColumnTypeDefiner,
) {
    override fun String.escape(): String =
        "\"${removePrefix("\"").removeSuffix("\"")}\""

    private fun createTableComment(name: String, comment: String): String? =
        if (comment.isNotEmpty()) {
            "COMMENT ON TABLE ${processIdentifier(name)} IS '${comment}'"
        } else {
            null
        }

    private fun createColumnComment(tableName: String, columnName: String, comment: String): String? =
        if (comment.isNotEmpty()) {
            "COMMENT ON COLUMN ${processIdentifier(tableName)}.${processIdentifier(columnName)} IS '${comment}'"
        } else {
            null
        }

    fun commentLines(table: GenTableAssociationsView): List<String> {
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
}
