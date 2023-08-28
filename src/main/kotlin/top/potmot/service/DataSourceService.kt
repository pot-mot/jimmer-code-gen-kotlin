package top.potmot.service

import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.dto.GenSchemaView
import top.potmot.model.dto.GenTableColumnsView

interface DataSourceService {
    /**
     * 保存数据源
     */
    fun saveDataSource(input: GenDataSourceInput): GenDataSource

    /**
     * 删除数据源
     */
    fun deleteDataSources(ids: List<Long>): Int

    /**
     * 获取全部数据源
     */
    fun listDataSources(): List<GenDataSourceView>

    /**
     * 预览数据源中 schema
     */
    fun viewSchemas(dataSourceId: Long): List<GenSchema>

    /**
     * 获取数据源下面的 schema
     */
    fun listSchemas(dataSourceId: Long): List<GenSchemaView>

    /**
     * 导入
     */
    fun importSchema(dataSourceId: Long, name: String, groupId: Long? = null): List<GenSchema>

    /**
     * 刷新
     */
    fun refreshSchema(schemaId: Long): List<GenTableColumnsView>

    /**
     * 删除
     */
    fun deleteSchemas(ids: List<Long>): Int
}
