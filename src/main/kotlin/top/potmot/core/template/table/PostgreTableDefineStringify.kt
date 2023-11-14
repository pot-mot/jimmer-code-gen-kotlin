package top.potmot.core.template.table

import top.potmot.core.template.TemplateBuilder
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

private fun String.escape(): String =
    this.escape(DataSourceType.PostgreSQL)

fun Collection<GenTableAssociationsView>.postgreTablesStringify(): String =
    TemplateBuilder().apply {
        forEach {
            line(dropTable(it.name))
        }

        separate()

        forEach {
            lines(it.createTable())
            separate()
            lines(it.commentsStringify())
            separate()
        }

        forEach {
            lines(it.fksStringify(DataSourceType.PostgreSQL))
            separate()
        }
    }.build()

fun GenTableAssociationsView.postgreTableStringify(): String =
    TemplateBuilder().apply {
        line(dropTable(name))
        separate()
        lines(createTable())
        separate()
        lines(commentsStringify())
        separate()
        lines(fksStringify(DataSourceType.PostgreSQL))
    }.build()


private fun GenTableAssociationsView.createTable(): String =
    TemplateBuilder().apply {
        line("CREATE TABLE ${name.escape()} (")

        increaseIndentation()
        lines(columns.joinToString(",\n") { it.postgreColumnStringify().trim() })
        decreaseIndentation()

        line(");")
    }.build()

private fun dropTable(name: String) =
    "DROP TABLE IF EXISTS ${name.escape()} CASCADE;"

fun GenTableAssociationsView.TargetOf_columns.postgreColumnStringify(): String =
    if (partOfPk) {
        "${name.escape()} ${pkColumnType()} PRIMARY KEY"
    } else {
        "${name.escape()} $type" +
                " ${if (typeNotNull) "NOT NULL" else ""}" +
                " ${
                    if (!defaultValue.isNullOrBlank())
                        "DEFAULT $defaultValue"
                    else if (!typeNotNull)
                        "DEFAULT NULL"
                    else ""
                }"
    }

private fun GenTableAssociationsView.TargetOf_columns.pkColumnType(): String =
    when (this.typeCode) {
        Types.TINYINT -> "SMALLSERIAL"
        Types.SMALLINT -> "SMALLSERIAL"
        Types.INTEGER -> "SERIAL"
        Types.BIGINT -> "BIGSERIAL"
        else -> "SERIAL"
    }

private fun GenTableAssociationsView.commentsStringify(): List<String> {
    val list = mutableListOf<String>()

    if (comment.isNotEmpty()) {
        list += "COMMENT ON TABLE ${name.escape()} IS '$comment';"
    }

    columns.forEach {
        if (it.comment.isNotEmpty()) {
            list += "COMMENT ON COLUMN ${name.escape()}.${it.name.escape()} IS '${it.comment}';"
        }
    }

    return list
}
