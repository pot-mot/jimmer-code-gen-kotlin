package top.potmot.core.template.ddl

import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationView

fun GenTableAssociationView.mysqlTableStringify(): String {
    return """
DROP TABLE IF EXISTS `$name`;
CREATE TABLE `$name`
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
    "`$name` ${fullType()}" +
            " ${if (isNotNull) "NOT NULL" else ""}" +
            " ${if (isPk && isAutoIncrement) "AUTO_INCREMENT" else ""}" +
            " ${if (!defaultValue.isNullOrBlank()) "DEFAULT $defaultValue" else if (!isNotNull) "DEFAULT NULL" else ""}" +
            " COMMENT '$comment'"

private fun GenTableAssociationView.pkColumn(): GenTableAssociationView.TargetOf_columns? =
    this.columns.firstOrNull { it.isPk }

private fun GenTableAssociationView.pkStringify(): String =
    "PRIMARY KEY (`${pkColumn()?.name ?: ""}`) USING BTREE"

private fun GenTableAssociationView.fkColumns(): List<GenTableAssociationView.TargetOf_columns> =
    this.columns.filter { it.isFk }

private fun GenTableAssociationView.fkStringify(): List<String> {
    val list = mutableListOf<String>()

    for (fkColumn in fkColumns()) {
        val indexName = "`idx_${name}_${fkColumn.name}`"

        list += "${if (fkColumn.isUnique) "UNIQUE " else ""}INDEX $indexName (`${fkColumn.name}`) USING BTREE"

        for (outAssociation in fkColumn.outAssociations) {
            list += fkColumn.createFkConstraint(indexName, outAssociation, DataSourceType.MySQL)
        }
    }

    return list
}
