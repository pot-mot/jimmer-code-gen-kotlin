package top.potmot.core.database.meta

import top.potmot.entity.dto.GenTableGenerateView

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

fun OutAssociationMeta<GenTableGenerateView, GenTableGenerateView.TargetOf_columns>.toFkMeta(): ForeignKeyMeta =
    ForeignKeyMeta(
        name = association.name,
        sourceTableName = this.sourceTable.name,
        sourceColumnNames = sourceColumns.map { it.name },
        targetTableName = this.targetTable.name,
        targetColumnNames = targetColumns.map { it.name },
        onUpdate = association.updateAction,
        onDelete = association.deleteAction,
    )
