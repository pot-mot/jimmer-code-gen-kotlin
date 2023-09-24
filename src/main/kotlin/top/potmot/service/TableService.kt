package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import top.potmot.model.*
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.query.TableQuery
import kotlin.reflect.KClass

@RestController
@RequestMapping("/table")
class TableService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/{ids}")
    fun list(@PathVariable ids: List<Long>): List<GenTableColumnsView> {
        return query(TableQuery(ids), GenTableColumnsView::class)
    }

    @GetMapping("/query")
    fun query(query: TableQuery): List<GenTableCommonView> {
        return query(query, GenTableCommonView::class)
    }

    @Transactional
    @DeleteMapping("/{ids}")
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
            query.groupIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.groupId valueIn it)
            }
            query.schemaIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.schemaId valueIn it)
            }
            query.name?.let {
                where(table.asTableEx().columns.name ilike it)
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
