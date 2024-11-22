package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.query.EntityQuery
import top.potmot.entity.query.Query
import top.potmot.entity.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired val sqlClient: KSqlClient,
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEntityDetailView? =
        sqlClient.findById(GenEntityDetailView::class, id)

    @GetMapping("/language")
    fun listLanguage(): List<GenLanguage> =
        GenLanguage.entries

    @PostMapping("/query")
    fun query(@RequestBody query: EntityQuery): List<GenEntityDetailView> =
        sqlClient.queryEntity(query, GenEntityDetailView::class)

    private fun <T : View<GenEntity>> KSqlClient.queryEntity(query: Query<GenEntity>, viewClass: KClass<T>): List<T> =
        createQuery(GenEntity::class) {
            where(query)
            select(table.fetch(viewClass))
        }.execute()
}
