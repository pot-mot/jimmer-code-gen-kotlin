package top.potmot.service.impl

import org.babyfish.jimmer.View
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.GenAssociation
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.query.TableGroupQuery
import top.potmot.service.TableGroupService
import kotlin.reflect.KClass

@Service
class TableGroupServiceImpl(
    @Autowired val tableGroupRepository: GenTableGroupRepository
): TableGroupService {
    @Transactional
    override fun createGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return GenTableGroupCommonView(tableGroupRepository.insert(group))
    }

    @Transactional
    override fun editGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return GenTableGroupCommonView(tableGroupRepository.update(group))
    }

    @Transactional
    override fun moveGroups(ids: List<Long>, groupId: Long): Int {
        TODO("Not yet implemented")
    }

    override fun <T : View<GenAssociation>> queryGroups(query: TableGroupQuery, viewCLass: KClass<T>): List<T> {
        TODO("Not yet implemented")
    }


    @Transactional
    override fun deleteGroups(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
