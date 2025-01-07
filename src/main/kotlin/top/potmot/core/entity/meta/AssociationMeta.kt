package top.potmot.core.entity.meta

import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.entity.dto.GenTableConvertView

data class AssociationMeta(
    val association: GenAssociationConvertView,
    val sourceTable: GenTableConvertView,
    val sourceColumns: List<GenTableConvertView.TargetOf_columns>,
    val targetTable: GenTableConvertView,
    val targetColumns: List<GenTableConvertView.TargetOf_columns>,
)
