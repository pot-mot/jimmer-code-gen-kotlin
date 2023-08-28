package top.potmot.service.impl

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenDataSourceRepository
import top.potmot.dao.GenSchemaRepository
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.dataSourceId
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.dto.GenSchemaView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.service.DataSourceService
import top.potmot.util.extension.getCatalog
import top.potmot.util.extension.toGenSchema
import top.potmot.util.extension.toGenTableGroup
import top.potmot.util.extension.toSource

@Service
class DataSourceServiceImpl(
    @Autowired val genDataSourceRepository: GenDataSourceRepository,
    @Autowired val genSchemaRepository: GenSchemaRepository,
    @Autowired val genTableGroupRepository: GenTableGroupRepository
) : DataSourceService {
    @Transactional
    override fun saveDataSource(input: GenDataSourceInput): GenDataSource {
        val dataSource = input.toEntity().toSource()
        dataSource.close()
        return genDataSourceRepository.insert(input)
    }

    @Transactional
    override fun deleteDataSources(ids: List<Long>): Int {
        return genDataSourceRepository.deleteByIds(ids, DeleteMode.PHYSICAL)
    }

    override fun listDataSources(): List<GenDataSourceView> {
        return genDataSourceRepository.findAll(GenDataSourceView.METADATA.fetcher).map {
            GenDataSourceView(it)
        }
    }

    override fun viewSchemas(dataSourceId: Long): List<GenSchema> {
        return genDataSourceRepository.findById(dataSourceId).map {
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
        return genSchemaRepository.sql
            .createQuery(GenSchema::class) {
                where(table.dataSourceId eq dataSourceId)
                select(
                    table.fetch(GenSchemaView::class)
                )
            }
            .execute()
    }

    @Transactional
    override fun importSchema(dataSourceId: Long, name: String, groupId: Long?): List<GenSchema> {
        val genDataSource = genDataSourceRepository.findById(dataSourceId).get()
        val dataSource = genDataSource.toSource()
        val catalog = dataSource.getCatalog(name)
        // 获取 catalog 中的 schema（包含 table）的数据并转化成 entity
        val schemasBeforeInsert = catalog.schemas.map {
            it.toGenSchema(genDataSource.id)
        }
        val schemasInserted = genSchemaRepository.saveAll(schemasBeforeInsert)
        // 将保存后的 schema 再转换成 table group 以便管理
        val groupBeforeInsert = schemasInserted.map {
            it.toGenTableGroup(dataSource.getCatalog(it.name), groupId)
        }
        genTableGroupRepository.saveAll(groupBeforeInsert)
        dataSource.close()
        return schemasInserted
    }

    @Transactional
    override fun refreshSchema(schemaId: Long): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteSchemas(ids: List<Long>): Int {
        return genSchemaRepository.deleteByIds(ids, DeleteMode.PHYSICAL)
    }
}
