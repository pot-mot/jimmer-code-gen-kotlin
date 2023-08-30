package top.potmot.service

import org.babyfish.jimmer.View
import top.potmot.model.GenAssociation
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.query.TableGroupQuery
import kotlin.reflect.KClass

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
    fun moveGroups(ids: List<Long>, groupId: Long): Int

    /**
     * 查询组
     */
    fun <T : View<GenAssociation>> queryGroups(query: TableGroupQuery, viewCLass: KClass<T>): List<T>

    /**
     * 删除组，同时删除内部的表
     */
    fun deleteGroups(ids: List<Long>): Int
}
