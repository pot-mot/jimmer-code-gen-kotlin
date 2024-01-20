package top.potmot.core.database.meta

import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableComment
import top.potmot.core.entity.convert.clearTableName
import top.potmot.model.GenAssociation
import top.potmot.model.dto.ColumnTypeMeta
import top.potmot.model.dto.GenTableAssociationsView
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
        get() = createMappingColumnNames(sourceTableName, sourceColumnNames)

    val mappingTargetColumnNames
        get() = createMappingColumnNames(targetTableName, targetColumnNames)

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

    val sourceFk: ForeignKeyMeta
        get() = ForeignKeyMeta(
            name = "${name}_S",
            sourceTableName = name,
            sourceColumnNames = mappingSourceColumnNames,
            targetTableName = sourceTableName,
            targetColumnNames = sourceColumnNames,
        )

    val targetFk: ForeignKeyMeta
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
): String =
    "${sourceTableComment.clearTableComment()}与${targetTableComment.clearTableComment()}的映射关系表"

fun createMappingColumnName(
    tableName: String,
    columnName: String,
): String =
   "${tableName.clearTableName()}_${columnName.clearColumnName()}"

fun createMappingColumnNames(
    tableName: String,
    columnNames: List<String>,
): List<String> =
    columnNames.map { createMappingColumnName(tableName, it) }

fun GenAssociation.toMappingTableMeta(): MappingTableMeta =
    MappingTableMeta(
        name = this.name,
        sourceTableName = sourceTable.name,
        targetTableName = targetTable.name,
        sourceTableComment = sourceTable.comment,
        targetTableComment = targetTable.comment,
        sourceColumnNames = this.columnReferences.map { it.sourceColumn.name },
        targetColumnNames = this.columnReferences.map { it.targetColumn.name },
        columnTypes = this.columnReferences.map { it.sourceColumn.getTypeMeta() },
    )

private fun createMappingTableColumn(
    name: String,
    type: ColumnTypeMeta
): GenTableAssociationsView.TargetOf_columns =
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
        displaySize = type.displaySize,
        numericPrecision = type.numericPrecision,
        typeNotNull = type.typeNotNull,
        logicalDelete = false,
        partOfPk = true,
        autoIncrement = false,
        businessKey = false,
        comment = "",
        tableId = 0,
    )
