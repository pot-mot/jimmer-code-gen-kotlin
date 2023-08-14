package top.potmot.service

import top.potmot.model.GenDataSource
import top.potmot.model.GenTableGroup
import top.potmot.model.dto.GenDataSourceInsertInput
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import java.util.*

interface DataSourceService {
    /**
     * 向 GenDataSource 导入目标数据源中的表
     * @param groupId 组 ID，置空时导入的表将未分组
     */
    fun importTables(dataSource: GenDataSourceInsertInput, groupId: Long? = null): GenTableGroup

    /**
     * 向 GenDataSource 导入表
     * @param tables 导入的表
     * @param groupId 组 ID，置空时导入的表将未分组
     */
    fun importTables(tables: List<GenTableColumnsInput>, groupId: Long? = null): List<Optional<GenTableColumnsView>>

    /**
     * 清除 GenDataSource 某组中的表或未分组的表并重新导入目标数据源中的表
     * @param groupId 组 ID，置空时将清空未分组的表
     */
    fun refreshTables(dataSource: GenDataSourceInsertInput, groupId: Long? = null): List<GenTableColumnsView>

    /**
     * 清除 GenDataSource 某组中的表或未分组的表并重新导入
     * @param tables 重新导入的表
     * @param groupId 组 ID，置空时将清空未分组的表
     */
    fun refreshTables(tables: List<GenTableColumnsInput>, groupId: Long? = null): List<Optional<GenTableColumnsView>>
}
