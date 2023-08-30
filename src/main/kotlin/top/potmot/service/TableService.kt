package top.potmot.service

import org.babyfish.jimmer.View
import top.potmot.model.GenColumn
import top.potmot.model.GenTable
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import kotlin.reflect.KClass

/**
 * 表业务类
 *
 * tip:
 * 作为业务代码生成器，不该支持表的编辑
 * GenTable 表中的数据应该完全依赖于数据源，所以本业务类不该提供直接的保存
 * 提供对表和列的查询
 */
interface TableService {
    /**
     * 查询表
     */
    fun <T : View<GenTable>> queryTables(query: TableQuery, viewCLass: KClass<T>): List<T>

    /**
     * 查询列
     */
    fun <T : View<GenColumn>> queryColumns(query: ColumnQuery, viewCLass: KClass<T>): List<T>

    /**
     * 移动表
     */
    fun moveTables(ids: List<Long>, groupId: Long): Int

    /**
     * 删除表
     * @param ids 列 Id，empty 将不进行删除
     */
    fun deleteTables(ids: List<Long>): Int

    /**
     * 获取指定组下的递归组树和表
     * @param groupIds 组 ID，empty 将获得所有的表
     */
    fun getTableTrees(groupIds: List<Long>? = null): List<GenTableGroupTreeView>
}
