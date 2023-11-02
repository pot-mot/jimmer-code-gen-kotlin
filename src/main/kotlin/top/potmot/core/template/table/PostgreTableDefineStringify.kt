package top.potmot.core.template.table

import top.potmot.config.GenConfig
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

private fun String.escape(): String =
    this.escape(DataSourceType.PostgreSQL)

fun GenTableAssociationsView.postgreTableStringify(): String {
    var base = """-- ----------------------------
-- Table structure for $name
-- ----------------------------
DROP TABLE IF EXISTS ${name.escape()} CASCADE;
CREATE TABLE ${name.escape()}
(
${columns.map { it.postgreColumnStringify() }.joinToString(",\n") { "    ${it.trim()}" }}       
);"""

    commentStringify().joinToString("\n") { "$it;" }.takeIf { it.isNotEmpty() }?.let {
        base += "\n\n" + it
    }


    fkStringify().joinToString("\n") { "$it;" }.takeIf { it.isNotEmpty() }?.let {
        base += "\n\n" + it
    }

    return base
}

fun GenTableAssociationsView.TargetOf_columns.postgreColumnStringify(): String =
    if (partOfPk) {
        "${name.escape()} ${pkColumnType()} PRIMARY KEY"
    } else {
        "${name.escape()} $type" +
                " ${if (notNull) "NOT NULL" else ""}" +
                " ${if (!defaultValue.isNullOrBlank()) "DEFAULT $defaultValue" else if (!notNull) "DEFAULT NULL" else ""}"
    }

private fun GenTableAssociationsView.TargetOf_columns.pkColumnType(): String =
    when (this.typeCode) {
        Types.TINYINT -> "SMALLSERIAL"
        Types.SMALLINT -> "SMALLSERIAL"
        Types.INTEGER -> "SERIAL"
        Types.BIGINT -> "BIGSERIAL"
        else -> "SERIAL"
    }

private fun GenTableAssociationsView.fkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    this.columns.filter { it.partOfFk }

private fun GenTableAssociationsView.fkStringify(): List<String> {
    if (!GenConfig.tableDefineWithFk) return emptyList()

    val list = mutableListOf<String>()

    for (fkColumn in fkColumns()) {
        val indexName = "idx_${name}_${fkColumn.name}"

        list += "CREATE ${if (fkColumn.partOfUniqueIdx) "UNIQUE " else ""}INDEX ${indexName.escape()} ON ${name.escape()} (${fkColumn.name.escape()})"

        for (outAssociation in fkColumn.outAssociations) {
            list += "ALTER TABLE ${name.escape()} ADD " + fkColumn.createFkConstraint(
                indexName,
                outAssociation,
                DataSourceType.PostgreSQL
            )
        }
    }

    return list
}

private fun GenTableAssociationsView.commentStringify(): List<String> {
    val list = mutableListOf<String>()

    if (comment.isNotEmpty()) {
        list += "COMMENT ON TABLE ${name.escape()} IS '$comment'"
    }

    columns.forEach {
        if (it.comment.isNotEmpty()) {
            list += "COMMENT ON COLUMN ${name.escape()}.${it.name.escape()} IS '${it.comment}'"
        }
    }

    return list
}
