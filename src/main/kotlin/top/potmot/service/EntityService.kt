package top.potmot.service

import org.babyfish.jimmer.View
import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.query.EntityQuery
import java.util.*
import kotlin.reflect.KClass

/**
 * 实体业务类
 */
interface EntityService {
    /**
     * 映射实体，并且保存
     */
    fun mapEntities(tableIds: List<Long>): List<GenEntityPropertiesView>

    /**
     * 同步实体，并且保存
     * 将结合已有的所有由当前 table 衍生出的 GenEntity 重新进行生成
     */
    fun syncEntities(tableIds: List<Long>): List<GenEntityPropertiesView>

    /**
     * 保存实体
     */
    fun saveEntities(entities: List<GenEntityPropertiesInput>): List<GenEntityPropertiesView>

    /**
     * 配置实体
     */
    fun configEntity(entity: GenEntityConfigInput): GenEntityPropertiesView

    /**
     * 查询实体
     */
    fun <T : View<GenEntity>> queryEntities(query: EntityQuery, viewCLass: KClass<T>): List<T>

    /**
     * 删除实体
     */
    fun deleteEntities(ids: List<Long>): Int
}
