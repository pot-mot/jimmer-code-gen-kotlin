package top.potmot.core.model.business

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.babyfish.jimmer.sql.kt.ast.table.isNotNull
import org.babyfish.jimmer.sql.kt.ast.table.isNull
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.by
import top.potmot.entity.column
import top.potmot.entity.`column?`
import top.potmot.entity.dto.GenEntityModelView
import top.potmot.entity.dto.GenPropertyModelView
import top.potmot.entity.entityId
import top.potmot.entity.id
import top.potmot.entity.modelId

data class EntityModelBusinessView(
    val tableConvertedEntity: GenEntityModelView,
    val otherProperties: List<GenPropertyModelView>,
)

private val EntityModelViewFetcher = newFetcher(GenEntity::class).by(GenEntityModelView.METADATA.fetcher) {
    properties(
        GenEntityModelView.TargetOf_properties.METADATA.fetcher
    ) {
        filter {
            where(table.column.isNotNull())
        }
    }
}

fun getEntityModelBusinessViews(
    sqlClient: KSqlClient,
    modelId: Long,
    excludeEntityIds: List<Long>?,
): List<EntityModelBusinessView> {
    val entities = sqlClient.executeQuery(GenEntity::class) {
        where(table.modelId eq modelId)
        excludeEntityIds?.takeIf { it.isNotEmpty() }?.let {
            where(table.id valueNotIn it)
        }
        select(table.fetch(EntityModelViewFetcher))
    }

    val propertiesMap = sqlClient.executeQuery(GenProperty::class) {
        where(table.`column?`.isNull())
        where(table.entityId valueIn entities.map { it.id })
        select(table.entityId, table.fetch(GenPropertyModelView.METADATA.fetcher))
    }.groupBy {
        it._1
    }

    return entities.map {
        EntityModelBusinessView(
            GenEntityModelView(it),
            propertiesMap[it.id]
                ?.map { (_, property) -> GenPropertyModelView(property) }
                ?: emptyList()
        )
    }
}