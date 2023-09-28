package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import top.potmot.enum.DataSourceType
import top.potmot.error.DataSourceErrorCode
import top.potmot.error.DataSourceException
import top.potmot.extension.*
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.copy
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.id

@RestController
@RequestMapping("/dataSource")
class DataSourceService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/types")
    fun listTypes(): List<DataSourceType> {
        return DataSourceType.values().toList()
    }

    @GetMapping
    fun list(): List<GenDataSourceView> {
        return sqlClient.createQuery(GenDataSource::class) {
            select(table.fetch(GenDataSourceView::class))
        }.execute()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): List<GenDataSourceView> {
        return sqlClient.createQuery(GenDataSource::class) {
            where(table.id eq id)
            select(table.fetch(GenDataSourceView::class))
        }.execute()
    }

    @PostMapping
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun insert(@RequestBody dataSource: GenDataSourceInput): Long {
        dataSource.test()
        return sqlClient.insert(dataSource).modifiedEntity.id
    }

    @PutMapping("/{id}")
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun edit(@PathVariable id: Long, @RequestBody dataSource: GenDataSourceInput): Int {
        dataSource.test()
        return sqlClient.update(dataSource.toEntity().copy {
            this.id = id
            this.schemas = emptyList()
        }).totalAffectedRowCount
    }

    @PostMapping("/test")
    @Transactional
    fun test(@RequestBody dataSource: GenDataSourceInput): Boolean {
        return try {
            dataSource.test()
            true
        } catch (e: DataSourceException) {
            false
        }
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenDataSource::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @GetMapping("/{dataSourceId}/schema")
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun viewSchemas(@PathVariable dataSourceId: Long): List<GenSchema> {
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

    @PostMapping("/{dataSourceId}/schema/{name}")
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun importSchema(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String
    ): Int {
        val dataSource =
            sqlClient.findById(GenDataSource::class, dataSourceId)?.toSource()

        var result = 0

        if (dataSource != null) {
            val schemas =
                dataSource
                    .getCatalog(schemaPattern = name)
                    .toGenSchemas(dataSourceId)
            dataSource.close()


            schemas.forEach {
                result += sqlClient.save(it).totalAffectedRowCount
            }
        }

        return result
    }
}
