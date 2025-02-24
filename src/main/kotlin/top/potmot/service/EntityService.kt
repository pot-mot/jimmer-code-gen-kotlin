package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.entity.config.EntityConfig
import top.potmot.core.entity.config.EntityConfigInput
import top.potmot.core.entity.config.EntityConfigView
import top.potmot.core.entity.config.EntityConfigViewer

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    override val transactionTemplate: TransactionTemplate,
) : EntityConfig,
    EntityConfigViewer {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): EntityConfigView? =
        sqlClient.getEntityConfigView(id)

    @GetMapping("/model/{modelId}")
    fun listByModelId(
        @PathVariable modelId: Long,
        @RequestParam(required = false) excludeEntityIds: List<Long>?
    ): List<EntityConfigView> =
        sqlClient.getEntityConfigViewByModelId(modelId, excludeEntityIds)

    @PutMapping("/config")
    fun config(@RequestBody input: EntityConfigInput) {
        sqlClient.configEntity(input)
    }

    @PutMapping("/config/list")
    fun configList(@RequestBody inputs: List<EntityConfigInput>) {
        sqlClient.configEntities(inputs)
    }
}
