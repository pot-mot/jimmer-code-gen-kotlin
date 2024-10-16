package top.potmot.core.database.meta

import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.share.ReferenceColumn
import top.potmot.entity.dto.share.ReferenceTable

data class OutAssociationMeta<T, C>(
    val association: GenAssociationSimpleView,
    val sourceTable: T,
    val sourceColumns: List<C>,
    val targetTable: ReferenceTable,
    val targetColumns: List<ReferenceColumn>,
)

data class InAssociationMeta<T, C>(
    val association: GenAssociationSimpleView,
    val sourceTable: ReferenceTable,
    val sourceColumns: List<ReferenceColumn>,
    val targetTable: T,
    val targetColumns: List<C>,
)
