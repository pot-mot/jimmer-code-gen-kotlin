package top.potmot.core.meta

import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableName
import top.potmot.model.GenAssociation
import top.potmot.model.GenColumn
import top.potmot.model.GenTable

data class AssociationMeta(
    val name: String,
    val sourceTable: GenTable,
    val sourceColumns: List<GenColumn>,
    val targetTable: GenTable,
    val targetColumns: List<GenColumn>
)

fun GenAssociation.getMeta(): AssociationMeta =
    AssociationMeta(
        name = this.name,
        sourceTable = this.sourceTable,
        sourceColumns = this.columnReferences.map{it.sourceColumn},
        targetTable = this.targetTable,
        targetColumns = this.columnReferences.map{it.targetColumn},
    )


fun getFkName(
    sourceTableName: String,
    sourceColumnNames: List<String>,
    targetTableName: String,
    targetColumnNames: List<String>,
): String =
    "fk_${sourceTableName.clearTableName()}_${sourceColumnNames.joinToString("_") { it.clearColumnName() }}" +
            "_${targetTableName.clearTableName()}_${targetColumnNames.joinToString("_") { it.clearColumnName() }}"


fun getMappingTableName(
    sourceTableName: String,
    targetTableName: String,
    sourceColumnName: String = "",
    targetColumnName: String = "",
): String =
    "${sourceTableName.clearTableName()}_${targetTableName.clearTableName()}_mapping"
