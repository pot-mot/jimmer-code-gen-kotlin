package top.potmot.service

import top.potmot.model.GenTable
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import java.util.*

/**
 * 表业务类
 */
interface TableService {
    /**
     * 查看数据源中的表
     * @param namePattern 表的匹配式，支持数据库查询通配符
     */
    fun viewTables(namePattern: String? = null): List<GenTable>

    /**
     * 向 GenDataSource 导入全部目标数据源中的表
     * @param groupId 组 ID，置空时导入的表将未分组
     */
    fun importAllTables(groupId: Long? = null): List<GenTableColumnsView>

    /**
     * 向 GenDataSource 导入表
     * @param tables 导入的表
     * @param groupId 组 ID，置空时导入的表将未分组
     */
    fun importTables(tables: List<GenTableColumnsInput>, groupId: Long? = null): List<Optional<GenTableColumnsView>>

    /**
     * 清除 GenDataSource 某组中的表或未分组的表并重新导入全部目标数据源中的表
     * @param groupId 组 ID，置空时将清空未分组的表
     */
    fun refreshAllTables(groupId: Long? = null): List<GenTableColumnsView>

    /**
     * 清除 GenDataSource 某组中的表或未分组的表并重新导入
     * @param tables 重新导入的表
     * @param groupId 组 ID，置空时将清空未分组的表
     */
    fun refreshTables(tables: List<GenTableColumnsInput>, groupId: Long? = null): List<Optional<GenTableColumnsView>>

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
