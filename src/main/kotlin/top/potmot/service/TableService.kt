package top.potmot.service

import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery

/**
 * 表业务类
 *
 * tip:
 * 作为业务代码生成器，不该支持表的编辑
 * GenTable 表中的数据应该完全依赖于数据源，所以本业务类不该提供直接的保存，避免产生无源的实体
 *  如果需要保存，请参照:
 *  @see top.potmot.service.DataSourceService.importTables
 *  @see top.potmot.service.DataSourceService.refreshTables
 * 提供对表和列的查询
 */
interface TableService {

    /**
     * 查询表
     */
    fun queryTables(query: TableQuery): List<GenTableColumnsView>

    /**
     * 查询列
     */
    fun queryColumns(query: ColumnQuery): List<GenColumnCommonView>

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
