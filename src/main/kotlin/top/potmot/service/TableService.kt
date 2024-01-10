package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenTable
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.dto.GenTableIdView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.name
import top.potmot.model.query.TableQuery
import top.potmot.model.schemaId
import top.potmot.model.type
import kotlin.reflect.KClass

@RestController
@RequestMapping("/table")
class TableService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/query/id")
    fun queryIdView(query: TableQuery): List<GenTableIdView> {
        return query(query, GenTableIdView::class)
    }

    @GetMapping("/query/common")
    fun queryCommonView(query: TableQuery): List<GenTableCommonView> {
        return query(query, GenTableCommonView::class)
    }

    @GetMapping("/query/columns")
    fun queryColumnsView(query: TableQuery): List<GenTableColumnsView> {
        return query(query, GenTableColumnsView::class)
    }

    @GetMapping("/query")
    fun queryAssociationsView(query: TableQuery): List<GenTableAssociationsView> {
        return query(query, GenTableAssociationsView::class)
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenTable::class, ids).totalAffectedRowCount
    }

    fun <T : View<GenTable>> query(query: TableQuery, viewCLass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenTable::class) {
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(
                        or(
                            table.name ilike it,
                            table.comment ilike it
                        )
                    )
                }
            }

            query.nonModel?.let {
                where(table.modelId.isNull())
            }
            query.modelIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.modelId valueIn it)
            }

            query.nonSchema?.let {
                where(table.schemaId.isNull())
            }
            query.schemaIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.schemaId valueIn it)
            }

            query.type?.let {tableType ->
                where(table.type eq tableType)
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
