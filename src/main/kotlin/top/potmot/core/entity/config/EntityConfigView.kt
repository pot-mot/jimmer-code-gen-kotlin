package top.potmot.core.entity.config

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.babyfish.jimmer.sql.kt.ast.table.isNull
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.`column?`
import top.potmot.entity.dto.GenEntityConfigView
import top.potmot.entity.dto.GenPropertyConfigView
import top.potmot.entity.entityId
import top.potmot.entity.id
import top.potmot.entity.modelId

/**
 * 实体 - 用于模型业务配置的输出
 * 同时也将在 JSON 保存时作为输入对象，此时将被被转换为 EntityModelBusinessInput
 */
data class EntityConfigView(
    /**
     * 表直接转换得到的实体
     */
    val tableConvertedEntity: GenEntityConfigView,

    /**
     * 其余非转换属性
     */
    val notConvertedProperties: List<GenPropertyConfigView>,
)

interface EntityConfigViewer {
    fun KSqlClient.getEntityConfigView(
        entityId: Long,
    ): EntityConfigView? {
        val entity = createQuery(GenEntity::class) {
            where(table.id eq entityId)
            select(table.fetch(GenEntityConfigView::class))
        }.fetchOneOrNull()

        if (entity == null) return null

        // 非列映射的属性
        val properties = executeQuery(GenProperty::class) {
            where(table.`column?`.isNull())
            where(table.entityId eq entityId)
            select(table.fetch(GenPropertyConfigView::class))
        }

        return EntityConfigView(
            entity,
            properties
        )
    }

    fun KSqlClient.getEntityConfigViewByModelId(
        modelId: Long,
        excludeEntityIds: List<Long>?,
    ): List<EntityConfigView> {
        val entities = executeQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            excludeEntityIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueNotIn it)
            }
            select(table.fetch(GenEntityConfigView::class))
        }

        // 非列映射的属性
        val propertyMap = executeQuery(GenProperty::class) {
            where(table.`column?`.isNull())
            where(table.entityId valueIn  entities.map { it.id })
            select(table.entityId, table.fetch(GenPropertyConfigView::class))
        }.groupBy({ it._1 }) { it._2 }

        return entities.map {
            EntityConfigView(
                it,
                propertyMap[it.id] ?: emptyList()
            )
        }
    }
}