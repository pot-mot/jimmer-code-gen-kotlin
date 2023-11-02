package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
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
import top.potmot.core.convert.toGenEntity
import top.potmot.core.load.getCatalog
import top.potmot.core.load.getFkAssociation
import top.potmot.core.load.getSchemas
import top.potmot.error.DataSourceErrorCode
import top.potmot.extension.toSource
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.dataSourceId
import top.potmot.model.dto.GenSchemaView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.id
import top.potmot.model.schemaId
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
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun preview(@PathVariable dataSourceId: Long): List<GenSchema> {
        val dataSource = getDataSource(dataSourceId)

        if (dataSource != null) {
            val schemas =
                dataSource
                    .getCatalog(withoutTable = true)
                    .getSchemas(dataSourceId, withTable = false, withColumn = false)
                    .map { it.second }
            dataSource.close()
            return schemas
        }

        return emptyList()
    }

    /**
     * 导入 schema
     */
    @PostMapping("/dataSource/{dataSourceId}/schema/{name}")
    @ThrowsAll(DataSourceErrorCode::class)
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

            val genSchemas = catalog.getSchemas(dataSourceId)

            // 遍历 schema 进行保存 （因为一个 schema name 有可能会获取到多个不同的 schema）
            genSchemas.forEach {(schema, genSchema) ->
                val newSchemaId = sqlClient.save(genSchema).modifiedEntity.id

                result += newSchemaId

                val tables = catalog.getTables(schema)

                // 获取 table 的外键以生成关联
                tables.forEach { table ->
                    table.getFkAssociation(newSchemaId)
                        .forEach { association ->
                            sqlClient.save(association)
                        }
                }

                // 初次导入伴随第一次基本映射
                val typeMapping = sqlClient.createQuery(GenTypeMapping::class) {
                    select(table)
                }.execute()

                // 转换成实体并进行保存
                val genTables = sqlClient.createQuery(GenTable::class) {
                    where(table.schemaId eq newSchemaId)
                    select(table.fetch(GenTableAssociationsView::class))
                }.execute()

                genTables.map { it.toGenEntity(typeMapping) }.forEach {
                    sqlClient.insert(it)
                }
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
