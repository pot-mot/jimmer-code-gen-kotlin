package top.potmot.core.database.meta

import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.entity.dto.share.ReferenceColumn
import top.potmot.entity.dto.share.ReferenceTable

data class OutAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: GenTableAssociationsView,
    val sourceColumns: List<GenTableAssociationsView.TargetOf_columns>,
    val targetTable: ReferenceTable,
    val targetColumns: List<ReferenceColumn>,
)

data class InAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: ReferenceTable,
    val sourceColumns: List<ReferenceColumn>,
    val targetTable: GenTableAssociationsView,
    val targetColumns: List<GenTableAssociationsView.TargetOf_columns>,
)
