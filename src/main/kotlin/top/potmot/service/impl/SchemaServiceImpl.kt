package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenDataSourceRepository
import top.potmot.dao.GenSchemaRepository
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.GenTableGroup
import top.potmot.model.dto.GenSchemaInsertInput
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.service.SchemaService
import top.potmot.util.extension.getCatalog
import top.potmot.util.extension.schemaPattern
import top.potmot.util.extension.toGenSchema
import top.potmot.util.extension.toGenTableGroup
import top.potmot.util.extension.toSource
import java.util.*

@Service
class SchemaServiceImpl(
    @Autowired val genDataSourceRepository: GenDataSourceRepository,
    @Autowired val genSchemaRepository: GenSchemaRepository,
    @Autowired val genTableGroupRepository: GenTableGroupRepository
): SchemaService {
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

    override fun importTables(tables: List<GenTableColumnsInput>, groupId: Long?): List<Optional<GenTableColumnsView>> {
        TODO("Not yet implemented")
    }

    override fun refreshTables(
        tables: List<GenTableColumnsInput>,
        groupId: Long?
    ): List<Optional<GenTableColumnsView>> {
        TODO("Not yet implemented")
    }
}
