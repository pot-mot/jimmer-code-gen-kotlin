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

data class ForeignKeyMeta(
    val sourceTableName: String,
    val sourceColumnNames: List<String>,
    val targetTableName: String,
    val targetColumnNames: List<String>,
    val name: String = createFkName(sourceTableName, sourceColumnNames, targetTableName, targetColumnNames),
)


fun GenAssociation.getMeta(): AssociationMeta =
    AssociationMeta(
        name = this.name,
        sourceTable = this.sourceTable,
        sourceColumns = this.columnReferences.map{it.sourceColumn},
        targetTable = this.targetTable,
        targetColumns = this.columnReferences.map{it.targetColumn},
    )

fun AssociationMeta.toFkMeta(): ForeignKeyMeta =
    ForeignKeyMeta(
        name = this.name,
        sourceTableName = this.sourceTable.name,
        sourceColumnNames = this.sourceColumns.map{it.name},
        targetTableName = this.targetTable.name,
        targetColumnNames = this.targetColumns.map{it.name}
    )

fun GenAssociation.toFkMeta(): ForeignKeyMeta =
    this.getMeta().toFkMeta()

fun createFkName(
    sourceTableName: String,
    sourceColumnNames: List<String>,
    targetTableName: String,
    targetColumnNames: List<String>,
): String =
    "fk_${sourceTableName.clearTableName()}_${sourceColumnNames.joinToString("_") { it.clearColumnName() }}" +
            "_${targetTableName.clearTableName()}_${targetColumnNames.joinToString("_") { it.clearColumnName() }}"
