package top.potmot.service.impl

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenDataSourceRepository
import top.potmot.dao.GenSchemaRepository
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.dataSourceId
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.dto.GenSchemaView
import top.potmot.service.DataSourceService
import top.potmot.util.extension.getCatalog
import top.potmot.util.extension.test
import top.potmot.util.extension.toGenSchema
import top.potmot.util.extension.toGenSchemas
import top.potmot.util.extension.toSource

@Service
class DataSourceServiceImpl(
    @Autowired val dataSourceRepository: GenDataSourceRepository,
    @Autowired val schemaRepository: GenSchemaRepository,
) : DataSourceService {
    @Transactional
    override fun saveDataSource(dataSource: GenDataSourceInput): GenDataSource {
        dataSource.toEntity().test()
        return dataSourceRepository.insert(dataSource)
    }

    @Transactional
    override fun deleteDataSources(ids: List<Long>): Int {
        return dataSourceRepository.deleteByIds(ids, DeleteMode.PHYSICAL)
    }

    override fun listDataSources(): List<GenDataSourceView> {
        return dataSourceRepository.findAll(GenDataSourceView.METADATA.fetcher).map {
            GenDataSourceView(it)
        }
    }

    override fun viewSchemas(dataSourceId: Long): List<GenSchema> {
        return dataSourceRepository.findById(dataSourceId).map {
            val dataSource = it.toSource()
            val schemaList = dataSource.getCatalog(withoutTable = true)
                .schemas.map { schema ->
                schema.toGenSchema(dataSourceId)
            }
            dataSource.close()
            schemaList
        }.orElse(emptyList())
    }

    override fun listSchemas(dataSourceId: Long): List<GenSchemaView> {
        return schemaRepository.sql
            .createQuery(GenSchema::class) {
                where(table.dataSourceId eq dataSourceId)
                select(
                    table.fetch(GenSchemaView::class)
                )
            }
            .execute()
    }

    @Transactional
    override fun importSchema(dataSourceId: Long, name: String): List<GenSchema> {
        val genDataSource = dataSourceRepository.findById(dataSourceId).get()
        val dataSource = genDataSource.toSource()
        val schemas = dataSource.getCatalog(name).toGenSchemas(dataSourceId)
        dataSource.close()
        return schemaRepository.saveAll(schemas)
    }

    @Transactional
    override fun refreshSchema(schemaId: Long): Int {
        val schema = schemaRepository.findById(schemaId).get()
        return importSchema(schema.dataSourceId, schema.name).size
    }

    @Transactional
    override fun deleteSchemas(ids: List<Long>): Int {
        return schemaRepository.deleteByIds(ids, DeleteMode.PHYSICAL)
    }
}
