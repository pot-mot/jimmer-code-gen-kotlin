package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.gt
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.lt
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
import top.potmot.enum.AssociationType
import top.potmot.model.GenAssociation
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.id
import top.potmot.model.query.AssociationQuery
import top.potmot.model.query.TableQuery
import top.potmot.model.sourceColumn
import top.potmot.model.sourceColumnId
import top.potmot.model.tableId
import top.potmot.model.targetColumn
import top.potmot.model.targetColumnId
import top.potmot.util.association.AssociationMatch
import top.potmot.util.association.simplePkColumnMatch
import top.potmot.util.extension.toColumnMatchViews
import kotlin.reflect.KClass
import top.potmot.util.extension.newGenAssociationMatchView

@RestController
@RequestMapping("/association")
class AssociationService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val tableService: TableService
) {
    @GetMapping("/select")
    fun select(tableId: Long): List<GenAssociationCommonView> {
        return select(tableId, GenAssociationCommonView::class)
    }

    @GetMapping("/query")
    fun query(query: AssociationQuery): List<GenAssociationCommonView> {
        return query(query, GenAssociationCommonView::class)
    }

    @PostMapping("/save")
    @Transactional
    fun save(@RequestBody associations: List<GenAssociationCommonInput>): List<Long> {
        val result = mutableListOf<Long>()
        associations.forEach {
            result += sqlClient.save(it).modifiedEntity.id
        }
        return result
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @PostMapping("/scan")
    fun scan(@RequestBody tableIds: List<Long>): List<GenAssociationMatchView> {
        val columns = tableService.query(TableQuery(ids = tableIds), GenTableColumnsView::class).flatMap { it.toColumnMatchViews() }
        return matchColumns(columns)
    }

    fun matchColumns(columns: List<GenColumnMatchView>, match: AssociationMatch = simplePkColumnMatch): List<GenAssociationMatchView> {
        val result = mutableListOf<GenAssociationMatchView>()

        columns.forEach{source ->
            if (source.table != null)
            columns.forEach {target ->
                if (target.table != null && source.id != target.id && match(source, target)) {
                    result += newGenAssociationMatchView(AssociationType.MANY_TO_ONE, source, target)
                }
            }
        }

        return result
    }

    fun <T : View<GenAssociation>> query(query: AssociationQuery, viewClass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenAssociation::class) {
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(table.comment ilike it)
                }
            }

            query.sourceColumnId?.let {
                where(table.sourceColumnId eq it)
            }
            query.sourceTableId?.let {
                where(table.sourceColumn.tableId eq it)
            }

            query.targetColumnId?.let {
                where(table.targetColumnId eq it)
            }
            query.targetTableId?.let {
                where(table.targetColumn.tableId eq it)
            }

            query.ids?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueIn it)
            }
            query.createdTime?.let {
                where(table.createdTime gt it.start)
                where(table.createdTime lt it.end)
            }
            query.modifiedTime?.let {
                where(table.createdTime gt it.start)
                where(table.createdTime lt it.end)
            }

            select(table.fetch(viewClass))
        }.execute()
    }

    fun <T : View<GenAssociation>> select(tableId: Long, viewClass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenAssociation::class) {
            where(table.sourceColumn.tableId eq tableId)
            or(table.targetColumn.tableId eq tableId)

            select(table.fetch(viewClass))
        }.execute()
    }
}
