package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.database.model.ColumnFullTypePair
import java.sql.Connection
import kotlin.collections.set

class PostgreSQLMetadataFetcher(
    connection: Connection
) : MetadataFetcher(connection) {

    override fun fetchTableColumns(tableName: String): List<TableInput.TargetOf_columns> {
        val fullTypeMap = mutableMapOf<String, ColumnFullTypePair>()

        // 获取表的所有列定义，包括数组信息和维度
        connection.prepareStatement(
            """
            SELECT a.attname as column_name,
                   pg_catalog.format_type(a.atttypid, a.atttypmod) as formatted_type
            FROM pg_catalog.pg_attribute a
            JOIN pg_catalog.pg_type ty ON a.atttypid = ty.oid
            LEFT JOIN pg_catalog.pg_attrdef d ON (a.attrelid = d.adrelid AND a.attnum = d.adnum)
            JOIN pg_catalog.pg_class c ON a.attrelid = c.oid
            JOIN pg_catalog.pg_namespace n ON c.relnamespace = n.oid
            WHERE c.relname = ?
              AND n.nspname = ?
              AND a.attnum > 0
              AND NOT a.attisdropped
            ORDER BY a.attnum
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, tableName)
            stmt.setString(2, schema ?: "public")
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val columnName = rs.getString("column_name")
                    val formattedType = rs.getString("formatted_type")

                    fullTypeMap[columnName] = ColumnFullTypePair(
                        columnName = columnName,
                        fullType = formattedType
                    )
                }   
            }
        }

        // 获取主键信息
        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        // 构建返回结果
        val columns = mutableListOf<TableInput.TargetOf_columns>()
        metadata.getColumns(
            catalog,
            schema,
            tableName,
            "%"
        ).use { rs ->
            while (rs.next()) {
                val columnName = rs.getString("COLUMN_NAME")
                val columnInfo = fullTypeMap[columnName]

                val remarks = rs.getString("REMARKS") ?: ""
                val typeName = columnInfo?.fullType ?: rs.getString("TYPE_NAME")
                val dataSize = rs.getInt("COLUMN_SIZE").takeIf { it != 0 }
                val numericPrecision = rs.getInt("DECIMAL_DIGITS").takeIf { it != 0 }
                val nullable = rs.getString("IS_NULLABLE") == "YES"
                val defaultValue = rs.getString("COLUMN_DEF")
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
        }

        return columns
    }

    private fun isAutoIncrement(tableName: String, columnName: String): Boolean {
        connection.prepareStatement(
            """
            SELECT column_default
            FROM information_schema.columns
            WHERE table_name = ?
              AND column_name = ?
              AND table_schema = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, tableName)
            stmt.setString(2, columnName)
            stmt.setString(3, schema ?: "public")
            stmt.executeQuery().use { rs ->
                if (rs.next()) {
                    val defaultValue = rs.getString("column_default")
                    return defaultValue?.startsWith("nextval(") == true
                }
            }
        }
        return false
    }

    override fun fetchTableIndexes(tableName: String): List<TableInput.TargetOf_indexes> {
        val indexes = mutableMapOf<String, TableInput.TargetOf_indexes>()

        connection.prepareStatement(
            """
        SELECT 
            idx.relname AS index_name,
            a.attname AS column_name,
            i.indisunique AS is_unique,
            i.indisprimary AS is_primary,
            CASE 
                WHEN i.indpred IS NOT NULL THEN pg_get_expr(i.indpred, i.indrelid)
            END AS predicate
        FROM pg_index i
        JOIN pg_class idx ON i.indexrelid = idx.oid
        JOIN pg_class tbl ON i.indrelid = tbl.oid
        JOIN pg_namespace n ON tbl.relnamespace = n.oid
        LEFT JOIN LATERAL unnest(i.indkey) WITH ORDINALITY AS ak(key, k) ON TRUE
        LEFT JOIN pg_attribute a ON a.attrelid = tbl.oid AND a.attnum = ak.key
        WHERE tbl.relname = ?
          AND n.nspname = ?
          AND i.indisprimary = false
        ORDER BY idx.relname, ak.k
        """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, tableName)
            stmt.setString(2, schema ?: "public")
            stmt.executeQuery().use { rs ->

                while (rs.next()) {
                    val indexName = rs.getString("index_name")
                    val columnName = rs.getString("column_name")
                    val isUnique = rs.getBoolean("is_unique")
                    val predicate = rs.getString("predicate")

                    indexes[indexName] = indexes[indexName]?.let { existing ->
                        existing.copy(
                            columnNames = existing.columnNames + columnName
                        )
                    } ?: TableInput.TargetOf_indexes(
                        name = indexName,
                        columnNames = listOf(columnName),
                        uniqueIndex = isUnique,
                        wherePredicates = predicate
                    )
                }
            }
        }

        return indexes.values.toList()
    }

    override fun fetchTableChecks(tableName: String): List<TableInput.TargetOf_checks> {
        val checks = mutableListOf<TableInput.TargetOf_checks>()

        // 获取 CHECK 约束信息
        connection.prepareStatement(
            """
            SELECT conname AS constraint_name,
                   pg_get_constraintdef(c.oid) AS constraint_definition
            FROM pg_constraint c
            JOIN pg_class cls ON c.conrelid = cls.oid
            JOIN pg_namespace nsp ON cls.relnamespace = nsp.oid
            WHERE contype = 'c'
              AND cls.relname = ?
              AND nsp.nspname = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, tableName)
            stmt.setString(2, schema ?: "public")
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val constraintName = rs.getString("constraint_name")
                    val definition = rs.getString("constraint_definition")

                    checks.add(
                        TableInput.TargetOf_checks(
                            name = constraintName,
                            expression = definition
                        )
                    )
                }
            }
        }

        return checks
    }
}
