package top.potmot.service

import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTableGroup
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenSchemaInsertInput
import top.potmot.model.dto.GenTableColumnsView

interface SchemaService {
    fun saveDataSource(input: GenDataSourceInput): GenDataSource

    fun viewSchemas(dataSourceId: Long): List<GenSchema>

    fun deleteDataSources(ids: List<Long> = emptyList()): Int

    fun getDataSources(): List<GenDataSource>

    /**
     * 导入目标数据源中的表并生成基本表组
     * @param groupId 组 ID，置空时导入的表将未分组
     */
    fun importTables(input: GenSchemaInsertInput, groupId: Long? = null): List<GenTableGroup>

    /**
     * 向 GenDataSource 刷新表
     */
    fun refreshTables(input: GenSchemaInsertInput): List<GenTableColumnsView>
}
