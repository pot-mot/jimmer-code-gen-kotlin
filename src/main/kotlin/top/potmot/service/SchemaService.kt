package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.database.load.getCatalog
import top.potmot.core.database.load.toInput
import top.potmot.core.database.load.toView
import top.potmot.entity.GenDataSource
import top.potmot.entity.GenSchema
import top.potmot.entity.dataSourceId
import top.potmot.entity.dto.GenSchemaPreview
import top.potmot.entity.dto.GenSchemaView
import top.potmot.entity.dto.GenTableLoadView
import top.potmot.entity.extension.use
import top.potmot.entity.id
import top.potmot.error.LoadFromDataSourceException

@RestController
class SchemaService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate,
) {
    private fun getDataSource(id: Long): GenDataSource? =
        sqlClient.findById(GenDataSource::class, id)

    /**
     * 预览数据源中全部的 schema
     */
    @GetMapping("/dataSource/{dataSourceId}/schema/preview")
    fun preview(@PathVariable dataSourceId: Long): List<GenSchemaPreview> =
        getDataSource(dataSourceId)?.use {
            it
                .getCatalog(withTable = false)
                .schemas
                .mapNotNull { schema ->
                    schema.toView(dataSourceId)
                }
        } ?: emptyList()

    /**
     * 导入 schema
     */
    @PostMapping("/dataSource/{dataSourceId}/schema/{name}")
    @Throws(LoadFromDataSourceException::class)
    fun load(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String,
    ): List<Long> =
        getDataSource(dataSourceId)?.use { dataSource ->
            val result = mutableListOf<Long>()

            // 获取目录
            val catalog = dataSource.getCatalog(schemaPattern = name)

            transactionTemplate.execute {
                // 遍历 schema 进行保存 （因为一个 schema name 有可能会获取到多个不同的 schema）
                catalog.schemas.forEach { schema ->
                    val tables = catalog.getTables(schema)

                    // 保存 schema
                    val schemaInput = schema.toInput(dataSourceId)
                    val savedSchema = sqlClient.save(schemaInput).modifiedEntity

                    // 保存 tables
                    val tableInputs = tables.map { it.toInput(savedSchema.id) }
                    val savedTables = sqlClient.entities.saveInputs(tableInputs).items.map {
                        GenTableLoadView(it.modifiedEntity)
                    }

                    val tableNameMap = savedTables.associateBy { it.name }

                    tables.forEach { table ->
                        // 保存 associations
                        val associationInputs = table.foreignKeys.map { it.toInput(tableNameMap) }
                        sqlClient.entities.saveInputs(associationInputs)

                        // 保存 indexes
                        val indexInputs = table.indexes.mapNotNull { index ->
                            tableNameMap[table.name]?.let { index.toInput(it) }
                        }
                        sqlClient.entities.saveInputs(indexInputs)
                    }

                    result += savedSchema.id
                }
            }

            result
        } ?: emptyList()

    @GetMapping("/dataSource/{dataSourceId}/schema/")
    fun list(
        @PathVariable dataSourceId: Long,
        @RequestParam(required = false) schemaIds: List<Long>?,
    ): List<GenSchemaView> =
        sqlClient
            .createQuery(GenSchema::class) {
                where(table.dataSourceId eq dataSourceId)
                schemaIds?.takeIf { it.isNotEmpty() }?.let {
                    where(table.id valueIn it)
                }
                select(table.fetch(GenSchemaView::class))
            }
            .execute()

    @DeleteMapping("/schema/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int =
        transactionTemplate.execute {
            sqlClient.deleteByIds(GenSchema::class, ids).affectedRowCount(GenSchema::class)
        }!!
}
