package top.potmot.core.database.meta

import top.potmot.model.dto.GenAssociationSimpleView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.SourceColumn
import top.potmot.model.dto.SourceTable
import top.potmot.model.dto.TargetColumn
import top.potmot.model.dto.TargetTable

data class OutAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: GenTableAssociationsView,
    val sourceColumns: List<GenTableAssociationsView.TargetOf_columns>,
    val targetTable: TargetTable,
    val targetColumns: List<TargetColumn>,
)

data class InAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: SourceTable,
    val sourceColumns: List<SourceColumn>,
    val targetTable: GenTableAssociationsView,
    val targetColumns: List<GenTableAssociationsView.TargetOf_columns>,
)
