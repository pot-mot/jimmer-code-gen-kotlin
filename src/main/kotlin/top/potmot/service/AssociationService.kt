package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.between
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.match.AssociationMatch
import top.potmot.core.match.simplePkColumnMatch
import top.potmot.enumeration.AssociationMatchType
import top.potmot.enumeration.SelectType
import top.potmot.model.extension.newGenAssociationMatchView
import top.potmot.model.GenAssociation
import top.potmot.model.GenColumn
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.GenAssociationIdView
import top.potmot.model.dto.GenAssociationInput
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenAssociationView
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.model.id
import top.potmot.model.query.AssociationQuery
import top.potmot.model.sourceColumn
import top.potmot.model.sourceColumnId
import top.potmot.model.tableId
import top.potmot.model.targetColumn
import top.potmot.model.targetColumnId
import kotlin.reflect.KClass

@RestController
@RequestMapping("/association")
class AssociationService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/query")
    fun query(query: AssociationQuery): List<GenAssociationView> {
        return query(query, GenAssociationView::class)
    }

    @GetMapping("/table")
    fun queryByTable(
        @RequestParam tableIds: List<Long>,
        @RequestParam(defaultValue = "OR") selectType: SelectType
    ): List<GenAssociationView> {
        return queryByTable(tableIds, selectType, GenAssociationView::class)
    }

    @GetMapping("/column")
    fun queryByColumn(
        @RequestParam sourceColumnIds: List<Long>,
        @RequestParam targetColumnIds: List<Long>,
        @RequestParam(defaultValue = "OR") selectType: SelectType
    ): List<GenAssociationView> {
        return queryByColumn(
            sourceColumnIds,
            targetColumnIds,
            selectType,
            GenAssociationView::class
        )
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

    @DeleteMapping("/table")
    @Transactional
    fun deleteByTable(
        @RequestParam tableIds: List<Long>,
        @RequestParam(defaultValue = "AND") selectType: SelectType
    ): Int {
        val ids = queryByTable(tableIds, selectType, GenAssociationIdView::class).map { it.id }
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @DeleteMapping("/column")
    @Transactional
    fun deleteByColumn(
        @RequestParam sourceColumnIds: List<Long>,
        @RequestParam targetColumnIds: List<Long>,
        @RequestParam(defaultValue = "AND") selectType: SelectType
    ): Int {
        val ids = queryByColumn(sourceColumnIds, targetColumnIds, selectType, GenAssociationIdView::class).map { it.id }
        return sqlClient.deleteByIds(GenAssociation::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @PostMapping("/match")
    fun match(
        @RequestBody tableIds: List<Long>,
        @RequestParam(defaultValue = "SIMPLE_PK") matchType: AssociationMatchType
    ): List<GenAssociationMatchView> {
        val columns = sqlClient.createQuery(GenColumn::class) {
            where(
                table.tableId valueIn tableIds
            )
            select(table.fetch(GenColumnMatchView::class))
        }.execute()
        return matchColumns(columns, matchType.toMatchMethod())
    }

    fun matchColumns(
        columns: List<GenColumnMatchView>,
        match: AssociationMatch = simplePkColumnMatch
    ): List<GenAssociationMatchView> {
        val result = mutableListOf<GenAssociationMatchView>()

        columns.forEach { source ->
            columns.forEach { target ->
                val type = match(source, target)
                if (source.id != target.id && type != null) {
                    result += newGenAssociationMatchView(type, source, target)
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

    fun <T : View<GenAssociation>> queryByTable(
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

    fun <T : View<GenAssociation>> queryByColumn(
        sourceColumnIds: List<Long>,
        targetColumnIds: List<Long>,
        selectType: SelectType,
        viewClass: KClass<T>
    ): List<T> {
        return sqlClient.createQuery(GenAssociation::class) {
            if (sourceColumnIds.isNotEmpty() || targetColumnIds.isNotEmpty()) {
                when (selectType) {
                    SelectType.AND -> {
                        where(
                            table.sourceColumn.id valueIn sourceColumnIds,
                            table.targetColumn.id valueIn targetColumnIds
                        )
                    }

                    SelectType.OR -> {
                        where(
                            or(
                                table.sourceColumn.id valueIn sourceColumnIds,
                                table.targetColumn.id valueIn targetColumnIds
                            )
                        )
                    }
                }
            }
            select(table.fetch(viewClass))
        }.execute()
    }
}
