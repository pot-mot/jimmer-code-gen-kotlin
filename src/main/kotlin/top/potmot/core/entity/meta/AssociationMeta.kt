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

fun List<GenAssociationConvertView>.toAssociationMetaIdMap(
    tableIdMap: Map<Long, GenTableConvertView>,
    columnIdMap: Map<Long, GenTableConvertView.TargetOf_columns>,
): Map<Long, AssociationMeta> =
    map { association ->
        val sourceTable = tableIdMap[association.sourceTableId]!!
        val sourceColumns = association.columnReferences.mapNotNull { columnReference ->
            columnIdMap[columnReference.sourceColumnId]
        }

        val targetTable = tableIdMap[association.targetTableId]!!
        val targetColumns = association.columnReferences.mapNotNull { columnReference ->
            columnIdMap[columnReference.targetColumnId]
        }

        AssociationMeta(
            association = association,
            sourceTable = sourceTable,
            sourceColumns = sourceColumns,
            targetTable = targetTable,
            targetColumns = targetColumns,
        )
    }
        .associateBy { it.association.id }