package top.potmot.core.database.generate

import top.potmot.core.database.build.PostgreTableDefineBuilder
import top.potmot.model.dto.GenTableAssociationsView

class PostgreTableDefineGenerator : TableDefineGenerator() {
    override fun formatFileName(name: String): String =
        "[pg]$name.sql"

    override fun stringify(table: GenTableAssociationsView): String =
        table.postgreTableStringify()

    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        tables.postgreTablesStringify()

    private fun GenTableAssociationsView.postgreTableStringify(): String =
        PostgreTableDefineBuilder().apply {
            line(dropTable(name))
            separate()
            lines(createTable(this@postgreTableStringify))
            separate()
            lines(commentsStringify())
            separate()
            lines(associationsStringify())
        }.build()

    private fun Collection<GenTableAssociationsView>.postgreTablesStringify(): String =
        PostgreTableDefineBuilder().apply {
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
}
