package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

): TableService {
    @Transactional
    override fun moveTables(ids: List<Long>, groupId: Long): Int {
        TODO("Not yet implemented")
    }

    override fun queryTables(query: TableQuery): List<GenTableColumnsView> {
        TODO("Not yet implemented")
    }

    override fun queryColumns(query: ColumnQuery): List<GenColumnCommonView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTables(ids: Iterable<Long>): Int {
        TODO("Not yet implemented")
    }

}
