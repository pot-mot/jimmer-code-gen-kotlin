package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupMoveInput
import top.potmot.model.dto.GenTableGroupTreeView
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
    override fun moveGroup(group: GenTableGroupMoveInput): GenTableGroupCommonView {
        return GenTableGroupCommonView(genTableGroupRepository.update(group))
    }

    override fun getTableTrees(groupIds: List<Long>?): List<GenTableGroupTreeView> {
        if (groupIds == null) {
            return genTableGroupRepository.findAll(GenTableGroupTreeView.METADATA.fetcher).map {
                GenTableGroupTreeView(it)
            }
        }
        return genTableGroupRepository.findByIds(ids = groupIds, GenTableGroupTreeView.METADATA.fetcher).map {
            GenTableGroupTreeView(it)
        }
    }

    override fun queryGroups(query: TableGroupQuery): List<GenTableGroupCommonView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteGroups(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
