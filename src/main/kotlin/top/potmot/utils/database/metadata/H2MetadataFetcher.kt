package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput
import java.sql.Connection

class H2MetadataFetcher(
    connection: Connection,
    catalog: String? = connection.catalog?.ifBlank { null },
    schema: String? = connection.schema?.ifBlank { null },
) : MetadataFetcher(connection, catalog, schema) {
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
                val remarks = rs.getString("REMARKS") ?: ""
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

    private fun buildFullTypeDeclaration(
        typeName: String,
        dataSize: Int?,
        numericPrecision: Int?,
    ): String {
        val uppercaseTypeName = typeName.uppercase()

        return when (uppercaseTypeName) {
            "CHARACTER", "CHARACTER VARYING", "CHAR", "VARCHAR", "VARCHAR2", "NVARCHAR", "NVARCHAR2", "NCHAR" -> {
                if (dataSize != null) "$typeName($dataSize)" else typeName
            }

            "DECIMAL", "NUMERIC" -> {
                when {
                    dataSize != null && numericPrecision != null ->
                        "$typeName($dataSize,$numericPrecision)"

                    dataSize != null ->
                        "$typeName($dataSize)"

                    else -> typeName
                }
            }

           "TIME", "TIMESTAMP" -> {
                if (numericPrecision != null) "$typeName($numericPrecision)" else typeName
            }

            else -> {
                if (uppercaseTypeName.endsWith(" WITH TIME ZONE")) {
                    if (numericPrecision != null) "${typeName.substring(0, typeName.length - 15)}($numericPrecision) WITH TIME ZONE" else typeName
                } else if (uppercaseTypeName.endsWith(" ARRAY")) {
                    if (dataSize != null) "$typeName[$dataSize]" else typeName
                } else {
                    typeName
                }
            }
        }
    }
}