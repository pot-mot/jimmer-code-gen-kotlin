package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.generate.generateTableDefine
import top.potmot.model.GenTable
import top.potmot.model.columns
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.GenTableAssociationView
import top.potmot.model.dto.GenTableColumnView
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.dto.GenTableInput
import top.potmot.model.id
import top.potmot.model.name
import top.potmot.model.query.TableQuery
import top.potmot.model.schemaId
import kotlin.reflect.KClass

@RestController
@RequestMapping("/table")
class TableService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/columnView/{ids}")
    fun listColumnView(@PathVariable ids: List<Long>): List<GenTableColumnView> {
        return query(TableQuery(ids), GenTableColumnView::class)
    }

    @GetMapping("/associationView/{id}")
    fun getAssociationView(@PathVariable id: Long): GenTableAssociationView? {
        return query(TableQuery(ids=listOf(id)), GenTableAssociationView::class).getOrNull(0)
    }

    @GetMapping("/query")
    fun query(query: TableQuery): List<GenTableCommonView> {
        return query(query, GenTableCommonView::class)
    }

    @GetMapping("/define/{id}")
    fun getTableDefine(
        @PathVariable id: Long
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()

        getAssociationView(id)?.let {
            map += generateTableDefine(it)
        }

        return map
    }

    @PostMapping
    @Transactional
    fun create(
        @RequestBody input: GenTableInput
    ): Long {
        return sqlClient.insert(input).modifiedEntity.id
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
            query.nonSchema?.let {
                where(table.schemaId.isNull())
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
