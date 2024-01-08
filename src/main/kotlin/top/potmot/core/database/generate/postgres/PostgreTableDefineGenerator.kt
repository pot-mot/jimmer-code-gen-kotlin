package top.potmot.core.database.generate.postgres

import top.potmot.core.database.generate.TableDefineBuilder
import top.potmot.core.database.generate.TableDefineGenerator
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.core.database.generate.ColumnTypeDefiner
import top.potmot.core.database.generate.getColumnTypeDefiner
import top.potmot.core.database.generate.getIdentifierFilter
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

class PostgreTableDefineGenerator : TableDefineGenerator() {
    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        tables.postgreTablesStringify()

    private fun Collection<GenTableAssociationsView>.postgreTablesStringify(): String =
        PostgreTableDefineBuilder().apply {
            forEach {
                line(dropTable(it.name) + ";")
            }

            separate()

            forEach {
                lines(createTable(it) + ";")
                lines(createIndexes(it)) { "$it;"}
                lines(it.commentsStringify()) { "$it;"}
                separate()
            }

            forEach {
                lines(it.associationsStringify()) { "$it;"}
                separate()
            }
        }.build()

    class PostgreTableDefineBuilder : TableDefineBuilder(DataSourceType.PostgreSQL.getIdentifierFilter()) {
        override fun String.escape(): String =
            "\"${removePrefix("\"").removeSuffix("\"")}\""

        override fun getColumnTypeDefiner(): ColumnTypeDefiner = DataSourceType.PostgreSQL.getColumnTypeDefiner()

        private fun createTableComment(name: String, comment: String): String? =
            if (comment.isNotEmpty()) {
                "COMMENT ON TABLE ${produceIdentifier(name)} IS '${comment}'"
            } else {
                null
            }

        private fun createColumnComment(tableName: String, columnName: String, comment: String): String? =
            if (comment.isNotEmpty()) {
                "COMMENT ON COLUMN ${produceIdentifier(tableName)}.${produceIdentifier(columnName)} IS '${comment}'"
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
            super.dropTable(name, append) + " CASCADE"

        override fun createMappingTable(
            meta: MappingTableMeta,
            otherLines: List<String>,
            append: String,
        ): List<String> {
            return super.createMappingTable(
                meta,
                otherLines,
                append,
            ) + "${createTableComment(meta.name, meta.comment)}"
        }
    }
}
