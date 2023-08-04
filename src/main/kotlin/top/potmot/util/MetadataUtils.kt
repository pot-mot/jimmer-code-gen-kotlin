package top.potmot.util

import java.sql.Connection
import java.sql.ResultSet

object MetadataUtils {
    fun getTables(
        connection: Connection,
        types: Array<String> = arrayOf("TABLE", "VIEW")
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getTables(catalog, null, null, types)
    }

    fun getColumns(
        connection: Connection,
        tablePattern: String? = null,
        columnNamePattern: String? = null
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getColumns(catalog, null, tablePattern, columnNamePattern)
    }

    fun getPrimaryKeys(
        connection: Connection,
        tablePattern: String? = null,
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getPrimaryKeys(catalog, null, tablePattern)
    }

    fun getPkColumns(
        connection: Connection,
        tablePattern: String,
    ): List<String> {
        var columns = emptyList<String>()
        val primaryKeys = getPrimaryKeys(connection, tablePattern)
        while (primaryKeys.next()) {
            columns = columns + primaryKeys.getString("COLUMN_NAME")
        }
        return columns
    }

    fun getForeignKeys(
        connection: Connection,
        tablePattern: String? = null,
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getImportedKeys(catalog, null, tablePattern)
    }

    fun getFkColumns(
        connection: Connection,
        tablePattern: String,
    ): List<String> {
        var columns = emptyList<String>()
        val foreignKeys = getForeignKeys(connection, tablePattern)
        while (foreignKeys.next()) {
            columns = columns + foreignKeys.getString("COLUMN_NAME")
        }
        return columns
    }


    fun getIndexes(
        connection: Connection,
        tablePattern: String? = null,
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getIndexInfo(catalog, null, tablePattern, false, false)
    }

    fun getIdxColumns(
        connection: Connection,
        tablePattern: String,
    ): List<String> {
        var columns = emptyList<String>()
        val indexes = getIndexes(connection, tablePattern)
        while (indexes.next()) {
            columns  = columns + indexes.getString("COLUMN_NAME")
        }
        return columns
    }

    fun getUniqueIndexes(
        connection: Connection,
        tablePattern: String? = null,
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getIndexInfo(catalog, null, tablePattern, true, true)
    }

    fun getUniqueColumns(
        connection: Connection,
        tablePattern: String,
    ): List<String> {
        var columns = emptyList<String>()
        val uniqueIndexes = getUniqueIndexes(connection, tablePattern)
        while (uniqueIndexes.next()) {
            columns = columns + uniqueIndexes.getString("COLUMN_NAME")
        }
        return columns
    }
}
