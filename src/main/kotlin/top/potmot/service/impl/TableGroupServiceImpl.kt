package top.potmot.service.impl

import org.springframework.stereotype.Service
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupMoveInput
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.TableGroupQuery
import top.potmot.service.TableGroupService
import java.util.*

@Service
class TableGroupServiceImpl(

): TableGroupService {
    override fun createGroup(group: GenTableGroupCommonInput): Optional<GenTableGroupCommonView> {
        TODO("Not yet implemented")
    }

    override fun editGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView {
        TODO("Not yet implemented")
    }

    override fun moveGroup(group: GenTableGroupMoveInput): GenTableGroupCommonView {
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
