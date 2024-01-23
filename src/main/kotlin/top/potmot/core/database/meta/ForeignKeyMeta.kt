package top.potmot.core.database.meta

import top.potmot.model.GenAssociation

data class ForeignKeyMeta(
    val name: String,
    val sourceTableName: String,
    val sourceColumnNames: List<String>,
    val targetTableName: String,
    val targetColumnNames: List<String>,
    val onUpdate: String = "",
    val onDelete: String = "",
)

fun ForeignKeyMeta.reversed(): ForeignKeyMeta =
    ForeignKeyMeta(
        name,
        targetTableName,
        targetColumnNames,
        sourceTableName,
        sourceColumnNames,
        onUpdate,
        onDelete,
    )

fun GenAssociation.toFkMeta(): ForeignKeyMeta =
    ForeignKeyMeta(
        name = this.name,
        sourceTableName = this.sourceTable.name,
        sourceColumnNames = this.columnReferences.map { it.sourceColumn.name },
        targetTableName = this.targetTable.name,
        targetColumnNames = this.columnReferences.map { it.targetColumn.name },
        onUpdate = this.updateAction,
        onDelete = this.deleteAction,
    )
