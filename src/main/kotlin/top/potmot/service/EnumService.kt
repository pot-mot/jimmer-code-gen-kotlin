package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.isNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenEnum
import top.potmot.model.createdTime
import top.potmot.model.dto.GenEnumItemsInput
import top.potmot.model.dto.GenEnumItemsView
import top.potmot.model.dto.GenEnumView
import top.potmot.model.genPackage
import top.potmot.model.id
import top.potmot.model.name
import top.potmot.model.packageId
import top.potmot.model.query.EnumQuery
import kotlin.reflect.KClass

@RestController
@RequestMapping("/enum")
class EnumService(
    @Autowired val sqlClient: KSqlClient,
) {
    @PostMapping
    fun create(
        @RequestBody input: GenEnumItemsInput
    ): Long {
        return sqlClient.insert(input).modifiedEntity.id
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEnumItemsView? {
        return sqlClient.findById(GenEnumItemsView::class, id)
    }

    @GetMapping("/query")
    fun query(query: EnumQuery): List<GenEnumView> {
        return query(query, GenEnumView::class)
    }

    fun <T : View<GenEnum>> query(query: EnumQuery, viewCLass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenEnum::class) {
            query.names?.takeIf { it.isNotEmpty() }?.let {
                where(table.name valueIn it)
            }

            query.packageIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.packageId valueIn it)
            }

            query.nonPackage?.takeIf { it }?.let {
                where(table.genPackage.isNull())
            }

            query.ids?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueIn it)
            }
            query.createdTime?.let {
                where(table.createdTime.between(it.start, it.end))
            }
            query.modifiedTime?.let {
                where(table.createdTime.between(it.start, it.end))
            }

            select(table.fetch(viewCLass))
        }.execute()
    }
}
