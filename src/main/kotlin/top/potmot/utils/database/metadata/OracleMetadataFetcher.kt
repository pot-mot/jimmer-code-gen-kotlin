package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection
import java.util.concurrent.ConcurrentHashMap

class OracleMetadataFetcher(
    connection: Connection,
    catalog: String? = connection.catalog,
    schema: String? = connection.schema,
) : MetadataFetcher(connection, catalog, schema) {
    private val tableCommentsCache = ConcurrentHashMap<String, String>()
    private val columnCommentsCache = ConcurrentHashMap<String, String>()

    override fun fetchTables(): List<TableInput> {
        loadAllComments()

        val tables = mutableListOf<TableInput>()

        metadata.getTables(
            catalog,
            schema,
            "%",
            arrayOf("TABLE")
        ).use { rs ->
            while (rs.next()) {
                val tableSchema = rs.getString("TABLE_SCHEM") ?: ""
                val tableName = rs.getString("TABLE_NAME")
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
        }

        return tables
    }

    override fun fetchTableColumns(tableName: String): List<TableInput.TargetOf_columns> {
        // 获取主键信息并更新列标记
        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        val columns = mutableListOf<TableInput.TargetOf_columns>()
        metadata.getColumns(
            catalog,
            schema,
            tableName,
            "%"
        ).use { rs ->
            while (rs.next()) {
                val columnName = rs.getString("COLUMN_NAME")
                val remarks = getColumnComment(schema, tableName, columnName) ?: ""
                val typeName = rs.getString("TYPE_NAME")
                val dataSize = rs.getInt("COLUMN_SIZE").takeIf { it != 0 }
                val numericPrecision = rs.getInt("DECIMAL_DIGITS").takeIf { it != 0 }
                val nullable = rs.getInt("NULLABLE") != 0
                val defaultValue = rs.getString("COLUMN_DEF")
                val autoIncrement = rs.getString("IS_AUTOINCREMENT")?.equals("YES", ignoreCase = true)

                columns.add(
                    TableInput.TargetOf_columns(
                        name = columnName,
                        comment = remarks,
                        type = buildFullTypeDeclaration(typeName, dataSize, numericPrecision),
                        dataSize = dataSize,
                        numericPrecision = numericPrecision,
                        nullable = nullable,
                        defaultValue = defaultValue,
                        partOfPrimaryKey = primaryKeys.contains(columnName),
                        autoIncrement = autoIncrement,
                    )
                )
            }
        }

        return columns
    }

    override fun fetchTableIndexes(tableName: String): List<TableInput.TargetOf_indexes> {
        return super.fetchTableIndexes(tableName).filter {
            !it.name.startsWith("SYS_")
        }
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
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    checks.add(
                        TableInput.TargetOf_checks(
                            name = rs.getString("CONSTRAINT_NAME"),
                            expression = rs.getString("SEARCH_CONDITION")
                        )
                    )
                }
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
        ).use { stmt ->
            stmt.setString(1, schema)
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val tableSchema = rs.getString("OWNER")
                    val tableName = rs.getString("TABLE_NAME")
                    val tableComment = rs.getString("COMMENTS")
                    tableCommentsCache["$tableSchema.$tableName"] = tableComment ?: ""
                }
            }
        }

        connection.prepareStatement(
            """
            SELECT OWNER, TABLE_NAME, COLUMN_NAME, COMMENTS 
            FROM ALL_COL_COMMENTS 
            WHERE OWNER = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, schema)
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val tableSchema = rs.getString("OWNER")
                    val tableName = rs.getString("TABLE_NAME")
                    val columnName = rs.getString("COLUMN_NAME")
                    val columnComment = rs.getString("COMMENTS")
                    columnCommentsCache["$tableSchema.$tableName.$columnName"] = columnComment ?: ""
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

    private fun buildFullTypeDeclaration(
        typeName: String,
        dataSize: Int?,
        numericPrecision: Int?,
    ): String {
        return when (typeName.uppercase()) {
            "VARCHAR2", "NVARCHAR2", "CHAR", "NCHAR", "RAW" -> {
                "$typeName($dataSize)"
            }
            "NUMBER" -> {
                when {
                    dataSize != null && numericPrecision != null ->
                        "$typeName($dataSize,$numericPrecision)"
                    dataSize != null ->
                        "$typeName($dataSize)"
                    else -> typeName
                }
            }
            "FLOAT" -> {
                if (dataSize != null) "$typeName($dataSize)" else typeName
            }
            else -> typeName
        }
    }
}
