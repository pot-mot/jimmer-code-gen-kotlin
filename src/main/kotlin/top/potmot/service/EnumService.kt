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
import top.potmot.entity.GenEnum
import top.potmot.entity.dto.GenEnumItemsView
import top.potmot.entity.dto.GenEnumView
import top.potmot.entity.query.EnumQuery
import top.potmot.entity.query.Query
import top.potmot.entity.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/enum")
class EnumService(
    @Autowired val sqlClient: KSqlClient,
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEnumItemsView? =
        sqlClient.findById(GenEnumItemsView::class, id)

    @PostMapping("/query")
    fun query(@RequestBody query: EnumQuery): List<GenEnumView> =
        sqlClient.queryEnum(query, GenEnumView::class)

    private fun <T : View<GenEnum>> KSqlClient.queryEnum(query: Query<GenEnum>, viewCLass: KClass<T>): List<T> =
        createQuery(GenEnum::class) {
            where(query)
            select(table.fetch(viewCLass))
        }.execute()
}
