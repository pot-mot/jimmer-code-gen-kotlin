package top.potmot.service.impl

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenDataSourceRepository
import top.potmot.dao.GenSchemaRepository
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTableGroup
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenSchemaInsertInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.service.SchemaService
import top.potmot.util.extension.getCatalog
import top.potmot.util.extension.schemaPattern
import top.potmot.util.extension.toGenSchema
import top.potmot.util.extension.toGenTableGroup
import top.potmot.util.extension.toSource

@Service
class SchemaServiceImpl(
    @Autowired val genDataSourceRepository: GenDataSourceRepository,
    @Autowired val genSchemaRepository: GenSchemaRepository,
    @Autowired val genTableGroupRepository: GenTableGroupRepository
) : SchemaService {
    override fun saveDataSource(input: GenDataSourceInput): GenDataSource {
        return genDataSourceRepository.insert(input)
    }

    override fun viewSchemas(dataSourceId: Long): List<GenSchema> {
        return genDataSourceRepository.findById(dataSourceId).map {
            it.toSource().getCatalog().schemas.map {schema ->
                schema.toGenSchema(dataSourceId)
            }
        }.orElse(emptyList())
    }

    @Transactional
    override fun deleteDataSources(ids: List<Long>): Int {
        return genDataSourceRepository.deleteByIds(ids, DeleteMode.PHYSICAL)
    }

    override fun getDataSources(): List<GenDataSource> {
        return genDataSourceRepository.findAll()
    }

    @Transactional
    override fun importTables(input: GenSchemaInsertInput, groupId: Long?): List<GenTableGroup> {
        val genDataSource = genDataSourceRepository.findById(input.dataSourceId).get()
        val dataSource = genDataSource.toSource()
        val catalog = dataSource.getCatalog(input.schemaPattern())
        val schemasBeforeInsert = catalog.schemas.map {
            it.toGenSchema(genDataSource.id)
        }
        val schemasInserted = genSchemaRepository.saveAll(schemasBeforeInsert)
        val groupBeforeInsert = schemasInserted.map {
            it.toGenTableGroup(dataSource.getCatalog(it.name), groupId)
        }
        return genTableGroupRepository.saveAll(groupBeforeInsert)
    }

    @Transactional
    override fun refreshTables(input: GenSchemaInsertInput): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }
}
