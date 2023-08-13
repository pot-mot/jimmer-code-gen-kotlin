package top.potmot.service

import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.TableGroupQuery
import java.util.*

/**
 * 表管理业务类
 */
interface TableManageService {
    /**
     * 创建组
     */
    fun createGroup(group: GenTableGroupCommonInput): Optional<GenTableGroupCommonView>

    /**
     * 编辑组
     */
    fun editGroup(group: GenTableGroupCommonInput): GenTableGroupCommonView

    /**
     * 移动表所在组
     */
    fun moveTables(ids: List<Long>, groupId: Long): Int


    /**
     * 获取指定组下的递归组树和表
     * @param groupIds 组 ID，empty 将获得未分组的表，null 将获得所有递归组树
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
