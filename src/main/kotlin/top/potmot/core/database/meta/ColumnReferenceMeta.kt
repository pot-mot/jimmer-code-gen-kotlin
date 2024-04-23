package top.potmot.core.database.meta

import org.babyfish.jimmer.ImmutableObjects.isLoaded
import schemacrawler.schema.Column
import schemacrawler.schema.ColumnReference
import top.potmot.error.DataSourceLoadException
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

private fun Column.keyPath() =
    key().toString().let {
        it.substring(9, it.length - 2).split("/").reversed()
    }

private fun ColumnReference.getSourceNames(): ColumnReferenceNamePart {
    val sourceNameList = foreignKeyColumn.keyPath()

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
    val targetNameList = primaryKeyColumn.keyPath()

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
): ColumnReferenceMetaPart {
    val table = tableNameMap[tableName]
        ?: throw DataSourceLoadException.table(
            "Can not find table [$tableName]"
        )
    val column = table.columns.find { column -> column.name == columnName }
        ?: throw DataSourceLoadException.table(
            "Can not find table column [$tableName.$columnName]"
        )
    val schema = if (isLoaded(table, GenTableProps.SCHEMA)) table.schema else null

    if (schema != null && isLoaded(
            schema,
            GenSchemaProps.NAME
        ) && schema.name != schemaName
    ) throw DataSourceLoadException.table(
        "Can not match schema [$schemaName]"
    )

    return ColumnReferenceMetaPart(schema, table, column)
}

private fun ColumnReference.getSourceMeta(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMetaPart =
    getSourceNames().toMetaPart(tableNameMap)

private fun ColumnReference.getTargetMeta(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMetaPart =
    getTargetNames().toMetaPart(tableNameMap)

fun ColumnReference.toColumnReferenceMeta(
    tableNameMap: Map<String, GenTable>
): ColumnReferenceMeta =
    ColumnReferenceMeta(
        getSourceMeta(tableNameMap),
        getTargetMeta(tableNameMap)
    )
