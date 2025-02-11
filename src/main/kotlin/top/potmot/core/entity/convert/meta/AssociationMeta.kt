package top.potmot.core.entity.convert.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.core.database.meta.createMappingColumnName
import top.potmot.entity.sub.JoinColumnMeta
import top.potmot.entity.sub.JoinTableMeta
import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.core.entity.convert.EntityView
import top.potmot.core.entity.convert.PropertyView
import top.potmot.entity.dto.GenTableConvertView

data class AssociationMeta(
    val association: GenAssociationConvertView,
    val sourceTable: GenTableConvertView,
    var sourceEntity: EntityView? = null,
    val sourceColumns: List<GenTableConvertView.TargetOf_columns>,
    var sourceProperties: List<PropertyView>? = null,
    val targetTable: GenTableConvertView,
    var targetEntity: EntityView? = null,
    val targetColumns: List<GenTableConvertView.TargetOf_columns>,
    var targetProperties: List<PropertyView>? = null,
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

    fun toJoinColumns(
        identifiers: IdentifierProcessor,
    ) =
        sourceColumns.mapIndexed { index, sourceColumn ->
            val targetColumn = targetColumns[index]
            JoinColumnMeta(
                joinColumnName = identifiers.process(sourceColumn.name, IdentifierType.COLUMN_NAME),
                referencedColumnName = identifiers.process(targetColumn.name, IdentifierType.COLUMN_NAME),
                foreignKeyType = if (association.fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
            )
        }

    fun toJoinTable(
        identifiers: IdentifierProcessor,
    ) =
        JoinTableMeta(
            identifiers.process(association.name, IdentifierType.TABLE_NAME),
            sourceColumns.mapIndexed { index, sourceColumn ->
                val targetColumn = targetColumns[index]

                val sourceColumnName = createMappingColumnName(sourceTable.name, sourceColumn.name)
                val targetColumnName = createMappingColumnName(targetTable.name, targetColumn.name)

                Pair(
                    identifiers.process(sourceColumnName, IdentifierType.COLUMN_NAME),
                    identifiers.process(targetColumnName, IdentifierType.COLUMN_NAME),
                )
            },
            foreignKeyType = if (association.fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
        )
}

fun Iterable<GenAssociationConvertView>.toAssociationMetaIdMap(
    tableIdMap: Map<Long, GenTableConvertView>,
    columnIdMap: Map<Long, GenTableConvertView.TargetOf_columns>,
    tableIdEntityMap: Map<Long, EntityView>,
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




