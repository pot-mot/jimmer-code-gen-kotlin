package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.enum.DataSourceType
import top.potmot.error.DataSourceErrorCode
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceView
import top.potmot.util.extension.getCatalog
import top.potmot.util.extension.test
import top.potmot.util.extension.toGenSchema
import top.potmot.util.extension.toGenSchemas
import top.potmot.util.extension.toSource

@RestController
@RequestMapping("/dataSource")
class DataSourceService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/types")
    fun listTypes(): List<String> {
        return enumValues<DataSourceType>().map { it.name }
    }

    @GetMapping
    fun list(): List<GenDataSourceView> {
        return sqlClient.createQuery(GenDataSource::class) {
            select(table.fetch(GenDataSourceView::class))
        }.execute()
    }

    @PostMapping
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun save(@RequestBody dataSource: GenDataSourceInput): GenDataSourceView {
        dataSource.toEntity().test()
        return GenDataSourceView(sqlClient.insert(dataSource).modifiedEntity)
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenDataSource::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @GetMapping("/{dataSourceId}/schema")
    @ThrowsAll(DataSourceErrorCode::class)
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
    fun importSchema(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String
    ): List<GenSchema> {
        val dataSource =
            sqlClient.findById(GenDataSource::class, dataSourceId)?.toSource()

        if (dataSource != null) {
            val schemas =
                dataSource
                    .getCatalog(schemaPattern = name)
                    .toGenSchemas(dataSourceId)
            dataSource.close()

            val result = mutableListOf<GenSchema>()

            schemas.forEach {
                result += sqlClient.save(it).modifiedEntity
            }

            return result
        }

        return emptyList()
    }
}
