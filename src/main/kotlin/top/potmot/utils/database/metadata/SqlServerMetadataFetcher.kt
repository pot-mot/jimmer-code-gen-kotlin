package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection
import java.util.concurrent.ConcurrentHashMap

class SqlServerMetadataFetcher(
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
            val comment = getTableComment(tableSchema, tableName) ?: ""

            val columns = fetchTableColumns(tableName)
            val indexes = fetchTableIndexes(tableName)
            val foreignKeys = fetchTableForeignKeys(tableName)
            val checks = fetchTableChecks(tableName)

            tables.add(
                TableInput(
                    schema = tableSchema,
                    name = tableName,
                    comment = comment,
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
            val comment = getColumnComment(schema, tableName, columnName) ?: ""
            val typeName = resultSet.getString("TYPE_NAME")
            val dataSize = resultSet.getInt("COLUMN_SIZE").takeIf { it != 0 }
            val numericPrecision = resultSet.getInt("DECIMAL_DIGITS").takeIf { it != 0 }
            val nullable = resultSet.getInt("NULLABLE") != 0
            val defaultValue = resultSet.getString("COLUMN_DEF")
            val autoIncrement = resultSet.getString("IS_AUTOINCREMENT")?.equals("YES", ignoreCase = true)

            columns.add(
                TableInput.TargetOf_columns(
                    name = columnName,
                    comment = comment,
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

    override fun fetchTableIndexes(tableName: String): List<TableInput.TargetOf_indexes> {
        val indexes = mutableMapOf<String, TableInput.TargetOf_indexes>()

        connection.prepareStatement(
            """
        SELECT 
            i.name AS index_name,
            c.name AS column_name,
            i.is_unique AS is_unique,
            i.filter_definition AS filter_definition
        FROM sys.indexes i
        INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
        INNER JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
        INNER JOIN sys.tables t ON i.object_id = t.object_id
        INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
        WHERE t.name = ?
          AND s.name = ?
          AND i.is_primary_key = 0
        ORDER BY i.name, ic.key_ordinal
        """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, tableName)
            stmt.setString(2, schema ?: "dbo")
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val indexName = rs.getString("index_name")
                    val columnName = rs.getString("column_name")
                    val isUnique = rs.getBoolean("is_unique")
                    val filterDefinition = rs.getString("filter_definition")

                    indexes[indexName] = indexes[indexName]?.let { existing ->
                        existing.copy(
                            columnNames = existing.columnNames + columnName
                        )
                    } ?: TableInput.TargetOf_indexes(
                        name = indexName,
                        columnNames = listOf(columnName),
                        uniqueIndex = isUnique,
                        wherePredicates = filterDefinition
                    )
                }
            }
        }

        return indexes.values.toList()
    }


    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        connection.prepareStatement(
            """
        SELECT cc.name as constraint_name,
               cc.definition as definition
        FROM sys.check_constraints cc
        INNER JOIN sys.tables t ON cc.parent_object_id = t.object_id
        INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
        WHERE t.name = ? AND s.name = ?
        """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, tableName)
            stmt.setString(2, schema ?: "dbo")
            val rs = stmt.executeQuery()

            while (rs.next()) {
                checks.add(
                    TableInput.TargetOf_checks(
                        name = rs.getString("constraint_name"),
                        expression = rs.getString("definition")
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
        // 批量获取所有表的注释
        connection.prepareStatement(
            """
            SELECT 
                s.name AS schema_name,
                t.name AS table_name,
                ep.value AS table_comment
            FROM sys.extended_properties ep
            INNER JOIN sys.tables t ON ep.major_id = t.object_id
            INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
            WHERE ep.name = 'MS_Description' 
            AND ep.minor_id = 0
            AND s.name = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, schema ?: "dbo")
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val schemaName = rs.getString("schema_name")
                    val tableName = rs.getString("table_name")
                    val tableComment = rs.getString("table_comment")
                    tableCommentsCache["$schemaName.$tableName"] = tableComment ?: ""
                }
            }
        }

        // 批量获取所有列的注释
        connection.prepareStatement(
            """
            SELECT 
                s.name AS schema_name,
                t.name AS table_name,
                c.name AS column_name,
                ep.value AS column_comment
            FROM sys.extended_properties ep
            INNER JOIN sys.columns c ON ep.major_id = c.object_id AND ep.minor_id = c.column_id
            INNER JOIN sys.tables t ON c.object_id = t.object_id
            INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
            WHERE ep.name = 'MS_Description'
            AND ep.minor_id > 0
            AND s.name = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, schema ?: "dbo")
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val schemaName = rs.getString("schema_name")
                    val tableName = rs.getString("table_name")
                    val columnName = rs.getString("column_name")
                    val columnComment = rs.getString("column_comment")
                    columnCommentsCache["$schemaName.$tableName.$columnName"] = columnComment ?: ""
                }
            }
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
