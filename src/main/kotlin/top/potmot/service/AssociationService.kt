package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import top.potmot.enum.AssociationMatchType
import top.potmot.enum.AssociationType
import top.potmot.enum.SelectType
import top.potmot.enum.getMatch
import top.potmot.model.*
import top.potmot.model.dto.*
import top.potmot.model.query.AssociationQuery
import top.potmot.model.query.TableQuery
import top.potmot.core.match.AssociationMatch
import top.potmot.core.match.simplePkColumnMatch
import top.potmot.extension.newGenAssociationMatchView
import top.potmot.extension.toColumnMatchViews
import kotlin.reflect.KClass

@RestController
@RequestMapping("/association")
class AssociationService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val tableService: TableService
) {
    @GetMapping("/select/table/{tableIds}")
    fun selectByTable(
        @PathVariable tableIds: List<Long>,
        @RequestParam(defaultValue = "OR") selectType: SelectType
    ): List<GenAssociationView> {
        return selectByTable(tableIds, selectType, GenAssociationView::class)
    }

    @GetMapping("/select/column/{columnIds}")
    fun selectByColumn(
        @PathVariable columnIds: List<Long>,
        @RequestParam(defaultValue = "OR") selectType: SelectType
    ): List<GenAssociationView> {
        return selectByColumn(columnIds, selectType, GenAssociationView::class)
    }

    @GetMapping("/query")
    fun query(query: AssociationQuery): List<GenAssociationView> {
        return query(query, GenAssociationView::class)
    }

    @PostMapping("/save")
    @Transactional
    fun save(@RequestBody associations: List<GenAssociationInput>): List<Long> {
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

    @DeleteMapping("/table/{tableIds}")
    @Transactional
    fun deleteByTable(
        @PathVariable tableIds: List<Long>,
        @RequestParam(defaultValue = "AND") selectType: SelectType
    ): Int {
        val ids = selectByTable(tableIds, selectType, GenAssociationIdView::class).map { it.id }
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @DeleteMapping("/column/{columnIds}")
    @Transactional
    fun deleteByColumn(
        @PathVariable columnIds: List<Long>,
        @RequestParam(defaultValue = "AND") selectType: SelectType
    ): Int {
        val ids = selectByColumn(columnIds, selectType, GenAssociationIdView::class).map { it.id }
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @GetMapping("/matchType")
    fun listMatchType(): List<AssociationMatchType> {
        return AssociationMatchType.values().toList()
    }

    @PostMapping("/match")
    fun match(
        @RequestBody tableIds: List<Long>,
        @RequestParam(defaultValue = "SIMPLE_PK") matchType: AssociationMatchType
    ): List<GenAssociationMatchView> {
        val columns = tableService.query(TableQuery(ids = tableIds), GenTableColumnView::class)
            .flatMap { it.toColumnMatchViews() }
        return matchColumns(columns, matchType.getMatch())
    }

    fun matchColumns(
        columns: List<GenColumnMatchView>,
        match: AssociationMatch = simplePkColumnMatch
    ): List<GenAssociationMatchView> {
        val result = mutableListOf<GenAssociationMatchView>()

        columns.forEach { source ->
            if (source.table != null)
                columns.forEach { target ->
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
                where(table.createdTime.between(it.start, it.end))
            }
            query.modifiedTime?.let {
                where(table.createdTime.between(it.start, it.end))
            }

            select(table.fetch(viewClass))
        }.execute()
    }

    fun <T : View<GenAssociation>> selectByTable(
        tableIds: List<Long>,
        selectType: SelectType,
        viewClass: KClass<T>
    ): List<T> {
        return sqlClient.createQuery(GenAssociation::class) {
            tableIds.takeIf { it.isNotEmpty() }?.let {
                when (selectType) {
                    SelectType.AND -> {
                        where(
                            table.sourceColumn.tableId valueIn it,
                            table.targetColumn.tableId valueIn it
                        )
                    }

                    SelectType.OR -> {
                        where(
                            or(
                                table.sourceColumn.tableId valueIn it,
                                table.targetColumn.tableId valueIn it
                            )
                        )
                    }
                }
            }
            select(table.fetch(viewClass))
        }.execute()
    }

    fun <T : View<GenAssociation>> selectByColumn(
        columnIds: List<Long>,
        selectType: SelectType,
        viewClass: KClass<T>
    ): List<T> {
        return sqlClient.createQuery(GenAssociation::class) {
            columnIds.takeIf { it.isNotEmpty() }?.let {
                when (selectType) {
                    SelectType.AND -> {
                        where(
                            table.sourceColumn.id valueIn it,
                            table.targetColumn.id valueIn it
                        )
                    }

                    SelectType.OR -> {
                        where(
                            or(
                                table.sourceColumn.id valueIn it,
                                table.targetColumn.id valueIn it
                            )
                        )
                    }
                }
            }
            select(table.fetch(viewClass))
        }.execute()
    }
}
