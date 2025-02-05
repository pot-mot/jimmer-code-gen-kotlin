package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.babyfish.jimmer.sql.kt.ast.table.isNotNull
import org.babyfish.jimmer.sql.kt.ast.table.isNull
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.entity.business.EntityBusinessConfig
import top.potmot.core.entity.business.EntityModelBusinessView
import top.potmot.core.model.load.ModelInputEntities
import top.potmot.core.model.load.ModelSave
import top.potmot.core.model.load.getGraphEntities
import top.potmot.entity.GenEntity
import top.potmot.entity.GenModel
import top.potmot.entity.GenProperty
import top.potmot.entity.by
import top.potmot.entity.column
import top.potmot.entity.`column?`
import top.potmot.entity.createdTime
import top.potmot.entity.dto.GenEntityModelView
import top.potmot.entity.dto.GenModelInput
import top.potmot.entity.dto.GenModelSimpleView
import top.potmot.entity.dto.GenModelView
import top.potmot.entity.dto.GenPropertyModelView
import top.potmot.entity.entityId
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.error.LoadFromModelException
import top.potmot.error.ModelBusinessInputException
import top.potmot.utils.transaction.executeNotNull

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    override val transactionTemplate: TransactionTemplate,
) : ModelSave,
    EntityBusinessConfig {
    @GetMapping
    fun list(): List<GenModelSimpleView> =
        sqlClient.executeQuery(GenModel::class) {
            orderBy(table.createdTime)
            select(table.fetch(GenModelSimpleView::class))
        }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenModelView? =
        sqlClient.findById(GenModelView::class, id)

    @GetMapping("/valueData/{id}")
    fun getValueData(@PathVariable id: Long): ModelInputEntities? =
        sqlClient.findById(GenModelView::class, id)?.getGraphEntities()

    private val entityModelViewFetcher = newFetcher(GenEntity::class).by(GenEntityModelView.METADATA.fetcher) {
        properties(
            GenEntityModelView.TargetOf_properties.METADATA.fetcher
        ) {
            filter {
                where(table.column.isNotNull())
            }
        }
    }

    @GetMapping("/entityBusinessViews/{id}")
    fun getEntityBusinessViews(
        @PathVariable id: Long,
        @RequestParam(required = false) excludeEntityIds: List<Long>?,
    ): List<EntityModelBusinessView> {
        val entities = sqlClient.executeQuery(GenEntity::class) {
            where(table.modelId eq id)
            excludeEntityIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueNotIn it)
            }
            select(table.fetch(entityModelViewFetcher))
        }

        val propertiesMap = sqlClient.executeQuery(GenProperty::class) {
            where(table.`column?`.isNull())
            where(table.entityId valueIn entities.map { it.id })
            select(table.entityId, table.fetch(GenPropertyModelView.METADATA.fetcher))
        }
            .groupBy { it._1 }

        return entities.map {
            EntityModelBusinessView(
                GenEntityModelView(it),
                propertiesMap[it.id]
                    ?.map { (_, property) -> GenPropertyModelView(property) }
                    ?: emptyList()
            )
        }
    }

    @PostMapping
    @Throws(LoadFromModelException::class)
    fun save(
        @RequestBody input: GenModelInput,
    ): Long =
        sqlClient.saveModel(input)

    @PostMapping("/business/{id}")
    @Throws(ModelBusinessInputException::class)
    fun saveBusiness(
        @PathVariable id: Long,
        @RequestBody entities: List<EntityModelBusinessView>,
    ) =
        sqlClient.configEntities(id, entities)

    @DeleteMapping
    fun delete(@RequestParam ids: List<Long>): Int =
        transactionTemplate.executeNotNull {
            sqlClient.deleteByIds(GenModel::class, ids).affectedRowCount(GenModel::class)
        }
}
