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
import top.potmot.core.database.load.DataSourceSave
import top.potmot.core.database.load.getCatalog
import top.potmot.core.database.load.toView
import top.potmot.entity.GenDataSource
import top.potmot.entity.GenSchema
import top.potmot.entity.dataSourceId
import top.potmot.entity.dto.GenSchemaPreview
import top.potmot.entity.dto.GenSchemaView
import top.potmot.entity.extension.use
import top.potmot.entity.id
import top.potmot.error.DataSourceException
import top.potmot.error.LoadFromDataSourceException
import top.potmot.utils.transaction.executeNotNull

@RestController
class SchemaService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    override val transactionTemplate: TransactionTemplate,
) : DataSourceSave {
    @Throws(DataSourceException::class)
    private fun getDataSource(id: Long): GenDataSource =
        sqlClient.findById(GenDataSource::class, id)
            ?: throw DataSourceException.dataSourceNotFound(id = id)

    /**
     * 预览数据源中全部的 schema
     */
    @GetMapping("/dataSource/{dataSourceId}/schema/preview")
    @Throws(DataSourceException::class)
    fun preview(@PathVariable dataSourceId: Long): List<GenSchemaPreview> =
        getDataSource(dataSourceId).use {
            it
                .getCatalog(withTable = false)
                .schemas
                .mapNotNull { schema ->
                    schema.toView(dataSourceId)
                }
        }

    /**
     * 导入 schema
     */
    @PostMapping("/dataSource/{dataSourceId}/schema/{name}")
    @Throws(DataSourceException::class, LoadFromDataSourceException::class)
    fun load(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String,
    ): List<Long> =
        getDataSource(dataSourceId).use { dataSource ->
            // 获取目录
            val catalog = dataSource.getCatalog(schemaPattern = name)

            sqlClient.saveDataSourceCatalog(dataSourceId, catalog)
        }

    @GetMapping("/dataSource/{dataSourceId}/schema/")
    fun list(
        @PathVariable dataSourceId: Long,
        @RequestParam(required = false) schemaIds: List<Long>?,
    ): List<GenSchemaView> =
        sqlClient.executeQuery(GenSchema::class) {
            where(table.dataSourceId eq dataSourceId)
            schemaIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueIn it)
            }
            select(table.fetch(GenSchemaView::class))
        }

    @DeleteMapping("/schema")
    fun delete(@RequestParam ids: List<Long>): Int =
        transactionTemplate.executeNotNull {
            sqlClient.deleteByIds(GenSchema::class, ids).affectedRowCount(GenSchema::class)
        }
}
