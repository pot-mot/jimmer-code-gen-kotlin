package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenTableColumnsView
import top.potmot.entity.dto.GenTableCommonView
import top.potmot.entity.query.Query
import top.potmot.entity.query.TableQuery
import top.potmot.entity.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/table")
class TableService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    @PostMapping("/query/common")
    fun queryCommonView(@RequestBody query: TableQuery): List<GenTableCommonView> =
        sqlClient.queryTable(query, GenTableCommonView::class)

    @PostMapping("/query/columns")
    fun queryColumnsView(@RequestBody query: TableQuery): List<GenTableColumnsView> =
        sqlClient.queryTable(query, GenTableColumnsView::class)

    private fun <T : View<GenTable>> KSqlClient.queryTable(query: Query<GenTable>, viewCLass: KClass<T>): List<T> =
        executeQuery(GenTable::class) {
            where(query)
            select(table.fetch(viewCLass))
        }
}
