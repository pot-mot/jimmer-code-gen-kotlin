package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection

class SqliteMetadataFetcher(
    connection: Connection,
    catalog: String? = connection.catalog?.ifBlank { null },
    schema: String? = connection.schema?.ifBlank { null },
) : MetadataFetcher(connection, catalog, schema) {
    private val checkPattern = Regex("CHECK\\s*\\([^)]+\\)", RegexOption.IGNORE_CASE)

    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        // SQLite 中通过 sqlite_master 表获取表的创建语句，然后解析出 CHECK 约束
        connection.prepareStatement(
            """
            SELECT sql 
            FROM sqlite_master 
            WHERE type = 'table' AND name = ?
            """.trimIndent()
        ).use { statement ->
            statement.setString(1, tableName)
            statement.executeQuery().use { rs ->
                if (rs.next()) {
                    val createTableSql = rs.getString("sql")
                    // 使用正则表达式从 CREATE TABLE 语句中提取 CHECK 约束
                    val matches = checkPattern.findAll(createTableSql)

                    matches.forEach { match ->
                        val checkClause = match.value
                        // 简单处理，将整个 CHECK 子句作为表达式
                        val check = TableInput.TargetOf_checks(
                            name = "", // SQLite 不直接提供 CHECK 约束名称，可以留空或生成一个
                            expression = checkClause
                        )
                        checks.add(check)
                    }
                }
            }
        }

        return checks
    }
}