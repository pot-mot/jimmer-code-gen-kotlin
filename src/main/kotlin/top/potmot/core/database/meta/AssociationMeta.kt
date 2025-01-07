package top.potmot.core.database.meta

import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.share.ReferenceColumn
import top.potmot.entity.dto.share.ReferenceTable

data class OutAssociationMeta(
    val association: GenAssociationSimpleView,
    val sourceTable: GenTableGenerateView,
    val sourceColumns: List<GenTableGenerateView.TargetOf_columns>,
    val targetTable: ReferenceTable,
    val targetColumns: List<ReferenceColumn>,
)
