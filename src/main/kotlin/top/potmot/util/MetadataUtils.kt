package top.potmot.util

import javax.sql.DataSource

object MetadataUtils {
    fun columns(
        dataSource: DataSource,
        catalog: String,
        schemaPattern: String = catalog,
        tablePattern: String = "%",
        columnNamePattern: String = "%"
    ) {
        val columns = dataSource.connection.metaData.getColumns(catalog, schemaPattern, tablePattern, columnNamePattern)
        for (i in 1 until columns.metaData.columnCount) {
            val name = columns.metaData.getColumnName(i)
            val type = columns.metaData.getColumnTypeName(i)

            println("${name}, ${type}")
        }
    }
}
