package top.potmot.core.database.meta

import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableComment
import top.potmot.core.entity.convert.clearTableName
import top.potmot.model.GenAssociation

data class MappingTableMeta(
    var name: String,

    var sourceTableName: String,
    var sourceTableComment: String,
    var sourceColumnNames: List<String>,

    var targetTableName: String,
    var targetTableComment: String,
    var targetColumnNames: List<String>,

    var columnTypes: List<String>,
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

fun createMappingColumnNames(
    tableName: String,
    columnNames: List<String>,
): List<String> =
    columnNames.map { "${tableName.clearTableName()}_${it.clearColumnName()}" }

fun GenAssociation.toMappingTableMeta(): MappingTableMeta =
    MappingTableMeta(
        name = this.name,
        sourceTableName = sourceTable.name,
        targetTableName = targetTable.name,
        sourceTableComment = sourceTable.comment,
        targetTableComment = targetTable.comment,
        sourceColumnNames = this.columnReferences.map { it.sourceColumn.name },
        targetColumnNames = this.columnReferences.map { it.targetColumn.name },
        columnTypes = this.columnReferences.map { it.sourceColumn.type },
    )
