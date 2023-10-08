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
import top.potmot.core.import.getFkAssociation
import top.potmot.core.import.toGenSchema
import top.potmot.core.import.toGenSchemas
import top.potmot.error.DataSourceErrorCode
import top.potmot.extension.getCatalog
import top.potmot.extension.toSource
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.dataSourceId
import top.potmot.model.dto.GenSchemaView
import top.potmot.model.id

@RestController
class SchemaService(
    @Autowired val sqlClient: KSqlClient
) {
    /**
     * 预览数据源中全部的 schema
     */
    @GetMapping("/dataSource/{dataSourceId}/schema/preview")
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun preview(@PathVariable dataSourceId: Long): List<GenSchema> {
        val dataSource =
            sqlClient.findById(GenDataSource::class, dataSourceId)?.toSource()

        if (dataSource != null) {
            val schemas =
                dataSource
                    .getCatalog(withoutTable = true)
                    .schemas.map { schema -> schema.toGenSchema(dataSourceId) }
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
    fun import(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String
    ): List<Long> {
        val dataSource =
            sqlClient.findById(GenDataSource::class, dataSourceId)?.toSource()

        val result = mutableListOf<Long>()

        if (dataSource != null) {
            val catalog = dataSource.getCatalog(schemaPattern = name)

            val genSchemas = catalog.toGenSchemas(dataSourceId)

            genSchemas.forEach {
                val newSchemaId = sqlClient.save(it.second).modifiedEntity.id

                result += newSchemaId

                catalog.getTables(it.first).forEach { table ->
                    table.getFkAssociation(newSchemaId).forEach { association ->
                        sqlClient.save(association)
                    }
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

    @Transactional
    @DeleteMapping("/schema/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenSchema::class, ids).totalAffectedRowCount
    }
}
