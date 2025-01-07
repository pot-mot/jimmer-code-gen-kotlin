package top.potmot.core.entity.meta

import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.entity.dto.GenTableConvertView

data class TableAssociationMeta(
    val outAssociationMetas: List<AssociationMeta>,
    val inAssociationMetas: List<AssociationMeta>,
)

fun GenTableConvertView.getAssociationMeta(
    tableIdMap: Map<Long, GenTableConvertView>,
    columnIdMap: Map<Long, GenTableConvertView.TargetOf_columns>,
    associationIdMap: Map<Long, GenAssociationConvertView>,
): TableAssociationMeta {
    val outAssociationMetas = outAssociationIds
        .mapNotNull { associationIdMap[it] }
        .map { association ->
            val sourceColumns = association.columnReferences.mapNotNull { columnReference ->
                columnIdMap[columnReference.sourceColumnId]
            }

            val targetTable = tableIdMap[association.targetTableId]!!

            val targetColumns = association.columnReferences.mapNotNull { columnReference ->
                columnIdMap[columnReference.targetColumnId]
            }

            AssociationMeta(
                association = association,
                sourceTable = this,
                sourceColumns = sourceColumns,
                targetTable = targetTable,
                targetColumns = targetColumns,
            )
        }

    val inAssociationMetas = inAssociationIds
        .mapNotNull { associationIdMap[it] }
        .map { association ->
            val targetColumns = association.columnReferences.mapNotNull { columnReference ->
                columnIdMap[columnReference.targetColumnId]
            }

            val sourceTable = tableIdMap[association.sourceTableId]!!

            val sourceColumns = association.columnReferences.mapNotNull { columnReference ->
                columnIdMap[columnReference.sourceColumnId]
            }

            AssociationMeta(
                association = association,
                targetTable = this,
                targetColumns = targetColumns,
                sourceTable = sourceTable,
                sourceColumns = sourceColumns,
            )
        }

    return TableAssociationMeta(
        outAssociationMetas = outAssociationMetas,
        inAssociationMetas = inAssociationMetas
    )
}