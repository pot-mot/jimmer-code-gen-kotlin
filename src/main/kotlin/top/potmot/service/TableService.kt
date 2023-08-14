package top.potmot.service

import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery

/**
 * 表业务类
 */
interface TableService {
    /**
     * 移动表
     */
    fun moveTables(ids: List<Long>, groupId: Long): Int

    /**
     * 查询表
     */
    fun queryTables(query: TableQuery): List<GenTableColumnsView>

    /**
     * 查询列
     */
    fun queryColumns(query: ColumnQuery): List<GenColumnCommonView>

    /**
     * 删除表
     * @param ids 列 Id，empty 将不进行删除
     */
    fun deleteTables(ids: Iterable<Long>): Int
}
