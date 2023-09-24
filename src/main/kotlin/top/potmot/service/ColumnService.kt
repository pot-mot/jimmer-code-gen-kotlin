package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.*
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.query.ColumnQuery
import kotlin.reflect.KClass

@RestController
@RequestMapping("/column")
class ColumnService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/query")
    fun query(query: ColumnQuery): List<GenColumnCommonView> {
        return query(query, GenColumnCommonView::class)
    }

    fun <T : View<GenColumn>> query(query: ColumnQuery, viewCLass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenColumn::class) {
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(
                        or(
                            table.comment ilike it,
                            table.name ilike it
                        )
                    )
                }
            }
            query.tableIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.tableId valueIn it)
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
