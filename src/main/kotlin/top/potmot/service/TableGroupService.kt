package top.potmot.service

import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupMoveInput
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.TableGroupQuery

/**
 * 表管理业务类
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
    fun moveGroup(group: GenTableGroupMoveInput): GenTableGroupCommonView

    /**
     * 获取指定组下的递归组树和表
     * @param groupIds 组 ID，empty 将获得所有的表
     */
    fun getTableTrees(groupIds: List<Long>? = null): List<GenTableGroupTreeView>

    /**
     * 查询组
     */
    fun queryGroups(query: TableGroupQuery): List<GenTableGroupCommonView>

    /**
     * 删除组，同时删除内部的表
     */
    fun deleteGroups(ids: List<Long>): Int
}
