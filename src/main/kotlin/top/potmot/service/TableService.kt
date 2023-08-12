package top.potmot.service

import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.dto.GenTableGroupTreeView
import java.util.Optional

/**
 * 表业务类
 */
interface TableService {
    /**
     * 查看数据源中的表
     * @param namePattern 表的匹配式，支持数据库查询通配符
     */
    fun viewTables(namePattern: String? = null): List<GenTableColumnsView>

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
     * 获取指定组下的所有表
     * @param groupIds 组 ID，empty 将获得未分组的表，null 将获得所有表
     */
    fun getTables(groupIds: List<Long>? = null): List<GenTableCommonView>

    /**
     * 获取指定组下的递归组树和表
     * @param groupIds 组 ID，empty 将获得未分组的表，null 将获得所有递归组树
     */
    fun getTableTrees(groupIds: List<Long>? = null): List<GenTableGroupTreeView>

    /**
     * 查询表
     * @param ids 表 Id，empty 将查询全部
     * @param keywords 模糊匹配 tableName、tableComment，empty 不进行过滤
     */
    fun queryTables(
        ids: List<Long> = emptyList(),
        keywords: List<String> = emptyList()
    ): List<GenTableColumnsView>

    /**
     * 查询列
     * @param ids 列 Id，empty 将查询全部
     * @param keywords 模糊匹配 columnName、columnComment，empty 不进行过滤
     * @param tableIds 表 Id，empty 将查询全部
     */
    fun queryColumns(
        ids: List<Long> = emptyList(),
        keywords: List<String> = emptyList(),
        tableIds: List<Long> = emptyList()
    ): List<GenColumnCommonView>

    /**
     * 删除表
     * @param ids 列 Id，empty 将不进行删除
     */
    fun deleteTables(ids: Iterable<Long>): Int
}
