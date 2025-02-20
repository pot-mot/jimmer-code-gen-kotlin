package top.potmot.core.model.export

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.babyfish.jimmer.sql.kt.ast.table.isNull
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.`column?`
import top.potmot.entity.dto.GenEntityExportView
import top.potmot.entity.dto.GenPropertyExportView
import top.potmot.entity.entityId
import top.potmot.entity.id
import top.potmot.entity.modelId

data class EntityExportView(
    /**
     * 表直接转换得到的实体
     */
    val entity: GenEntityExportView,

    /**
     * 其余非转换属性
     */
    val properties: List<GenPropertyExportView>,
)

interface ModelEntitiesExport {
    fun KSqlClient.exportModelEntities(
        modelId: Long,
        excludeEntityIds: List<Long>?,
    ): List<EntityExportView> {
        val entities = executeQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            excludeEntityIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueNotIn it)
            }
            select(table.fetch(GenEntityExportView::class))
        }

        // 非列映射的属性
        val propertiesEntityIdMap = executeQuery(GenProperty::class) {
            where(table.`column?`.isNull())
            where(table.entityId valueIn entities.map { it.id })
            select(table.entityId, table.fetch(GenPropertyExportView::class))
        }
            .groupBy({ it._1 }, { it._2 })

        return entities.map {
            EntityExportView(
                it,
                propertiesEntityIdMap[it.id] ?: emptyList()
            )
        }
    }
}