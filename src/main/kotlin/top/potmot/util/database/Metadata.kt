package top.potmot.util.database

import top.potmot.constant.TableType
import java.sql.Connection
import java.sql.ResultSet

fun getTableResultSet(
    connection: Connection,
    tablePattern: String? = null,
    types: Array<String> = arrayOf(TableType.TABLE.value, TableType.VIEW.value),
): ResultSet {
    val metaData = connection.metaData
    val catalog = connection.catalog
    return metaData.getTables(catalog, null, tablePattern, types)
}

fun getColumnResultSet(
    connection: Connection,
    tablePattern: String? = null,
    columnNamePattern: String? = null
): ResultSet {
    val metaData = connection.metaData
    val catalog = connection.catalog
    return metaData.getColumns(catalog, null, tablePattern, columnNamePattern)
}

fun getPrimaryKeysResultSet(
    connection: Connection,
    tablePattern: String? = null,
): ResultSet {
    val metaData = connection.metaData
    val catalog = connection.catalog
    return metaData.getPrimaryKeys(catalog, null, tablePattern)
}

fun getPkColumnNames(
    connection: Connection,
    tablePattern: String,
): List<String> {
    val columns = mutableListOf<String>()
    val primaryKeys = getPrimaryKeysResultSet(connection, tablePattern)
    while (primaryKeys.next()) {
        columns += primaryKeys.getString("COLUMN_NAME")
    }
    return columns
}

fun getForeignKeysResultSet(
    connection: Connection,
    tablePattern: String? = null,
): ResultSet {
    val metaData = connection.metaData
    val catalog = connection.catalog
    return metaData.getImportedKeys(catalog, null, tablePattern)
}

fun getFkColumnNames(
    connection: Connection,
    tablePattern: String,
): List<String> {
    val columns = mutableListOf<String>()
    val foreignKeys = getForeignKeysResultSet(connection, tablePattern)
    while (foreignKeys.next()) {
        columns += foreignKeys.getString("COLUMN_NAME")
    }
    return columns
}


fun getIndexesResultSet(
    connection: Connection,
    tablePattern: String? = null,
): ResultSet {
    val metaData = connection.metaData
    val catalog = connection.catalog
    return metaData.getIndexInfo(catalog, null, tablePattern, false, false)
}

fun getIdxColumnNames(
    connection: Connection,
    tablePattern: String,
): List<String> {
    val columns = mutableListOf<String>()
    val indexes = getIndexesResultSet(connection, tablePattern)
    while (indexes.next()) {
        columns += indexes.getString("COLUMN_NAME")
    }
    return columns
}

fun getUniqueIndexesResultSet(
    connection: Connection,
    tablePattern: String? = null,
): ResultSet {
    val metaData = connection.metaData
    val catalog = connection.catalog
    return metaData.getIndexInfo(catalog, null, tablePattern, true, true)
}

fun getUniqueColumnNames(
    connection: Connection,
    tablePattern: String,
): List<String> {
    val columns = mutableListOf<String>()
    val uniqueIndexes = getUniqueIndexesResultSet(connection, tablePattern)
    while (uniqueIndexes.next()) {
        columns += uniqueIndexes.getString("COLUMN_NAME")
    }
    return columns
}
