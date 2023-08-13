package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.MetadataDao
import top.potmot.model.GenTable
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import top.potmot.service.TableService
import java.util.*

@Service
class TableServiceImpl(
    @Autowired val metadataDao: MetadataDao
): TableService {
    override fun viewTables(namePattern: String?): List<GenTable> {
        return metadataDao.getTables(namePattern)
    }

    override fun importAllTables(groupId: Long?): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }

    override fun importTables(tables: List<GenTableColumnsInput>, groupId: Long?): List<Optional<GenTableColumnsView>> {
        TODO("Not yet implemented")
    }

    override fun refreshAllTables(groupId: Long?): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }

    override fun refreshTables(
        tables: List<GenTableColumnsInput>,
        groupId: Long?
    ): List<Optional<GenTableColumnsView>> {
        TODO("Not yet implemented")
    }

    override fun queryTables(query: TableQuery): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }

    override fun queryColumns(query: ColumnQuery): List<GenColumnCommonView> {
        TODO("Not yet implemented")
    }

    override fun deleteTables(ids: Iterable<Long>): Int {
        TODO("Not yet implemented")
    }

}
