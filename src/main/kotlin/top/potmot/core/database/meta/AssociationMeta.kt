package top.potmot.core.database.meta

import top.potmot.model.dto.GenTableAssociationsView

data class OutAssociationMeta(
    val association: GenTableAssociationsView.TargetOf_outAssociations,
    val sourceTable: GenTableAssociationsView,
    val sourceColumns: List<GenTableAssociationsView.TargetOf_columns>,
    val targetTable: GenTableAssociationsView.TargetOf_outAssociations.TargetOf_targetTable,
    val targetColumns: List<GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences.TargetOf_targetColumn>,
)

data class InAssociationMeta(
    val association: GenTableAssociationsView.TargetOf_inAssociations,
    val sourceTable: GenTableAssociationsView.TargetOf_inAssociations.TargetOf_sourceTable,
    val sourceColumns: List<GenTableAssociationsView.TargetOf_inAssociations.TargetOf_columnReferences.TargetOf_sourceColumn>,
    val targetTable: GenTableAssociationsView,
    val targetColumns: List<GenTableAssociationsView.TargetOf_columns>,
)
