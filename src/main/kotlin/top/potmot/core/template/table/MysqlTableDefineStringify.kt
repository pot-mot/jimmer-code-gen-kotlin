package top.potmot.core.template.table

import top.potmot.core.template.TemplateBuilder
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

private fun String.escape(): String =
    this.escape(DataSourceType.MySQL)

fun Collection<GenTableAssociationsView>.mysqlTablesStringify(): String =
    TemplateBuilder().apply {
        forEach {
            line(dropTable(it.name))
        }

        separate()

        forEach {
            lines(it.createTable())
            separate()
        }

        forEach {
            lines(it.fksStringify(DataSourceType.MySQL))
            separate()
        }
    }.build()

fun GenTableAssociationsView.mysqlTableStringify(): String =
    TemplateBuilder().apply {
        line(dropTable(name))
        separate()
        lines(createTable())
        separate()
        lines(fksStringify(DataSourceType.MySQL))
    }.build()

private fun GenTableAssociationsView.createTable(): String =
    TemplateBuilder().apply {
        line("CREATE TABLE ${name.escape()} (")

        increaseIndentation()
        lines(columns.map { it.mysqlColumnStringify().trim() }) {"$it,"}
        line(pkStringify())
        decreaseIndentation()

        append(
            """
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '$comment'
  ROW_FORMAT = Dynamic;
        """
        )
    }.build()

private fun dropTable(name: String) =
    "DROP TABLE IF EXISTS ${name.escape()};"

fun GenTableAssociationsView.TargetOf_columns.mysqlColumnStringify(): String =
    "${name.escape()} ${mysqlType()}" +
            " ${if (typeNotNull) "NOT NULL" else ""}" +
            " ${if (partOfPk && autoIncrement) "AUTO_INCREMENT" else ""}" +
            " ${
                if (!defaultValue.isNullOrBlank())
                    "DEFAULT $defaultValue"
                else if (!typeNotNull)
                    "DEFAULT NULL"
                else ""
            }" +
            " COMMENT '$comment'"

// TODO 处理数据库中特殊类型
fun GenTableAssociationsView.TargetOf_columns.mysqlType(): String =
    if (typeCode == Types.LONGVARCHAR || typeCode == Types.LONGNVARCHAR) {
        "LONGTEXT"
    } else if (type.uppercase() == "ENUM"){
        "VARCHAR(50)"
    } else {
        fullType
    }

private fun GenTableAssociationsView.pkColumn(): GenTableAssociationsView.TargetOf_columns? =
    this.columns.firstOrNull { it.partOfPk }

private fun GenTableAssociationsView.pkStringify(): String =
    "PRIMARY KEY (${(pkColumn()?.name ?: "").escape()}) USING BTREE"
