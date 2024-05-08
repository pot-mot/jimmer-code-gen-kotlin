package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.DataSourceType
import top.potmot.entity.GenColumnDefault
import top.potmot.entity.GenTypeMapping
import top.potmot.entity.dataSourceType
import top.potmot.entity.dto.GenColumnDefaultInput
import top.potmot.entity.dto.GenColumnDefaultView
import top.potmot.entity.orderKey

@RestController
@RequestMapping("/columnDefault")
class ColumnDefaultService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenColumnDefaultView? =
        sqlClient.findById(GenColumnDefaultView::class, id)

    @GetMapping
    fun list(
        @RequestParam(required = false) dataSourceType: DataSourceType? = null
    ): List<GenColumnDefaultView> =
        sqlClient.createQuery(GenColumnDefault::class) {
            if (dataSourceType != null) {
                where(table.dataSourceType eq dataSourceType)
            }
            orderBy(table.orderKey)
            select(table.fetch(GenColumnDefaultView::class))
        }.execute()

    @PostMapping
    fun saveAll(@RequestBody typeMappings: List<GenColumnDefaultInput>): List<Long> =
        transactionTemplate.execute {
            sqlClient.createDelete(GenTypeMapping::class) {}.execute()
            sqlClient.entities.saveInputs(typeMappings).simpleResults.map { it.modifiedEntity.id }
        }!!
}
