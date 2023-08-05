package top.potmot.dao

import org.babyfish.jimmer.kt.new
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import top.potmot.model.GenTable
import top.potmot.model.GenTableColumn
import top.potmot.model.by
import top.potmot.util.MetadataUtils
import java.sql.Connection
import java.sql.DatabaseMetaData
import javax.sql.DataSource

@Component
class MetadataDao(
    @Qualifier(value = "GenDataSource") val dataSource: DataSource
) {
    fun getTables(tablePattern: String? = null): List<GenTable> {
        return dataSource.connection.use { connection ->
            getTables(connection, tablePattern)
        }
    }

    fun getColumns(tableName: String): List<GenTableColumn> {
        return dataSource.connection.use { connection ->
            getColumns(connection, tableName)
        }
    }

    fun getTables(connection: Connection, tablePattern: String? = null): List<GenTable> {
        val tablesResultSet = MetadataUtils.getTables(connection, tablePattern)
        val genTables = mutableListOf<GenTable>()
        while (tablesResultSet.next()) {
            val tableName = tablesResultSet.getString("TABLE_NAME") ?: ""
            val tableComment = tablesResultSet.getString("REMARKS") ?: ""
            val tableType = tablesResultSet.getString("TABLE_TYPE") ?: ""

            var genTable = new(GenTable::class).by {
                this.tableName = tableName
                this.tableComment = tableComment
                this.tableType = tableType
            }

            genTable = new(GenTable::class).by(genTable) {
                columns = getColumns(connection, tableName)
            }
            genTables += genTable
        }
        return genTables
    }

    fun getColumns(connection: Connection, tableName: String): List<GenTableColumn> {
        val columns = MetadataUtils.getColumns(connection, tableName)
        val pkColumns = MetadataUtils.getPkColumns(connection, tableName)
        val uniqueColumns = MetadataUtils.getUniqueColumns(connection, tableName)
        MetadataUtils.getUniqueIndexes(connection, tableName)
        var index = 0L
        val genColumns = mutableListOf<GenTableColumn>()
        while (columns.next()) {
            index++
            val columnName = columns.getString("COLUMN_NAME") ?: ""
            val columnTypeCode = columns.getInt("DATA_TYPE")
            val columnType = columns.getString("TYPE_NAME") ?: ""
            val columnComment = columns.getString("REMARKS") ?: ""
            val columnDisplaySize = columns.getLong("COLUMN_SIZE")
            val columnPrecision = columns.getLong("DECIMAL_DIGITS")
            val columnDefault = columns.getString("COLUMN_DEF") ?: ""
            val isNotNull = columns.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls
            val isAutoIncrement = columns.getString("IS_AUTOINCREMENT") == "YES"
            val isVirtualColumn = columns.getString("IS_GENERATEDCOLUMN") == "YES"
            val isPk = pkColumns.contains(columnName)
            val isUnique = uniqueColumns.contains(columnName)

            val genColumn = new(GenTableColumn::class).by {
                this.columnSort = index
                this.columnName = columnName
                this.columnTypeCode = columnTypeCode
                this.columnType = columnType
                this.columnComment = columnComment
                this.columnDisplaySize = columnDisplaySize
                this.columnPrecision = columnPrecision
                this.columnDefault = columnDefault
                this.isPk = isPk
                this.isAutoIncrement = isAutoIncrement
                this.isUnique = isUnique
                this.isNotNull = isNotNull
                this.isVirtualColumn = isVirtualColumn
            }

            genColumns += genColumn
        }
        return genColumns
    }
}
