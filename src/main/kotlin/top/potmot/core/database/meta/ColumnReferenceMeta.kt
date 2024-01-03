package top.potmot.core.database.meta

import org.babyfish.jimmer.ImmutableObjects.isLoaded
import schemacrawler.schema.ColumnReference
import top.potmot.model.GenColumn
import top.potmot.model.GenSchema
import top.potmot.model.GenSchemaProps
import top.potmot.model.GenTable
import top.potmot.model.GenTableProps

data class ColumnReferenceNamePart(
    val schemaName: String?,
    val tableName: String,
    val columnName: String,
)

data class ColumnReferenceMetaPart(
    val schema: GenSchema?,
    val table: GenTable,
    val column: GenColumn
)

data class ColumnReferenceMeta(
    val source: ColumnReferenceMetaPart,
    val target: ColumnReferenceMetaPart
)

private fun ColumnReference.getSourceNames(): ColumnReferenceNamePart {
    val sourceNameList = foreignKeyColumn.fullName.split(".").reversed()

    val sourceSchemaName = sourceNameList[2]
    val sourceTableName = sourceNameList[1]
    val sourceColumnName = sourceNameList[0]

    return ColumnReferenceNamePart(
        sourceSchemaName,
        sourceTableName,
        sourceColumnName,
    )
}

private fun ColumnReference.getTargetNames(): ColumnReferenceNamePart {
    val targetNameList = primaryKeyColumn.fullName.split(".").reversed()

    val targetSchemaName = targetNameList[2]
    val targetTableName = targetNameList[1]
    val targetColumnName = targetNameList[0]

    return ColumnReferenceNamePart(
        targetSchemaName,
        targetTableName,
        targetColumnName,
    )
}

private fun ColumnReferenceNamePart.toMetaPart(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMetaPart? {
    val table = tableNameMap[tableName] ?: return null
    val column = table.columns.find { column -> column.name == columnName } ?: return null
    val schema = if (isLoaded(table, GenTableProps.SCHEMA)) table.schema else null

    if (schema != null && isLoaded(schema, GenSchemaProps.NAME) && schema.name != schemaName) return null

    return ColumnReferenceMetaPart(schema, table, column)
}

private fun ColumnReference.getSourceMeta(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMetaPart? =
    getSourceNames().toMetaPart(tableNameMap)

private fun ColumnReference.getTargetMeta(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMetaPart? =
    getTargetNames().toMetaPart(tableNameMap)

fun ColumnReference.getMeta(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMeta? =
    getSourceMeta(tableNameMap)?.let { sourceMeta ->
        getTargetMeta(tableNameMap)?.let { targetMeta ->
            ColumnReferenceMeta(
                sourceMeta,
                targetMeta
            )
        }
    }
