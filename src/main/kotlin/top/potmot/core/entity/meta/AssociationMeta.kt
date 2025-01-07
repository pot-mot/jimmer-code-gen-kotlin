package top.potmot.core.entity.meta

import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.share.ReferenceColumn
import top.potmot.entity.dto.share.ReferenceTable

data class OutAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: GenTableConvertView,
    val sourceColumns: List<GenTableConvertView.TargetOf_columns>,
    val targetTable: ReferenceTable,
    val targetColumns: List<ReferenceColumn>,
)

data class InAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: ReferenceTable,
    val sourceColumns: List<ReferenceColumn>,
    val targetTable: GenTableConvertView,
    val targetColumns: List<GenTableConvertView.TargetOf_columns>,
)
