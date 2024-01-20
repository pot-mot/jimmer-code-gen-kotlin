package top.potmot.core.database.meta

import org.babyfish.jimmer.sql.DissociateAction
import top.potmot.enumeration.AssociationType
import top.potmot.model.GenAssociation
import top.potmot.model.GenColumn
import top.potmot.model.GenTable

data class AssociationMeta(
    val name: String,
    val sourceTable: GenTable,
    val sourceColumns: List<GenColumn>,
    val targetTable: GenTable,
    val targetColumns: List<GenColumn>,
    val type: AssociationType,
    val dissociateAction: DissociateAction?,
    val onUpdate: String,
    val onDelete: String,
)

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

fun GenAssociation.getMeta(): AssociationMeta =
    AssociationMeta(
        name = this.name,
        sourceTable = this.sourceTable,
        sourceColumns = this.columnReferences.map { it.sourceColumn },
        targetTable = this.targetTable,
        targetColumns = this.columnReferences.map { it.targetColumn },
        type,
        dissociateAction,
        this.updateAction,
        this.deleteAction
    )

fun AssociationMeta.toFkMeta(): ForeignKeyMeta =
    ForeignKeyMeta(
        name = this.name,
        sourceTableName = this.sourceTable.name,
        sourceColumnNames = this.sourceColumns.map { it.name },
        targetTableName = this.targetTable.name,
        targetColumnNames = this.targetColumns.map { it.name },
        onUpdate = this.onUpdate,
        onDelete = this.onDelete,
    )

fun GenAssociation.toFkMeta(): ForeignKeyMeta =
    this.getMeta().toFkMeta()
