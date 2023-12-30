package top.potmot.core.database.generate

import top.potmot.model.dto.GenTableAssociationsView

abstract class TableDefineGenerator {
    protected open fun formatFileName(name: String): String =
        "$name.sql"

    protected abstract fun stringify(table: GenTableAssociationsView): String

    protected abstract fun stringify(tables: Collection<GenTableAssociationsView>): String

    fun generate(
        table: GenTableAssociationsView
    ): Pair<String, String> =
        Pair(formatFileName(table.name), stringify(table))

    fun generate(
        tables: Collection<GenTableAssociationsView>,
        withSingleTable: Boolean = true
    ): List<Pair<String, String>> =
        listOf(
            Pair(formatFileName("all-tables"), stringify(tables))
        ).let {list ->
            if (withSingleTable) {
                list + tables.map { generate(it) }
            } else {
                list
            }
        }

}
