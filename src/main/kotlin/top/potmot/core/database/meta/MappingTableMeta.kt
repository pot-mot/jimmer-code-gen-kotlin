package top.potmot.core.database.meta

import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableComment
import top.potmot.core.entity.convert.clearTableName
import top.potmot.model.GenAssociation

data class MappingTableMeta(
    var sourceTableName: String,
    var sourceTableComment: String,
    var sourceColumnNames: List<String>,

    var targetTableName: String,
    var targetTableComment: String,
    var targetColumnNames: List<String>,

    var columnTypes: List<String>,

    var name: String = createMappingTableName(sourceTableName, targetTableName, sourceColumnNames, targetColumnNames)
) {
    val comment
        get() = createMappingTableComment(sourceTableComment, targetTableComment)

    val mappingSourceColumnNames
        get() = createMappingColumnNames(sourceTableName, sourceColumnNames)

    val mappingTargetColumnNames
        get() = createMappingColumnNames(targetTableName, targetColumnNames)

    val columnLines: List<String>
        get() {
            val sourceColumnLines = mutableListOf<String>()
            val targetColumnLines = mutableListOf<String>()

            columnTypes.mapIndexed { index, type ->
                sourceColumnLines += "${mappingSourceColumnNames[index]} $type NOT NULL"
                targetColumnLines += "${mappingTargetColumnNames[index]} $type NOT NULL"
            }

            return sourceColumnLines + targetColumnLines
        }

    val commonFkName: String
        get() = createFkName(
            sourceTableName,
            sourceColumnNames,
            targetTableName,
            targetColumnNames
        )

    val sourceFk: ForeignKeyMeta
        get() = ForeignKeyMeta(
            sourceTableName = name,
            sourceColumnNames = mappingSourceColumnNames,
            targetTableName = sourceTableName,
            targetColumnNames = sourceColumnNames,
            name = "${commonFkName}_SOURCE",
        )

    val targetFk: ForeignKeyMeta
        get() = ForeignKeyMeta(
            sourceTableName = name,
            sourceColumnNames = mappingTargetColumnNames,
            targetTableName = targetTableName,
            targetColumnNames = targetColumnNames,
            name = "${commonFkName}_TARGET",
        )
}

fun createMappingTableName(
    sourceTableName: String,
    targetTableName: String,
    sourceColumnNames: List<String> = emptyList(),
    targetColumnNames: List<String> = emptyList(),
): String =
    "${sourceTableName.clearTableName()}_${targetTableName.clearTableName()}_mapping"

fun createMappingTableComment(
    sourceTableComment: String,
    targetTableComment: String,
): String =
    "${sourceTableComment.clearTableComment()}与${targetTableComment.clearTableComment()}的映射关系表"

fun createMappingColumnNames(
    tableName: String,
    columnNames: List<String>,
): List<String> =
    columnNames.map { "${tableName.clearTableName()}_${it.clearColumnName()}" }

fun GenAssociation.toMappingTableMeta(): MappingTableMeta =
    MappingTableMeta(
        sourceTableName = sourceTable.name,
        targetTableName = targetTable.name,
        sourceTableComment = sourceTable.comment,
        targetTableComment = targetTable.comment,
        sourceColumnNames = this.columnReferences.map { it.sourceColumn.name },
        targetColumnNames = this.columnReferences.map { it.targetColumn.name },
        columnTypes = this.columnReferences.map { it.sourceColumn.type },
    )
