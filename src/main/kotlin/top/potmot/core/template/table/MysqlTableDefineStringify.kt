package top.potmot.core.template.table

import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationView

private fun String.escape(): String =
    this.escape(DataSourceType.MySQL)

fun GenTableAssociationView.mysqlTableStringify(): String {
    return """-- ----------------------------
-- Table structure for $name
-- ----------------------------
DROP TABLE IF EXISTS ${name.escape()};
CREATE TABLE ${name.escape()}
(
${columns.map { it.mysqlColumnStringify() }.joinToString("\n") { "    ${it.trim()}," }}  
     ${pkStringify()}${if (fkColumns().isNotEmpty()) "," else ""}
${fkStringify().joinToString(",\n") { "    ${it.trim()}" }}
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '$comment'
  ROW_FORMAT = Dynamic;
"""
}

fun GenTableAssociationView.TargetOf_columns.mysqlColumnStringify(): String =
    "${name.escape()} ${fullType()}" +
            " ${if (notNull) "NOT NULL" else ""}" +
            " ${if (partOfPk && autoIncrement) "AUTO_INCREMENT" else ""}" +
            " ${if (!defaultValue.isNullOrBlank()) "DEFAULT $defaultValue" else if (!notNull) "DEFAULT NULL" else ""}" +
            " COMMENT '$comment'"

private fun GenTableAssociationView.pkColumn(): GenTableAssociationView.TargetOf_columns? =
    this.columns.firstOrNull { it.partOfPk }

private fun GenTableAssociationView.pkStringify(): String =
    "PRIMARY KEY (${(pkColumn()?.name ?: "").escape()}) USING BTREE"

private fun GenTableAssociationView.fkColumns(): List<GenTableAssociationView.TargetOf_columns> =
    this.columns.filter { it.partOfFk }

private fun GenTableAssociationView.fkStringify(): List<String> {
    val list = mutableListOf<String>()

    for (fkColumn in fkColumns()) {
        val indexName = "idx_${name}_${fkColumn.name}"

        list += "${if (fkColumn.partOfUniqueIdx) "UNIQUE " else ""}INDEX ${indexName.escape()} (${fkColumn.name.escape()}) USING BTREE"

        for (outAssociation in fkColumn.outAssociations) {
            list += fkColumn.createFkConstraint(indexName, outAssociation, DataSourceType.MySQL)
        }
    }

    return list
}
