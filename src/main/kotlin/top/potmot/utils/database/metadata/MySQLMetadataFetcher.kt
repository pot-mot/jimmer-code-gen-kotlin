package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.database.model.CheckConstraintInfo
import top.potmot.utils.database.model.ColumnFullTypePair
import java.sql.Connection

private val columnRegex = Regex("`([^`]+)`\\s+([^\\n\\s]+)")
private val checkConstraintRegex = Regex("CONSTRAINT\\s+`([^`]+)`\\s+CHECK\\s*\\((.+)\\)", RegexOption.IGNORE_CASE)

class MySQLMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {
    override fun fetchTableColumns(tableName: String): List<TableInput.TargetOf_columns> {
        val createTableStmt = getCreateTableStmt(tableName)
        if (createTableStmt == null) return emptyList()

        val fullTypeMap = mutableMapOf<String, ColumnFullTypePair>()
        val checkInfoMap = mutableMapOf<String, MutableList<CheckConstraintInfo>>()

        // 解析 CREATE TABLE 语句中的列定义
        val lines = createTableStmt.lines()
        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine.startsWith("CONSTRAINT") && trimmedLine.contains("CHECK", ignoreCase = true)) {
                val checkConstraint = parseCheckConstraintDefinition(trimmedLine)
                checkConstraint?.let {
                    checkInfoMap[it.columnName]?.add(it) ?: run {
                        checkInfoMap[it.columnName] = mutableListOf(it)
                    }
                }
            } else if (
                !trimmedLine.startsWith("`PRIMARY") &&
                !trimmedLine.startsWith("`CONSTRAINT") &&
                !trimmedLine.startsWith("`KEY") &&
                !trimmedLine.startsWith("`UNIQUE") &&
                !trimmedLine.startsWith("`FULLTEXT") &&
                !trimmedLine.startsWith("`SPATIAL")
            ) {
                val columnFullTypePair = parseColumnDefinition(trimmedLine)
                columnFullTypePair?.let {
                    fullTypeMap[it.columnName] = it
                }
            }
        }

        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        val columns = mutableListOf<TableInput.TargetOf_columns>()
        val resultSet = metadata.getColumns(catalog, schema, tableName, "%")

        while (resultSet.next()) {
            val columnName = resultSet.getString("COLUMN_NAME")
            val columnInfo = fullTypeMap[columnName]
            val constraints = checkInfoMap[columnName]?.map { it.checkClause }

            val remarks = resultSet.getString("REMARKS") ?: ""
            val typeName = columnInfo?.fullType ?: resultSet.getString("TYPE_NAME")
            val dataSize = resultSet.getInt("COLUMN_SIZE").takeIf { it != 0 }
            val numericPrecision = resultSet.getInt("DECIMAL_DIGITS").takeIf { it != 0 }
            val nullable = resultSet.getInt("NULLABLE") != 0
            val defaultValue = resultSet.getString("COLUMN_DEF")
            val autoIncrement = resultSet.getString("IS_AUTOINCREMENT")?.equals("YES", ignoreCase = true)

            columns.add(
                TableInput.TargetOf_columns(
                    name = columnName,
                    comment = remarks,
                    type = typeName,
                    dataSize = dataSize,
                    numericPrecision = numericPrecision,
                    nullable = nullable,
                    defaultValue = defaultValue,
                    partOfPrimaryKey = primaryKeys.contains(columnName),
                    autoIncrement = autoIncrement,
                    otherConstraints = constraints
                )
            )
        }

        resultSet.close()

        return columns
    }

    private fun getCreateTableStmt(tableName: String): String? {
        connection.createStatement().use { stmt ->
            val resultSet = stmt.executeQuery("SHOW CREATE TABLE `$tableName`")
            if (resultSet.next()) {
                return resultSet.getString(2)
            }
        }
        return null
    }

    private fun parseColumnDefinition(columnDef: String): ColumnFullTypePair? {
        // 解析单个列定义
        val matchResult = columnRegex.find(columnDef)

        if (matchResult != null) {
            val columnName = matchResult.groupValues[1]
            val typePart = matchResult.groupValues[2]

            return ColumnFullTypePair(
                columnName = columnName,
                fullType = typePart
            )
        }

        return null
    }

    private fun parseCheckConstraintDefinition(constraintDef: String): CheckConstraintInfo? {
        // 解析 CHECK 约束定义
        val matchResult = checkConstraintRegex.find(constraintDef)

        if (matchResult != null) {
            val constraintName = matchResult.groupValues[1]
            val checkClause = matchResult.groupValues[2]

            // 简单提取列名（假设 CHECK 约束格式为 `column` op value）
            val columnRegex = Regex("`([^`]+)`")
            val columnMatch = columnRegex.find(checkClause)
            val columnName = columnMatch?.groupValues?.get(1) ?: ""

            return CheckConstraintInfo(
                constraintName = constraintName,
                columnName = columnName,
                checkClause = checkClause
            )
        }

        return null
    }
}
