package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.entity.config.EntityBusinessConfig
import top.potmot.core.entity.config.EntityModelBusinessInput
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.tableId
import top.potmot.enumeration.GenLanguage

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    override val transactionTemplate: TransactionTemplate,
): EntityBusinessConfig {
    @GetMapping("/language")
    fun listLanguage(): List<GenLanguage> =
        GenLanguage.entries

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEntityDetailView? =
        sqlClient.findById(GenEntityDetailView::class, id)


    @GetMapping("/table/{tableId}")
    fun getByTableId(@PathVariable tableId: Long): GenEntityDetailView? =
        sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOneOrNull()

    @PutMapping
    fun config(@RequestBody input: EntityModelBusinessInput) {
        sqlClient.configEntity(input)
    }
}
