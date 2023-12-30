package top.potmot.core.database.generate

import top.potmot.core.database.build.MysqlTableDefineBuilder
import top.potmot.model.dto.GenTableAssociationsView

class MysqlTableDefineGenerator: TableDefineGenerator() {
    override fun stringify(table: GenTableAssociationsView): String =
        table.mysqlTableStringify()

    override fun stringify(tables: Collection<GenTableAssociationsView>): String =
        tables.mysqlTablesStringify()

    private fun GenTableAssociationsView.mysqlTableStringify(): String =
        MysqlTableDefineBuilder().apply {
            line(dropTable(name))
            separate()
            lines(createTable(this@mysqlTableStringify))
            separate()
            lines(associationsStringify())
        }.build()

    private fun Collection<GenTableAssociationsView>.mysqlTablesStringify(): String =
        MysqlTableDefineBuilder().apply {
            forEach {
                line(dropTable(it.name))
            }

            separate()

            forEach {
                lines(createTable(it))
                separate()
            }

            forEach {
                lines(it.associationsStringify())
                separate()
            }
        }.build()
}
