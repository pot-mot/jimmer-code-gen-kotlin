package top.potmot.core.model.export

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.babyfish.jimmer.sql.kt.ast.table.isNull
import top.potmot.core.entity.config.EntityModelBusinessView
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.`column?`
import top.potmot.entity.dto.GenEntityModelView
import top.potmot.entity.dto.GenPropertyModelView
import top.potmot.entity.entityId
import top.potmot.entity.id
import top.potmot.entity.modelId

interface ModelEntityExport {
    val sqlClient: KSqlClient

    fun exportModelEntityBusinessViews(
        modelId: Long,
        excludeEntityIds: List<Long>?,
    ): List<EntityModelBusinessView> {
        val entities = sqlClient.executeQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            excludeEntityIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueNotIn it)
            }
            select(table.fetch(GenEntityModelView::class))
        }

        // 非列映射的属性
        val propertiesEntityIdMap = sqlClient.executeQuery(GenProperty::class) {
            where(table.`column?`.isNull())
            where(table.entityId valueIn entities.map { it.id })
            select(table.entityId, table.fetch(GenPropertyModelView::class))
        }
            .groupBy({ it._1 }, { it._2 })

        return entities.map {
            EntityModelBusinessView(
                it,
                propertiesEntityIdMap[it.id] ?: emptyList()
            )
        }
    }
}