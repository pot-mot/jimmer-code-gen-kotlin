package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.query.TableGroupQuery
import top.potmot.service.TableGroupService

@Service
class TableGroupServiceImpl(
    @Autowired val genTableGroupRepository: GenTableGroupRepository
): TableGroupService {
    @Transactional
    override fun createGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return GenTableGroupCommonView(genTableGroupRepository.insert(group))
    }

    @Transactional
    override fun editGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return GenTableGroupCommonView(genTableGroupRepository.update(group))
    }

    @Transactional
    override fun moveGroups(ids: List<Long>, groupId: Long): GenTableGroupCommonView {
        TODO("Not yet implemented")
    }

    override fun queryGroups(query: TableGroupQuery): List<GenTableGroupCommonView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteGroups(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
