package top.potmot.core.template.ddl

import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationView
import java.sql.Types

fun GenTableAssociationView.postgreTableStringify(): String {
    return """
DROP TABLE IF EXISTS $name;
CREATE TABLE $name
(
${columns.map { it.postgreColumnStringify() }.joinToString(",\n") { "    ${it.trim()}" }}       
);

${commentStringify().joinToString("\n") { "$it;" }}

${fkStringify().joinToString("\n") { "$it;" }}
"""
}

fun GenTableAssociationView.TargetOf_columns.postgreColumnStringify(): String =
    if (isPk) {
        "$name ${pkColumnType()} PRIMARY KEY"
    } else {
        "$name $type" +
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

        list += "CREATE ${if (fkColumn.isUnique) "UNIQUE " else ""}INDEX $indexName ON $name (${fkColumn.name})"

        for (outAssociation in fkColumn.outAssociations) {
            list += "ALTER TABLE $name ADD " + fkColumn.createFkConstraint(indexName, outAssociation, DataSourceType.PostgreSQL)
        }
    }

    return list
}

private fun GenTableAssociationView.commentStringify(): List<String> {
    val list = mutableListOf<String>()

    list += "COMMENT ON TABLE $name IS '$comment'"

    columns.forEach {
        list += "COMMENT ON COLUMN ${name}.${it.name} IS '$comment'"
    }

    return list
}
