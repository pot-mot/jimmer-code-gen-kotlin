package top.potmot.core.database.meta

import top.potmot.utils.string.clearColumnName
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.clearTableName
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.share.ColumnTypeMeta
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

    val columns: List<GenTableGenerateView.TargetOf_columns>
        get() {
            val sourceColumns = mutableListOf<GenTableGenerateView.TargetOf_columns>()
            val targetColumns = mutableListOf<GenTableGenerateView.TargetOf_columns>()

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

fun OutAssociationMeta<GenTableGenerateView, GenTableGenerateView.TargetOf_columns>.toMappingTableMeta() =
    MappingTableMeta(
        name = association.name,
        sourceTableName = sourceTable.name,
        targetTableName = targetTable.name,
        sourceTableComment = sourceTable.comment,
        targetTableComment = targetTable.comment,
        sourceColumnNames = sourceColumns.map { it.name },
        targetColumnNames = targetColumns.map { it.name },
        columnTypes = sourceColumns.map { it },
    )

private fun createMappingTableColumn(
    name: String,
    type: ColumnTypeMeta
) =
    GenTableGenerateView.TargetOf_columns(
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
    )
