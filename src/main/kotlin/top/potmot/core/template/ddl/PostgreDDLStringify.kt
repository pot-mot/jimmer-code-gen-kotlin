package top.potmot.core.template.ddl

import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationView
import java.sql.Types

private fun String.escape(): String =
    this.escape(DataSourceType.PostgreSQL)

fun GenTableAssociationView.postgreTableStringify(): String {
    return """
DROP TABLE IF EXISTS ${name.escape()};
CREATE TABLE ${name.escape()}
(
${columns.map { it.postgreColumnStringify() }.joinToString(",\n") { "    ${it.trim()}" }}       
);

${commentStringify().joinToString("\n") { "$it;" }}

${fkStringify().joinToString("\n") { "$it;" }}
"""
}

fun GenTableAssociationView.TargetOf_columns.postgreColumnStringify(): String =
    if (isPk) {
        "${name.escape()} ${pkColumnType()} PRIMARY KEY"
    } else {
        "${name.escape()} $type" +
                " ${if (isNotNull) "NOT NULL" else ""}" +
                " ${if (!defaultValue.isNullOrBlank()) "DEFAULT $defaultValue" else if (!isNotNull) "DEFAULT NULL" else ""}"
    }

private fun GenTableAssociationView.TargetOf_columns.pkColumnType(): String =
    when (this.typeCode) {
        Types.TINYINT -> "SMALLSERIAL"
        Types.SMALLINT -> "SMALLSERIAL"
        Types.INTEGER -> "SERIAL"
        Types.BIGINT -> "BIGSERIAL"
        else -> "SERIAL"
    }

private fun GenTableAssociationView.fkColumns(): List<GenTableAssociationView.TargetOf_columns> =
    this.columns.filter { it.isFk }

private fun GenTableAssociationView.fkStringify(): List<String> {
    val list = mutableListOf<String>()

    for (fkColumn in fkColumns()) {
        val indexName = "idx_${name}_${fkColumn.name}"

        list += "CREATE ${if (fkColumn.isUnique) "UNIQUE " else ""}INDEX ${indexName.escape()} ON ${name.escape()} (${fkColumn.name.escape()})"

        for (outAssociation in fkColumn.outAssociations) {
            list += "ALTER TABLE ${name.escape()} ADD " + fkColumn.createFkConstraint(indexName, outAssociation, DataSourceType.PostgreSQL)
        }
    }

    return list
}

private fun GenTableAssociationView.commentStringify(): List<String> {
    val list = mutableListOf<String>()

    list += "COMMENT ON TABLE ${name.escape()} IS '$comment'"

    columns.forEach {
        list += "COMMENT ON COLUMN ${name.escape()}.${it.name.escape()} IS '$comment'"
    }

    return list
}
