package top.potmot.util

import top.potmot.constant.TableType
import java.sql.Connection
import java.sql.ResultSet

object MetadataUtils {
    fun getTables(
        connection: Connection,
        tablePattern: String? = null,
        types: Array<String> = arrayOf(TableType.TABLE.value, TableType.VIEW.value),
    ): ResultSet {
        val metaData = connection.metaData
        val catalog = connection.catalog
        return metaData.getTables(catalog, null, tablePattern, types)
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
        val columns = mutableListOf<String>()
        val primaryKeys = getPrimaryKeys(connection, tablePattern)
        while (primaryKeys.next()) {
            columns += primaryKeys.getString("COLUMN_NAME")
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
        val columns = mutableListOf<String>()
        val foreignKeys = getForeignKeys(connection, tablePattern)
        while (foreignKeys.next()) {
            columns += foreignKeys.getString("COLUMN_NAME")
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
        val columns = mutableListOf<String>()
        val indexes = getIndexes(connection, tablePattern)
        while (indexes.next()) {
            columns += indexes.getString("COLUMN_NAME")
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
        val columns = mutableListOf<String>()
        val uniqueIndexes = getUniqueIndexes(connection, tablePattern)
        while (uniqueIndexes.next()) {
            columns += uniqueIndexes.getString("COLUMN_NAME")
        }
        return columns
    }
}
