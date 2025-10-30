package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection
import java.util.concurrent.ConcurrentHashMap

class OracleMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {
    private val tableCommentsCache = ConcurrentHashMap<String, String>()
    private val columnCommentsCache = ConcurrentHashMap<String, String>()

    override fun fetchTables(): List<TableInput> {
        loadAllComments()

        val tables = mutableListOf<TableInput>()

        val resultSet = metadata.getTables(catalog, schema, "%", arrayOf("TABLE"))

        while (resultSet.next()) {
            val tableSchema = resultSet.getString("TABLE_SCHEM") ?: ""
            val tableName = resultSet.getString("TABLE_NAME")
            val remarks = getTableComment(tableSchema, tableName) ?: ""

            val columns = fetchTableColumns(tableName)
            val indexes = fetchTableIndexes(tableName)
            val foreignKeys = fetchTableForeignKeys(tableName)
            val checks = fetchTableChecks(tableName)

            tables.add(
                TableInput(
                    schema = tableSchema,
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

        tableCommentsCache.clear()
        columnCommentsCache.clear()

        return tables
    }

    override fun fetchTableColumns(tableName: String): List<TableInput.TargetOf_columns> {
        // 获取主键信息并更新列标记
        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        val columns = mutableListOf<TableInput.TargetOf_columns>()
        val resultSet = metadata.getColumns(catalog, schema, tableName, "%")

        while (resultSet.next()) {
            val columnName = resultSet.getString("COLUMN_NAME")
            val remarks = getColumnComment(schema, tableName, columnName) ?: ""
            val typeName = resultSet.getString("TYPE_NAME")
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
        return columns
    }

    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        connection.prepareStatement(
            """
            SELECT CONSTRAINT_NAME, SEARCH_CONDITION
            FROM ALL_CONSTRAINTS
            WHERE OWNER = ?
              AND TABLE_NAME = ?
              AND CONSTRAINT_TYPE = 'C'
              AND UPPER(CONSTRAINT_NAME) NOT LIKE 'SYS_%'
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, connection.schema)
            stmt.setString(2, tableName)
            val resultSet = stmt.executeQuery()

            while (resultSet.next()) {
                checks.add(
                    TableInput.TargetOf_checks(
                        name = resultSet.getString("CONSTRAINT_NAME"),
                        expression = resultSet.getString("SEARCH_CONDITION")
                    )
                )
            }
        }

        return checks
    }

    /**
     * 批量加载所有表和列的注释信息
     */
    private fun loadAllComments() {
        connection.prepareStatement(
            """
            SELECT OWNER, TABLE_NAME, COMMENTS 
            FROM ALL_TAB_COMMENTS 
            WHERE OWNER = ?
            """.trimIndent()
        ).use { tableStmt ->
            tableStmt.setString(1, schema)
            val tableRs = tableStmt.executeQuery()

            while (tableRs.next()) {
                val tableSchema = tableRs.getString("OWNER")
                val tableName = tableRs.getString("TABLE_NAME")
                val tableComment = tableRs.getString("COMMENTS")
                tableCommentsCache["$tableSchema.$tableName"] = tableComment ?: ""
            }
            tableRs.close()
        }

        connection.prepareStatement(
            """
            SELECT OWNER, TABLE_NAME, COLUMN_NAME, COMMENTS 
            FROM ALL_COL_COMMENTS 
            WHERE OWNER = ?
            """.trimIndent()
        ).use { columnStmt ->
            columnStmt.setString(1, schema)
            val columnRs = columnStmt.executeQuery()

            while (columnRs.next()) {
                val tableSchema = columnRs.getString("OWNER")
                val tableName = columnRs.getString("TABLE_NAME")
                val columnName = columnRs.getString("COLUMN_NAME")
                val columnComment = columnRs.getString("COMMENTS")
                columnCommentsCache["$tableSchema.$tableName.$columnName"] = columnComment ?: ""
            }
            columnRs.close()
        }
    }

    private fun getTableComment(tableSchema: String?, tableName: String): String? {
        // 直接从缓存中获取
        return tableCommentsCache["$tableSchema.$tableName"]
    }

    private fun getColumnComment(tableSchema: String?, tableName: String, columnName: String): String? {
        // 直接从缓存中获取
        return columnCommentsCache["$tableSchema.$tableName.$columnName"]
    }
}
