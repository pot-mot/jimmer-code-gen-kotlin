package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityCommonView
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.query.EntityQuery
import top.potmot.entity.query.Query
import top.potmot.entity.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    /**
     * 列出所有数据源
     */
    @GetMapping
    fun list(): List<GenEntityCommonView> =
        sqlClient.createQuery(GenEntity::class) {
            select(table.fetch(GenEntityCommonView::class))
        }.execute()

    /**
     * 获取单个数据源
     */
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEntityPropertiesView? =
        sqlClient.findById(GenEntityPropertiesView::class, id)

    @GetMapping("/language")
    fun listLanguage(): List<GenLanguage> =
        GenLanguage.entries

    @PostMapping("/query")
    fun query(@RequestBody query: EntityQuery): List<GenEntityPropertiesView> =
        sqlClient.queryEntity(query, GenEntityPropertiesView::class)

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int =
        transactionTemplate.execute {
            sqlClient.deleteByIds(GenEntity::class, ids).totalAffectedRowCount
        }!!

    private fun <T : View<GenEntity>> KSqlClient.queryEntity(query: Query<GenEntity>, viewClass: KClass<T>): List<T> =
        createQuery(GenEntity::class) {
            where(query)
            select(table.fetch(viewClass))
        }.execute()
}
