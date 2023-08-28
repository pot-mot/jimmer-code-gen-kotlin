package top.potmot.service

import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.query.TableGroupQuery

/**
 * 表组业务类
 *
 */
interface TableGroupService {
    /**
     * 创建组
     */
    fun createGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView
    /**
     * 编辑组
     */
    fun editGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView

    /**
     * 移动组
     */
    fun moveGroups(ids: List<Long>, groupId: Long): GenTableGroupCommonView

    /**
     * 查询组
     */
    fun queryGroups(query: TableGroupQuery): List<GenTableGroupCommonView>

    /**
     * 删除组，同时删除内部的表
     */
    fun deleteGroups(ids: List<Long>): Int
}
