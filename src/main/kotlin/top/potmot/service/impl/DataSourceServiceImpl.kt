package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.GenDataSourceRepository
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.GenTableGroup
import top.potmot.model.dto.GenDataSourceInsertInput
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.service.DataSourceService
import top.potmot.util.extension.toTableGroup
import java.util.*

@Service
class DataSourceServiceImpl(
    @Autowired val genDataSourceRepository: GenDataSourceRepository,
    @Autowired val genTableGroupRepository: GenTableGroupRepository
): DataSourceService {
    override fun importTables(dataSource: GenDataSourceInsertInput, groupId: Long?): GenTableGroup {
        val dataSourceInserted = genDataSourceRepository.insert(dataSource)
        return genTableGroupRepository.insert(dataSourceInserted.toTableGroup(groupId))
    }

    override fun importTables(tables: List<GenTableColumnsInput>, groupId: Long?): List<Optional<GenTableColumnsView>> {
        TODO("Not yet implemented")
    }

    override fun refreshTables(dataSource: GenDataSourceInsertInput, groupId: Long?): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }

    override fun refreshTables(
        tables: List<GenTableColumnsInput>,
        groupId: Long?
    ): List<Optional<GenTableColumnsView>> {
        TODO("Not yet implemented")
    }
}
