package top.potmot.service.impl

import org.springframework.stereotype.Service
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.TableGroupQuery
import top.potmot.service.TableManageService
import java.util.*

@Service
class TableManageServiceImpl(

): TableManageService {
    override fun createGroup(group: GenTableGroupCommonInput): Optional<GenTableGroupCommonView> {
        TODO("Not yet implemented")
    }

    override fun editGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView {
        TODO("Not yet implemented")
    }

    override fun moveTables(ids: List<Long>, groupId: Long): Int {
        TODO("Not yet implemented")
    }

    override fun getTableTrees(groupIds: List<Long>?): List<GenTableGroupTreeView> {
        TODO("Not yet implemented")
    }

    override fun queryGroups(query: TableGroupQuery): List<GenTableGroupCommonView> {
        TODO("Not yet implemented")
    }

    override fun deleteGroups(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
