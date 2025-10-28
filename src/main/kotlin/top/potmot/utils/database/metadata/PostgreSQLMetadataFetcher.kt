package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.database.model.ColumnFullTypePair
import java.sql.Connection

class PostgreSQLMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {

    override fun fetchTableColumns(tableName: String): List<TableInput.TargetOf_columns> {
        val fullTypeMap = mutableMapOf<String, ColumnFullTypePair>()

        // 获取表的所有列定义，包括数组信息和维度
        connection.createStatement().use { stmt ->
            val resultSet = stmt.executeQuery(
                """
                SELECT a.attname as column_name,
                       pg_catalog.format_type(a.atttypid, a.atttypmod) as formatted_type
                FROM pg_catalog.pg_attribute a
                JOIN pg_catalog.pg_type ty ON a.atttypid = ty.oid
                LEFT JOIN pg_catalog.pg_attrdef d ON (a.attrelid = d.adrelid AND a.attnum = d.adnum)
                JOIN pg_catalog.pg_class c ON a.attrelid = c.oid
                JOIN pg_catalog.pg_namespace n ON c.relnamespace = n.oid
                WHERE c.relname = '$tableName'
                  AND n.nspname = '${schema ?: "public"}'
                  AND a.attnum > 0
                  AND NOT a.attisdropped
                ORDER BY a.attnum
                """.trimIndent()
            )

            while (resultSet.next()) {
                val columnName = resultSet.getString("column_name")
                val formattedType = resultSet.getString("formatted_type")

                fullTypeMap[columnName] = ColumnFullTypePair(
                    columnName = columnName,
                    fullType = formattedType
                )
            }
        }

        // 获取主键信息
        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        // 构建返回结果
        val columns = mutableListOf<TableInput.TargetOf_columns>()
        val resultSet = metadata.getColumns(catalog, schema, tableName, "%")

        while (resultSet.next()) {
            val columnName = resultSet.getString("COLUMN_NAME")
            val columnInfo = fullTypeMap[columnName]

            val remarks = resultSet.getString("REMARKS") ?: ""
            val typeName = columnInfo?.fullType ?: resultSet.getString("TYPE_NAME")
            val dataSize = resultSet.getInt("COLUMN_SIZE").takeIf { it != 0 }
            val numericPrecision = resultSet.getInt("DECIMAL_DIGITS").takeIf { it != 0 }
            val nullable = resultSet.getString("IS_NULLABLE") == "YES"
            val defaultValue = resultSet.getString("COLUMN_DEF")
            val autoIncrement = isAutoIncrement(tableName, columnName)

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

    private fun isAutoIncrement(tableName: String, columnName: String): Boolean {
        connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery(
                """
                SELECT column_default
                FROM information_schema.columns
                WHERE table_name = '$tableName'
                  AND column_name = '$columnName'
                  AND table_schema = '${schema ?: "public"}'
                """.trimIndent()
            )

            if (rs.next()) {
                val defaultValue = rs.getString("column_default")
                return defaultValue?.startsWith("nextval(") == true
            }
        }
        return false
    }

    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        // 获取 CHECK 约束信息
        connection.createStatement().use { stmt ->
            val constraintRs = stmt.executeQuery(
                """
                SELECT conname AS constraint_name,
                       pg_get_constraintdef(c.oid) AS constraint_definition
                FROM pg_constraint c
                JOIN pg_class cls ON c.conrelid = cls.oid
                JOIN pg_namespace nsp ON cls.relnamespace = nsp.oid
                WHERE contype = 'c'
                  AND cls.relname = '$tableName'
                  AND nsp.nspname = '${schema ?: "public"}'
                """.trimIndent()
            )

            while (constraintRs.next()) {
                val constraintName = constraintRs.getString("constraint_name")
                val definition = constraintRs.getString("constraint_definition")

                checks.add(
                    TableInput.TargetOf_checks(
                        name = constraintName,
                        expression = definition
                    )
                )
            }
        }

        return checks
    }
}
