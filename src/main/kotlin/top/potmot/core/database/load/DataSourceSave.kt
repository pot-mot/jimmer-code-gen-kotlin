package top.potmot.core.database.load

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.transaction.support.TransactionTemplate
import schemacrawler.schema.Catalog
import top.potmot.entity.dto.GenTableLoadView
import top.potmot.utils.transaction.executeNotNull

interface DataSourceSave {
    val transactionTemplate: TransactionTemplate

    fun KSqlClient.saveDataSourceCatalog(
        dataSourceId: Long,
        catalog: Catalog,
    ) = transactionTemplate.executeNotNull {
        val result = mutableListOf<Long>()

        // 遍历 schema 进行保存 （因为一个 schema name 有可能会获取到多个不同的 schema）
        catalog.schemas.forEach { schema ->
            val tables = catalog.getTables(schema)

            // 保存 schema
            val schemaInput = schema.toInput(dataSourceId)
            val savedSchema = save(schemaInput).modifiedEntity

            // 保存 tables
            val tableInputs = tables.map { it.toInput(savedSchema.id) }
            val savedTables = saveInputs(tableInputs).items.map {
                GenTableLoadView(it.modifiedEntity)
            }

            val tableNameMap = savedTables.associateBy { it.name }

            tables.forEach { table ->
                // 保存 associations
                val associationInputs = table.foreignKeys.map { it.toInput(tableNameMap) }
                saveInputs(associationInputs)

                // 保存 indexes
                val indexInputs = table.indexes.mapNotNull { index ->
                    tableNameMap[table.name]?.let { index.toInput(it) }
                }
                saveInputs(indexInputs)
            }

            result += savedSchema.id
        }

        result
    }
}