package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.database.load.getCatalog
import top.potmot.core.database.load.getFkAssociations
import top.potmot.core.database.load.getGenIndexes
import top.potmot.core.database.load.toGenSchema
import top.potmot.core.database.load.toGenTable
import top.potmot.error.DataSourceException
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.dataSourceId
import top.potmot.model.dto.GenSchemaView
import top.potmot.model.extension.toSource
import top.potmot.model.id
import us.fatehi.utility.datasource.DatabaseConnectionSource

@RestController
class SchemaService(
    @Autowired val sqlClient: KSqlClient
) {
    private fun getDataSource(id: Long): DatabaseConnectionSource? =
        sqlClient.findById(GenDataSource::class, id)?.toSource()

    /**
     * 预览数据源中全部的 schema
     */
    @GetMapping("/dataSource/{dataSourceId}/schema/preview")
    @Transactional
    fun preview(@PathVariable dataSourceId: Long): List<GenSchema> {
        val dataSource = getDataSource(dataSourceId)

        if (dataSource != null) {
            val schemas =
                dataSource
                    .getCatalog(withTable = false)
                    .let {catalog ->
                        catalog.schemas.mapNotNull{
                            it.toGenSchema(catalog, dataSourceId, withTable = false)
                        }
                    }
                    .map { it }
            dataSource.close()
            return schemas
        }

        return emptyList()
    }

    /**
     * 导入 schema
     */
    @PostMapping("/dataSource/{dataSourceId}/schema/{name}")
    @Transactional
    fun load(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String
    ): List<Long> {
        val dataSource = getDataSource(dataSourceId)

        val result = mutableListOf<Long>()

        if (dataSource != null) {
            // 获取目录
            val catalog = dataSource.getCatalog(schemaPattern = name)

            // 遍历 schema 进行保存 （因为一个 schema name 有可能会获取到多个不同的 schema）
            catalog.schemas.forEach {schema ->
                val genSchema = schema.toGenSchema(
                    catalog,
                    dataSourceId,
                    withTable = false
                )

                val newSchemaId = sqlClient.save(genSchema).modifiedEntity.id

                val tables = catalog.getTables(schema)

                val genTableNameMap = mutableMapOf<String, GenTable>()

                // 获取 table 的外键以生成关联
                val tableGenTablePairs = tables.map { table ->
                    val genTable = table.toGenTable(
                        schemaId = newSchemaId
                    )

                    if (genTableNameMap.containsKey(genTable.name)) {
                        throw DataSourceException.loadFail("DataSource load fail: \nmore than one table has then same name: [${genTable.name}] ")
                    }

                    val savedGenTable = sqlClient.save(genTable) {
                        this.setKeyProps(GenTable::schema, GenTable::name)
                    }.modifiedEntity

                    genTableNameMap[genTable.name] = savedGenTable

                    Pair(table, savedGenTable)
                }

                tableGenTablePairs.forEach { (table, genTable) ->
                    table.getFkAssociations(genTableNameMap)
                        .forEach { association ->
                            sqlClient.save(association)
                        }

                    table.getGenIndexes(genTable)
                        .forEach {index ->
                            sqlClient.save(index)
                        }
                }

                result += newSchemaId
            }

            dataSource.close()
        }

        return result
    }

    @GetMapping("/dataSource/{dataSourceId}/schema/{schemaIds}", "/dataSource/{dataSourceId}/schema/")
    fun list(
        @PathVariable dataSourceId: Long,
        @PathVariable(required = false) schemaIds: List<Long>?
    ): List<GenSchemaView> {
        return sqlClient
            .createQuery(GenSchema::class) {
                where(table.dataSourceId eq dataSourceId)
                schemaIds?.takeIf { it.isNotEmpty() }?.let {
                    where(table.id valueIn it)
                }
                select(table.fetch(GenSchemaView::class))
            }
            .execute()
    }

    @DeleteMapping("/schema/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenSchema::class, ids).totalAffectedRowCount
    }
}
