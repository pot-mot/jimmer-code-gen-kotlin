package top.potmot.util.database

import java.sql.ResultSet
import java.sql.ResultSetMetaData

fun debugResultSetColumn(resultSet: ResultSet) {
    val metaData: ResultSetMetaData = resultSet.getMetaData()
    val columnCount = metaData.columnCount

    // 遍历结果列
    for (i in 1..columnCount) {
        val columnName = metaData.getColumnName(i)
        val columnType = metaData.getColumnTypeName(i)
        println("Column Name: $columnName")
        println("Column Type: $columnType")
        println()
    }
}
