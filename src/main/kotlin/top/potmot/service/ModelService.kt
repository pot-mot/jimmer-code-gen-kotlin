package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
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
import top.potmot.core.entity.business.configEntities
import top.potmot.core.model.business.EntityModelBusinessView
import top.potmot.core.model.business.createEntityModelBusinessInputs
import top.potmot.core.model.business.getEntityModelBusinessViews
import top.potmot.core.model.load.ModelInputEntities
import top.potmot.core.model.load.getGraphEntities
import top.potmot.core.model.load.saveModel
import top.potmot.entity.GenModel
import top.potmot.entity.createdTime
import top.potmot.entity.dto.GenModelInput
import top.potmot.entity.dto.GenModelSimpleView
import top.potmot.entity.dto.GenModelView
import top.potmot.error.LoadFromModelException
import top.potmot.error.ModelBusinessInputException
import top.potmot.utils.transaction.executeNotNull

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate,
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenModelView? =
        sqlClient.findById(GenModelView::class, id)

    @GetMapping("/valueData/{id}")
    fun getValueData(@PathVariable id: Long): ModelInputEntities? =
        sqlClient.findById(GenModelView::class, id)?.getGraphEntities()

    @GetMapping("/entityBusinessViews/{id}")
    fun getEntityBusinessViews(
        @PathVariable id: Long,
        @RequestParam(required = false) excludeEntityIds: List<Long>?,
    ): List<EntityModelBusinessView> =
        getEntityModelBusinessViews(sqlClient, id, excludeEntityIds)

    @GetMapping
    fun list(): List<GenModelSimpleView> =
        sqlClient.executeQuery(GenModel::class) {
            orderBy(table.createdTime)
            select(table.fetch(GenModelSimpleView::class))
        }

    @PostMapping
    @Throws(LoadFromModelException::class)
    fun save(
        @RequestBody input: GenModelInput,
    ): Long =
        transactionTemplate.executeNotNull {
            saveModel(sqlClient, input)
        }

    @PostMapping("/business/{id}")
    @Throws(ModelBusinessInputException::class)
    fun saveBusiness(
        @PathVariable id: Long,
        @RequestBody entities: List<EntityModelBusinessView>,
    ) {
        transactionTemplate.execute {
            val entityModelBusinessInputs = createEntityModelBusinessInputs(
                sqlClient, id, entities,
            )

            configEntities(sqlClient, entityModelBusinessInputs)
        }
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int =
        transactionTemplate.executeNotNull {
            sqlClient.deleteByIds(GenModel::class, ids).affectedRowCount(GenModel::class)
        }
}
