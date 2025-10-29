package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection

class OracleMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {
    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        connection.createStatement().use { stmt ->
            val resultSet = stmt.executeQuery(
                """
            SELECT CONSTRAINT_NAME, SEARCH_CONDITION
            FROM ALL_CONSTRAINTS
            WHERE OWNER = '${connection.schema}'
              AND TABLE_NAME = '$tableName'
              AND CONSTRAINT_TYPE = 'C'
              AND UPPER(CONSTRAINT_NAME) NOT LIKE 'SYS_%'
            """.trimIndent()
            )

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

}