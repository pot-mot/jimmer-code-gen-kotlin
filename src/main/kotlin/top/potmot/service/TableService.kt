package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenTable
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.dto.GenTableIdView
import top.potmot.query.Query
import top.potmot.query.TableQuery
import kotlin.reflect.KClass

@RestController
@RequestMapping("/table")
class TableService(
    @Autowired val sqlClient: KSqlClient
) {
    @PostMapping("/query/id")
    fun queryIdView(@RequestBody query: TableQuery): List<GenTableIdView> {
        return executeQuery(query, GenTableIdView::class)
    }

    @PostMapping("/query/common")
    fun queryCommonView(@RequestBody query: TableQuery): List<GenTableCommonView> {
        return executeQuery(query, GenTableCommonView::class)
    }

    @PostMapping("/query/columns")
    fun queryColumnsView(@RequestBody query: TableQuery): List<GenTableColumnsView> {
        return executeQuery(query, GenTableColumnsView::class)
    }

    @PostMapping("/query")
    fun queryAssociationsView(@RequestBody query: TableQuery): List<GenTableAssociationsView> {
        return executeQuery(query, GenTableAssociationsView::class)
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenTable::class, ids).totalAffectedRowCount
    }

    fun <T : View<GenTable>> executeQuery(query: Query<GenTable>, viewCLass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenTable::class) {
            where(*query.toPredicates(table))
            select(table.fetch(viewCLass))
        }.execute()
    }
}
