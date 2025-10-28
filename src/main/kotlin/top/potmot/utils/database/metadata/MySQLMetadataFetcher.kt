package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.database.model.ColumnFullTypePair
import java.sql.Connection

private val columnRegex = Regex("`([^`]+)`\\s+([^\\n\\s]+)")
private val checkConstraintRegex = Regex("CONSTRAINT\\s+`([^`]+)`\\s+CHECK\\s*\\((.+)\\)", RegexOption.IGNORE_CASE)

class MySQLMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {
    override fun fetchTables(): List<TableInput> {
        val tables = mutableListOf<TableInput>()

        val resultSet = metadata.getTables(catalog, schema, "%", arrayOf("TABLE"))

        while (resultSet.next()) {
            val schema = resultSet.getString("TABLE_SCHEM") ?: ""
            val tableName = resultSet.getString("TABLE_NAME")
            val remarks = resultSet.getString("REMARKS") ?: ""

            val (columns, checks) = fetchTableColumnsAndChecks(tableName)
            val indexes = fetchTableIndexes(tableName)
            val foreignKeys = fetchTableForeignKeys(tableName)

            // 创建 TableInput 实例
            tables.add(
                TableInput(
                    schema = schema,
                    name = tableName,
                    comment = remarks,
                    columns = columns,
                    indexes = indexes,
                    foreignKeys = foreignKeys,
                    checks = checks,
                )
            )
        }

        resultSet.close()
        return tables
    }

    fun fetchTableColumnsAndChecks(tableName: String): Pair<List<TableInput.TargetOf_columns>, List<TableInput.TargetOf_checks>> {
        val createTableStmt = getCreateTableStmt(tableName)
        if (createTableStmt == null) return emptyList<TableInput.TargetOf_columns>() to emptyList<TableInput.TargetOf_checks>()

        val fullTypeMap = mutableMapOf<String, ColumnFullTypePair>()
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        // 解析 CREATE TABLE 语句中的列定义
        val lines = createTableStmt.lines()
        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine.startsWith("CONSTRAINT") && trimmedLine.contains("CHECK", ignoreCase = true)) {
                val checkConstraint = parseCheckConstraintDefinition(trimmedLine)
                checkConstraint?.let { checks.add(it) }
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
                )
            )
        }

        resultSet.close()

        return columns to checks
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

    private fun parseCheckConstraintDefinition(constraintDef: String): TableInput.TargetOf_checks? {
        // 解析 CHECK 约束定义
        val matchResult = checkConstraintRegex.find(constraintDef)

        if (matchResult != null) {
            return TableInput.TargetOf_checks(
                name = matchResult.groupValues[1],
                expression = matchResult.groupValues[2]
            )
        }

        return null
    }
}
