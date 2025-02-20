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
import top.potmot.core.model.config.ModelEntitiesConfig
import top.potmot.core.model.export.EntityExportView
import top.potmot.core.model.export.ModelEntitiesExport
import top.potmot.core.model.load.ModelInputEntities
import top.potmot.core.model.load.ModelSave
import top.potmot.core.model.load.getGraphEntities
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
    override val transactionTemplate: TransactionTemplate,
) : ModelSave,
    ModelEntitiesExport,
    ModelEntitiesConfig {
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

    @PostMapping
    @Throws(LoadFromModelException::class)
    fun save(
        @RequestBody input: GenModelInput,
    ): Long =
        sqlClient.saveModel(input)

    @GetMapping("/exportEntities/{id}")
    fun exportEntities(
        @PathVariable id: Long,
        @RequestParam(required = false) excludeEntityIds: List<Long>?,
    ): List<EntityExportView> =
        sqlClient.exportModelEntities(id, excludeEntityIds)

    @PostMapping("/configEntities/{id}")
    @Throws(ModelBusinessInputException::class)
    fun configEntities(
        @PathVariable id: Long,
        @RequestBody entities: List<EntityExportView>,
    ) =
        sqlClient.configModelEntities(id, entities)

    @DeleteMapping
    fun delete(@RequestParam ids: List<Long>): Int =
        transactionTemplate.executeNotNull {
            sqlClient.deleteByIds(GenModel::class, ids).affectedRowCount(GenModel::class)
        }
}
