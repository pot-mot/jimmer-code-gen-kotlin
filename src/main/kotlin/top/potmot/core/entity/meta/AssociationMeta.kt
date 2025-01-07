package top.potmot.core.entity.meta

import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenTableConvertView

data class AssociationMeta(
    val association: GenAssociationConvertView,
    val sourceTable: GenTableConvertView,
    var sourceEntity: GenEntityDetailView? = null,
    val sourceColumns: List<GenTableConvertView.TargetOf_columns>,
    var sourceProperties: List<GenEntityDetailView.TargetOf_properties>? = null,
    val targetTable: GenTableConvertView,
    var targetEntity: GenEntityDetailView? = null,
    val targetColumns: List<GenTableConvertView.TargetOf_columns>,
    var targetProperties: List<GenEntityDetailView.TargetOf_properties>? = null,
) {
    fun reversed() =
        AssociationMeta(
            association = association.copy(type = association.type.reversed()),
            sourceTable = targetTable,
            sourceEntity = targetEntity,
            sourceColumns = targetColumns,
            sourceProperties = targetProperties,
            targetTable = sourceTable,
            targetEntity = sourceEntity,
            targetColumns = sourceColumns,
            targetProperties = sourceProperties,
        )
}

fun Iterable<GenAssociationConvertView>.toAssociationMetaIdMap(
    tableIdMap: Map<Long, GenTableConvertView>,
    columnIdMap: Map<Long, GenTableConvertView.TargetOf_columns>,
    tableIdEntityMap: Map<Long, GenEntityDetailView>,
): Map<Long, AssociationMeta> =
    map { association ->
        val sourceTable = tableIdMap[association.sourceTableId]!!
        val sourceEntity = tableIdEntityMap[association.sourceTableId]
        val sourceColumns = association.columnReferences.map { columnReference ->
            columnIdMap[columnReference.sourceColumnId]!!
        }
        val sourceColumnIds = sourceColumns.map { it.id }
        val sourceProperties = sourceEntity?.let { entity ->
            entity.properties.filter { it.columnId in sourceColumnIds }
        }

        val targetTable = tableIdMap[association.targetTableId]!!
        val targetEntity = tableIdEntityMap[association.targetTableId]
        val targetColumns = association.columnReferences.map { columnReference ->
            columnIdMap[columnReference.targetColumnId]!!
        }
        val targetColumnIds = targetColumns.map { it.id }
        val targetProperties = targetEntity?.let { entity ->
            entity.properties.filter { it.columnId in targetColumnIds }
        }

        AssociationMeta(
            association = association,
            sourceTable = sourceTable,
            sourceEntity = sourceEntity,
            sourceColumns = sourceColumns,
            sourceProperties = sourceProperties,
            targetTable = targetTable,
            targetEntity = targetEntity,
            targetColumns = targetColumns,
            targetProperties = targetProperties
        )
    }
        .associateBy { it.association.id }