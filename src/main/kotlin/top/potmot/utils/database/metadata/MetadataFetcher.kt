package top.potmot.utils.database.metadata

import org.slf4j.LoggerFactory
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbColumnRef
import top.potmot.entity.database.dto.TableInput
import java.sql.Connection
import java.sql.DatabaseMetaData

open class MetadataFetcher(
    protected val connection: Connection,
    protected val catalog: String? = connection.catalog,
    protected val schema: String? = connection.schema,
) {
    fun fetch(): List<TableInput> {
        return fetchTables()
    }

    protected val logger = LoggerFactory.getLogger(MetadataFetcher::class.java)

    protected val metadata: DatabaseMetaData = connection.metaData

    protected open fun fetchTables(): List<TableInput> {
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
                val indexes = fetchTableIndexes(tableName)
                val foreignKeys = fetchTableForeignKeys(tableName)
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

    protected open fun fetchTableColumns(
        tableName: String
    ): List<TableInput.TargetOf_columns> {
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

    protected open fun fetchPrimaryKeys(
        tableName: String
    ): List<String> {
        val primaryKeys = mutableListOf<String>()
        metadata.getPrimaryKeys(
            catalog,
            schema,
            tableName
        ).use { rs ->
            while (rs.next()) {
                primaryKeys.add(rs.getString("COLUMN_NAME"))
            }
        }

        return primaryKeys
    }

    protected open fun fetchTableIndexes(
        tableName: String
    ): List<TableInput.TargetOf_indexes> {
        val indexes = mutableMapOf<String, TableInput.TargetOf_indexes>()
        metadata.getIndexInfo(
            catalog,
            schema,
            tableName,
            false,
            true
        ).use { rs ->
            while (rs.next()) {
                val indexName = rs.getString("INDEX_NAME") ?: continue
                val columnName = rs.getString("COLUMN_NAME") ?: continue
                val nonUnique = rs.getBoolean("NON_UNIQUE")

                indexes[indexName] = indexes[indexName]?.let { existing ->
                    existing.copy(
                        columnNames = existing.columnNames + columnName
                    )
                } ?: TableInput.TargetOf_indexes(
                    name = indexName,
                    columnNames = listOf(columnName),
                    uniqueIndex = !nonUnique,
                    wherePredicates = null
                )
            }
        }

        return indexes.values.toList()
    }

    protected open fun fetchTableForeignKeys(
        tableName: String
    ): List<TableInput.TargetOf_foreignKeys> {
        val foreignKeys = mutableMapOf<String, TableInput.TargetOf_foreignKeys>()
        metadata.getImportedKeys(
            catalog,
            schema,
            tableName
        ).use { rs ->
            while (rs.next()) {
                val fkName = rs.getString("FK_NAME") ?: continue
                val fkComment = ""
                val fkColumnName = rs.getString("FKCOLUMN_NAME") ?: continue
                val pkTableSchema = rs.getString("PKTABLE_SCHEM") ?: ""
                val pkTableName = rs.getString("PKTABLE_NAME") ?: continue
                val pkColumnName = rs.getString("PKCOLUMN_NAME") ?: continue
                val onUpdate = rs.getInt("UPDATE_RULE")
                val onDelete = rs.getInt("DELETE_RULE")

                foreignKeys[fkName] = foreignKeys[fkName]?.let { existing ->
                    existing.copy(
                        columnRefs = existing.columnRefs + DbColumnRef(
                            columnName = fkColumnName,
                            referencedColumnName = pkColumnName
                        ),
                    )
                    existing
                } ?: TableInput.TargetOf_foreignKeys(
                    name = fkName,
                    comment = fkComment,
                    referencedTableName = pkTableName,
                    referencedTableSchema = pkTableSchema,
                    onUpdate = mapForeignKeyAction(onUpdate),
                    onDelete = mapForeignKeyAction(onDelete),
                    columnRefs = listOf(
                        DbColumnRef(
                            columnName = fkColumnName,
                            referencedColumnName = pkColumnName
                        )
                    ),
                )
            }
        }

        return foreignKeys.values.toList()
    }

    protected open fun mapForeignKeyAction(
        rule: Int?
    ): String? {
        return when (rule) {
            DatabaseMetaData.importedKeyCascade -> "CASCADE"
            DatabaseMetaData.importedKeyRestrict -> "RESTRICT"
            DatabaseMetaData.importedKeySetNull -> "SET NULL"
            DatabaseMetaData.importedKeyNoAction -> "NO ACTION"
            DatabaseMetaData.importedKeySetDefault -> "SET DEFAULT"
            else -> null
        }
    }

    protected open fun fetchTableChecks(
        tableName: String
    ): List<TableInput.TargetOf_checks> {
        logger.warn("Check constraints are not supported in generic metadata fetcher. Use database-specific implementation.")
        return emptyList()
    }
}

private fun getTypeFromConnection(connection: Connection): DatabaseType? =
    when (connection.metaData.databaseProductName.lowercase()) {
        "mysql" -> DatabaseType.MYSQL
        "postgresql" -> DatabaseType.POSTGRESQL
        "oracle" -> DatabaseType.ORACLE
        "microsoft sql server" -> DatabaseType.SQLSERVER
        "h2" -> DatabaseType.H2
        "sqlite" -> DatabaseType.SQLITE
        else -> null
    }

fun fetchMetadata(
    connection: Connection,
    catalog: String? = connection.catalog,
    schema: String? = connection.schema,
    databaseType: DatabaseType? = getTypeFromConnection(connection)
) =
    when (databaseType) {
        DatabaseType.MYSQL -> MySQLMetadataFetcher(connection, catalog, schema).fetch()
        DatabaseType.POSTGRESQL -> PostgreSQLMetadataFetcher(connection, catalog, schema).fetch()
        DatabaseType.ORACLE -> OracleMetadataFetcher(connection, catalog, schema).fetch()
        DatabaseType.SQLSERVER -> SqlServerMetadataFetcher(connection, catalog, schema).fetch()
        DatabaseType.H2 -> H2MetadataFetcher(connection, catalog, schema).fetch()
        DatabaseType.SQLITE -> SqliteMetadataFetcher(connection, catalog, schema).fetch()
        else -> MetadataFetcher(connection, catalog, schema).fetch()
    }
