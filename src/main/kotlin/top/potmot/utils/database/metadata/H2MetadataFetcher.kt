package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection

class H2MetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {
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
            statement.executeQuery().use { resultSet ->
                while (resultSet.next()) {
                    val check = TableInput.TargetOf_checks(
                        name = resultSet.getString("CONSTRAINT_NAME"),
                        expression = resultSet.getString("CHECK_CLAUSE")
                    )
                    checks.add(check)
                }
            }
        }

        return checks
    }
}