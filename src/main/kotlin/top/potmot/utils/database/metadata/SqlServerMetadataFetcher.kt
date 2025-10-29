// SqlServerMetadataFetcher.kt
package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection

class SqlServerMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {

    override fun fetchTables(): List<TableInput> {
        val tables = mutableListOf<TableInput>()

        val resultSet = metadata.getTables(catalog, schema, "%", arrayOf("TABLE"))

        while (resultSet.next()) {
            val schema = resultSet.getString("TABLE_SCHEM") ?: ""
            val tableName = resultSet.getString("TABLE_NAME")
            val remarks = getTableComment(schema, tableName) ?: ""

            val columns = fetchTableColumns(tableName)
            val indexes = fetchTableIndexes(tableName)
            val foreignKeys = fetchTableForeignKeys(tableName)
            val checks = fetchTableChecks(tableName)

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

    private fun getTableComment(schema: String, tableName: String): String? {
        return connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery(
                """
                SELECT value 
                FROM fn_listextendedproperty (
                    'MS_Description', 
                    'SCHEMA', '$schema', 
                    'TABLE', '$tableName', 
                    default, default
                )
                """.trimIndent()
            )

            if (rs.next()) {
                rs.getString("value")
            } else {
                null
            }
        }
    }

    override fun fetchTableColumns(tableName: String): List<TableInput.TargetOf_columns> {
        // 获取主键信息并更新列标记
        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        val columns = mutableListOf<TableInput.TargetOf_columns>()
        val resultSet = metadata.getColumns(catalog, schema, tableName, "%")

        while (resultSet.next()) {
            val columnName = resultSet.getString("COLUMN_NAME")
            val remarks = getColumnComment(schema!!, tableName, columnName) ?: ""
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

    private fun getColumnComment(schema: String, tableName: String, columnName: String): String? {
        return connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery(
                """
                SELECT value 
                FROM fn_listextendedproperty (
                    'MS_Description', 
                    'SCHEMA', '$schema', 
                    'TABLE', '$tableName', 
                    'COLUMN', '$columnName'
                )
                """.trimIndent()
            )

            if (rs.next()) {
                rs.getString("value")
            } else {
                null
            }
        }
    }

    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery(
                """
                SELECT cc.name as constraint_name,
                       cc.definition as definition
                FROM sys.check_constraints cc
                INNER JOIN sys.tables t ON cc.parent_object_id = t.object_id
                INNER JOIN sys.schemas s ON t.schema_id = s.schema_id
                WHERE t.name = '$tableName' AND s.name = '${schema ?: "dbo"}'
                """.trimIndent()
            )

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
}
