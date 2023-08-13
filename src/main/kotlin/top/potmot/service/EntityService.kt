package top.potmot.service

import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.query.EntityQuery
import java.util.*

/**
 * 实体业务类
 */
interface EntityService {
    /**
     * 预览并保存实体
     */
    fun mapEntity(table: GenTableColumnsInput): GenEntityPropertiesView

    /**
     * 预览同步后实体
     * 将结合已有的所有由当前 table 衍生出的 GenEntity 重新进行生成
     */
    fun previewSyncEntity(tableId: Long): List<GenEntityPropertiesView>

    /**
     * 保存实体
     */
    fun saveEntities(entities: List<GenEntityPropertiesInput>): List<Optional<GenEntityPropertiesView>>

    /**
     * 配置实体
     */
    fun configEntity(entity: GenEntityConfigInput): Optional<GenEntityPropertiesView>

    /**
     * 查询实体
     */
    fun queryEntities(query: EntityQuery): List<GenEntityPropertiesView>

    /**
     * 删除实体
     */
    fun deleteEntities(ids: List<Long>): Int
}
