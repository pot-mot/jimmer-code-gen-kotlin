package top.potmot.core.database.load

import schemacrawler.schema.Column
import schemacrawler.schema.ColumnReference
import top.potmot.entity.dto.GenTableLoadView
import top.potmot.error.LoadFromDataSourceException

data class ColumnReferenceNamePart(
    val tableName: String,
    val columnName: String,
)

data class LoadColumnReferencePart(
    val table: GenTableLoadView,
    val column: GenTableLoadView.TargetOf_columns,
)

data class LoadColumnReference(
    val source: LoadColumnReferencePart,
    val target: LoadColumnReferencePart,
)

private fun Column.keyPath() =
    key().toString().let {
        it.substring(9, it.length - 2).split("/").reversed()
    }

private fun ColumnReference.getSourceNames(): ColumnReferenceNamePart {
    val sourceNameList = foreignKeyColumn.keyPath()

    val sourceTableName = sourceNameList[1]
    val sourceColumnName = sourceNameList[0]

    return ColumnReferenceNamePart(
        sourceTableName,
        sourceColumnName,
    )
}

private fun ColumnReference.getTargetNames(): ColumnReferenceNamePart {
    val targetNameList = primaryKeyColumn.keyPath()

    val targetTableName = targetNameList[1]
    val targetColumnName = targetNameList[0]

    return ColumnReferenceNamePart(
        targetTableName,
        targetColumnName,
    )
}

@Throws(LoadFromDataSourceException::class)
private fun ColumnReferenceNamePart.toLoadPart(
    tableNameMap: Map<String, GenTableLoadView>,
): LoadColumnReferencePart {
    val table = tableNameMap[tableName]
        ?: throw LoadFromDataSourceException.associationColumnReferenceTableNotFound(
            "Can not find table [$tableName]",
            tableName = tableName,
        )
    val column = table.columns.find { column -> column.name == columnName }
        ?: throw LoadFromDataSourceException.associationColumnReferenceColumnNotFound(
            "Can not find table column [$tableName.$columnName]",
            tableName = tableName,
            columnName = columnName,
        )

    return LoadColumnReferencePart(table, column)
}

private fun ColumnReference.getSourcePart(
    tableNameMap: Map<String, GenTableLoadView>,
): LoadColumnReferencePart =
    getSourceNames().toLoadPart(tableNameMap)

private fun ColumnReference.getTargetPart(
    tableNameMap: Map<String, GenTableLoadView>,
): LoadColumnReferencePart =
    getTargetNames().toLoadPart(tableNameMap)

@Throws(LoadFromDataSourceException::class)
fun ColumnReference.toLoadColumnReference(
    tableNameMap: Map<String, GenTableLoadView>,
): LoadColumnReference =
    LoadColumnReference(
        getSourcePart(tableNameMap),
        getTargetPart(tableNameMap)
    )
