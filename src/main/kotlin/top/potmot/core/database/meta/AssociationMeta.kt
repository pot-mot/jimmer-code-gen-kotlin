package top.potmot.core.database.meta

import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableGenerateView

data class OutAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: GenTableGenerateView,
    val sourceColumns: List<GenTableGenerateView.TargetOf_columns>,
    val targetTable: GenTableGenerateView.TargetOf_outAssociations.TargetOf_targetTable,
    val targetColumns: List<GenTableGenerateView.TargetOf_outAssociations.TargetOf_columnReferences.TargetOf_targetColumn>,
)

data class InAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: GenTableGenerateView.TargetOf_inAssociations.TargetOf_sourceTable,
    val sourceColumns: List<GenTableGenerateView.TargetOf_inAssociations.TargetOf_columnReferences.TargetOf_sourceColumn>,
    val targetTable: GenTableGenerateView,
    val targetColumns: List<GenTableGenerateView.TargetOf_columns>,
)
