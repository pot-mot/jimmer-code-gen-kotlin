package top.potmot.dao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import top.potmot.util.MetadataUtils
import java.sql.Connection
import java.sql.DatabaseMetaData
import javax.sql.DataSource

@Component
class GenMetadataDao(
    @Qualifier(value = "GenDataSource")
    val dataSource: DataSource
) {
    fun getTables() {
        dataSource.connection.use { connection ->
            getTables(connection)
        }
    }

    fun getColumns(tableName: String) {
        dataSource.connection.use { connection ->
            getColumns(connection, tableName)
        }
    }

    fun getTables(connection: Connection) {
        val tablesResultSet = MetadataUtils.getTables(connection)
        while (tablesResultSet.next()) {
            val tableName = tablesResultSet.getString("TABLE_NAME")
            val tableComment = tablesResultSet.getString("REMARKS")
            println("-----------------------------")
            println("$tableName [$tableComment]")
            getColumns(connection, tableName)
        }
    }

    fun getColumns(connection: Connection, tableName: String) {
        val columns = MetadataUtils.getColumns(connection, tableName)
        val pkColumns = MetadataUtils.getPkColumns(connection, tableName)
        val uniqueColumns = MetadataUtils.getUniqueColumns(connection, tableName)
        MetadataUtils.getUniqueIndexes(connection, tableName)
        var index = 0
        while (columns.next()) {
            index++
            val columnName = columns.getString("COLUMN_NAME")
            val columnType = columns.getString("TYPE_NAME")
            val columnComment = columns.getString("REMARKS")
            val columnDisplaySize = columns.getLong("COLUMN_SIZE")
            val columnPrecision = columns.getLong("DECIMAL_DIGITS")
            val columnDefault = columns.getString("COLUMN_DEF")
            val isNotNull = columns.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls
            val isAutoIncrement = columns.getString("IS_AUTOINCREMENT") == "YES"
            val isVirtualColumn = columns.getString("IS_GENERATEDCOLUMN") == "YES"
            val isPk = pkColumns.contains(columnName)
            val isUnique = uniqueColumns.contains(columnName)
            println(
                "$index $columnName [$columnComment] $columnType($columnDisplaySize) $columnPrecision \"$columnDefault\" [$isNotNull] [$isPk][$isAutoIncrement] [$isUnique] [$isVirtualColumn]"
            )
        }
    }
}
