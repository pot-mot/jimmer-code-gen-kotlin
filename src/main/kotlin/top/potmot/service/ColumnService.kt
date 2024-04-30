package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.GenColumn
import top.potmot.entity.dto.GenColumnView
import top.potmot.entity.query.ColumnQuery
import top.potmot.entity.query.Query
import top.potmot.entity.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/column")
class ColumnService(
    @Autowired val sqlClient: KSqlClient
) {
    @PostMapping("/query")
    fun query(@RequestBody query: ColumnQuery): List<GenColumnView> {
        return executeQuery(query, GenColumnView::class)
    }

    fun <T : View<GenColumn>> executeQuery(query: Query<GenColumn>, viewCLass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenColumn::class) {
            where(query)
            select(table.fetch(viewCLass))
        }.execute()
    }
}
