package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.enum.AssociationType
import top.potmot.enum.SelectType
import top.potmot.model.GenAssociation
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.*
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
    @GetMapping("/select/table/{tableIds}")
    fun selectByTable(
        @PathVariable tableIds: List<Long>,
        @RequestParam(defaultValue = "OR") selectType: SelectType
    ): List<GenAssociationMatchView> {
        return selectByTable(tableIds, selectType, GenAssociationMatchView::class)
    }

    @GetMapping("/select/column/{columnIds}")
    fun selectByColumn(
        @PathVariable columnIds: List<Long>,
        @RequestParam(defaultValue = "OR") selectType: SelectType
    ): List<GenAssociationMatchView> {
        return selectByColumn(columnIds, selectType, GenAssociationMatchView::class)
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

    @DeleteMapping("/table/{tableIds}")
    @Transactional
    fun deleteByTable(@PathVariable tableIds: List<Long>, @RequestParam(defaultValue = "AND") selectType: SelectType): Int {
        val ids = selectByTable(tableIds, selectType, GenAssociationIdView::class).map { it.id }
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @DeleteMapping("/column/{columnIds}")
    @Transactional
    fun deleteByColumn(@PathVariable columnIds: List<Long>, @RequestParam(defaultValue = "AND") selectType: SelectType): Int {
        val ids = selectByColumn(columnIds, selectType, GenAssociationIdView::class).map { it.id }
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @PostMapping("/scan")
    fun scan(@RequestBody tableIds: List<Long>): List<GenAssociationMatchView> {
        val columns = tableService.query(TableQuery(ids = tableIds), GenTableColumnsView::class)
            .flatMap { it.toColumnMatchViews() }
        return matchColumns(columns)
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
