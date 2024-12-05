package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.GenColumn
import top.potmot.entity.dto.GenColumnView
import top.potmot.entity.query.ColumnQuery
import top.potmot.entity.query.where

@RestController
@RequestMapping("/column")
class ColumnService(
    @Autowired
    private val sqlClient: KSqlClient
) {
    @PostMapping("/query")
    fun query(@RequestBody query: ColumnQuery): List<GenColumnView> =
        sqlClient.executeQuery(GenColumn::class) {
            where(query)
            select(table.fetch(GenColumnView::class))
        }
}
