package top.potmot.core.database.meta

import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableComment
import top.potmot.core.entity.convert.clearTableName
import top.potmot.entity.GenAssociation
import top.potmot.entity.dto.ColumnTypeMeta
import top.potmot.entity.dto.GenTableAssociationsView
import java.time.LocalDateTime

data class MappingTableMeta(
    var name: String,

    var sourceTableName: String,
    var sourceTableComment: String,
    var sourceColumnNames: List<String>,

    var targetTableName: String,
    var targetTableComment: String,
    var targetColumnNames: List<String>,

    var columnTypes: List<ColumnTypeMeta>,
) {
    val comment
        get() = createMappingTableComment(sourceTableComment, targetTableComment)

    val mappingSourceColumnNames
        get() = sourceColumnNames.map { createMappingColumnName(sourceTableName, it) }

    val mappingTargetColumnNames
        get() = targetColumnNames.map { createMappingColumnName(targetTableName, it) }

    val columns: List<GenTableAssociationsView.TargetOf_columns>
        get() {
            val sourceColumns = mutableListOf<GenTableAssociationsView.TargetOf_columns>()
            val targetColumns = mutableListOf<GenTableAssociationsView.TargetOf_columns>()

            columnTypes.mapIndexed { index, type ->
                sourceColumns += createMappingTableColumn(mappingSourceColumnNames[index], type)
                targetColumns += createMappingTableColumn(mappingTargetColumnNames[index], type)
            }

            return sourceColumns + targetColumns
        }

    val sourceFk
        get() = ForeignKeyMeta(
            name = "${name}_S",
            sourceTableName = name,
            sourceColumnNames = mappingSourceColumnNames,
            targetTableName = sourceTableName,
            targetColumnNames = sourceColumnNames,
        )

    val targetFk
        get() = ForeignKeyMeta(
            name = "${name}_T",
            sourceTableName = name,
            sourceColumnNames = mappingTargetColumnNames,
            targetTableName = targetTableName,
            targetColumnNames = targetColumnNames,
        )
}

fun createMappingTableComment(
    sourceTableComment: String,
    targetTableComment: String,
) =
    "${sourceTableComment.clearTableComment()}与${targetTableComment.clearTableComment()}的映射关系表"

fun createMappingColumnName(
    tableName: String,
    columnName: String,
) =
    "${tableName.clearTableName()}_${columnName.clearColumnName()}"

fun GenAssociation.toMappingTableMeta() =
    MappingTableMeta(
        name = this.name,
        sourceTableName = sourceTable.name,
        targetTableName = targetTable.name,
        sourceTableComment = sourceTable.comment,
        targetTableComment = targetTable.comment,
        sourceColumnNames = this.columnReferences.map { it.sourceColumn.name },
        targetColumnNames = this.columnReferences.map { it.targetColumn.name },
        columnTypes = this.columnReferences.map { ColumnTypeMeta(it.sourceColumn) },
    )

fun OutAssociationMeta.toMappingTableMeta() =
    MappingTableMeta(
        name = association.name,
        sourceTableName = sourceTable.name,
        targetTableName = targetTable.name,
        sourceTableComment = sourceTable.comment,
        targetTableComment = targetTable.comment,
        sourceColumnNames = sourceColumns.map { it.name },
        targetColumnNames = targetColumns.map { it.name },
        columnTypes = sourceColumns.map { it.getTypeMeta() },
    )

private fun createMappingTableColumn(
    name: String,
    type: ColumnTypeMeta
) =
    GenTableAssociationsView.TargetOf_columns(
        id = 0,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        remark = "",
        orderKey = 0,
        name = name,
        typeCode = type.typeCode,
        overwriteByRaw = type.overwriteByRaw,
        rawType = type.rawType,
        dataSize = type.dataSize,
        numericPrecision = type.numericPrecision,
        typeNotNull = type.typeNotNull,
        partOfPk = true,
        autoIncrement = false,
        businessKey = false,
        logicalDelete = false,
        comment = "",
        tableId = 0,
    )
