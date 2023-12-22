package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenColumn
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.id
import top.potmot.model.name
import top.potmot.model.partOfPk
import top.potmot.model.query.ColumnQuery
import top.potmot.model.tableId
import top.potmot.model.type
import top.potmot.model.typeCode
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

            query.type?.let {
                where(table.type eq it)
            }
            query.typeCode?.let {
                where(table.typeCode eq it)
            }
            query.partOfPk?.let {
                where(table.partOfPk eq it)
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
