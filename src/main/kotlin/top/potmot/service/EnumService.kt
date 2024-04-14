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
import top.potmot.model.GenEnum
import top.potmot.model.dto.GenEnumItemsInput
import top.potmot.model.dto.GenEnumItemsView
import top.potmot.model.dto.GenEnumView
import top.potmot.model.query.EnumQuery
import top.potmot.model.query.Query
import top.potmot.model.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/enum")
class EnumService(
    @Autowired val sqlClient: KSqlClient,
) {
    @PostMapping
    fun save(
        @RequestBody input: GenEnumItemsInput
    ): Long {
        return sqlClient.save(input).modifiedEntity.id
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEnumItemsView? {
        return sqlClient.findById(GenEnumItemsView::class, id)
    }

    @PostMapping("/query")
    fun query(@RequestBody query: EnumQuery): List<GenEnumView> {
        return executeQuery(query, GenEnumView::class)
    }

    fun <T : View<GenEnum>> executeQuery(query: Query<GenEnum>, viewCLass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenEnum::class) {
            where(query)
            select(table.fetch(viewCLass))
        }.execute()
    }
}
