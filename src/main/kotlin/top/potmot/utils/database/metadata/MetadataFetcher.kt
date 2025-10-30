package top.potmot.utils.database.metadata

import org.slf4j.LoggerFactory
import top.potmot.entity.database.DbColumnRef
import top.potmot.entity.database.dto.TableInput
import java.sql.Connection
import java.sql.DatabaseMetaData

open class MetadataFetcher(
    protected val connection: Connection,
) {
    fun fetch(): List<TableInput> {
        return fetchTables()
    }

    protected val logger = LoggerFactory.getLogger(MetadataFetcher::class.java)

    protected val metadata: DatabaseMetaData = connection.metaData
    protected val catalog: String? = connection.catalog
    protected val schema: String? = connection.schema

    protected open fun fetchTables(): List<TableInput> {
        val tables = mutableListOf<TableInput>()

        val resultSet = metadata.getTables(catalog, schema, "%", arrayOf("TABLE"))

        while (resultSet.next()) {
            val schema = resultSet.getString("TABLE_SCHEM") ?: ""
            val tableName = resultSet.getString("TABLE_NAME")
            val remarks = resultSet.getString("REMARKS") ?: ""

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

        resultSet.close()
        return tables
    }

    protected open fun fetchTableColumns(
        tableName: String
    ): List<TableInput.TargetOf_columns> {
        // 获取主键信息并更新列标记
        val primaryKeys = fetchPrimaryKeys(tableName).toSet()

        val columns = mutableListOf<TableInput.TargetOf_columns>()
        val resultSet = metadata.getColumns(catalog, schema, tableName, "%")

        while (resultSet.next()) {
            val columnName = resultSet.getString("COLUMN_NAME")
            val remarks = resultSet.getString("REMARKS") ?: ""
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

    protected open fun fetchPrimaryKeys(
        tableName: String
    ): List<String> {
        val primaryKeys = mutableListOf<String>()
        val resultSet = metadata.getPrimaryKeys(catalog, schema, tableName)

        while (resultSet.next()) {
            primaryKeys.add(resultSet.getString("COLUMN_NAME"))
        }

        resultSet.close()
        return primaryKeys
    }

    protected open fun fetchTableIndexes(
        tableName: String
    ): List<TableInput.TargetOf_indexes> {
        val indexes = mutableMapOf<String, TableInput.TargetOf_indexes>()
        val resultSet = metadata.getIndexInfo(catalog, schema, tableName, false, true)

        while (resultSet.next()) {
            val indexName = resultSet.getString("INDEX_NAME") ?: continue
            val columnName = resultSet.getString("COLUMN_NAME") ?: continue
            val nonUnique = resultSet.getBoolean("NON_UNIQUE")

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

        resultSet.close()
        return indexes.values.toList()
    }

    protected open fun fetchTableForeignKeys(
        tableName: String
    ): List<TableInput.TargetOf_foreignKeys> {
        val foreignKeys = mutableMapOf<String, TableInput.TargetOf_foreignKeys>()
        val resultSet = metadata.getImportedKeys(catalog, schema, tableName)

        while (resultSet.next()) {
            val fkName = resultSet.getString("FK_NAME") ?: continue
            val fkComment = ""
            val fkTableName = resultSet.getString("FKTABLE_NAME") ?: continue
            val fkTableSchema = resultSet.getString("FKTABLE_SCHEM") ?: ""
            val fkColumnName = resultSet.getString("FKCOLUMN_NAME") ?: continue
            val pkColumnName = resultSet.getString("PKCOLUMN_NAME") ?: continue
            val onUpdate = resultSet.getInt("UPDATE_RULE")
            val onDelete = resultSet.getInt("DELETE_RULE")

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
                referencedTableName = fkTableName,
                referencedTableSchema = fkTableSchema,
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

        resultSet.close()
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
