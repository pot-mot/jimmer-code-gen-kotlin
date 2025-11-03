package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection

class H2MetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {
    override fun fetchTables(): List<TableInput> {
        val tables = mutableListOf<TableInput>()

        metadata.getTables(
            catalog,
            schema,
            "%",
            arrayOf("TABLE")
        ).use { rs ->
            while (rs.next()) {
                val schema = rs.getString("TABLE_SCHEM") ?: ""
                val tableName = rs.getString("TABLE_NAME")
                val remarks = rs.getString("REMARKS") ?: ""

                val columns = fetchTableColumns(tableName)
                val foreignKeys = fetchTableForeignKeys(tableName)

                val foreignKeyIndex = foreignKeys.map { it.name + "_INDEX_" }
                val indexes = fetchTableIndexes(tableName).filter { index ->
                    !index.name.startsWith("PRIMARY_KEY_") && !foreignKeyIndex.any { index.name.startsWith(it) }
                }

                val checks = fetchTableChecks(tableName)

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
        }

        return tables
    }

    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        connection.prepareStatement(
            """
            SELECT 
                tc.CONSTRAINT_NAME,
                cc.CHECK_CLAUSE
            FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
            JOIN INFORMATION_SCHEMA.CHECK_CONSTRAINTS cc 
                ON tc.CONSTRAINT_NAME = cc.CONSTRAINT_NAME
            WHERE tc.TABLE_NAME = ? 
            AND tc.CONSTRAINT_TYPE = 'CHECK'
            """.trimIndent()
        ).use { statement ->
            statement.setString(1, tableName.uppercase())
            statement.executeQuery().use { rs ->
                while (rs.next()) {
                    val check = TableInput.TargetOf_checks(
                        name = rs.getString("CONSTRAINT_NAME"),
                        expression = rs.getString("CHECK_CLAUSE")
                    )
                    checks.add(check)
                }
            }
        }

        return checks
    }
}