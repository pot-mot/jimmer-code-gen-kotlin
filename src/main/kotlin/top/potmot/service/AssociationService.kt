package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.GenAssociation
import top.potmot.entity.dto.GenAssociationView
import top.potmot.entity.query.AssociationColumnQuery
import top.potmot.entity.query.AssociationQuery
import top.potmot.entity.query.AssociationTableQuery
import top.potmot.entity.query.Query
import top.potmot.entity.query.where
import kotlin.reflect.KClass

@RestController
@RequestMapping("/association")
class AssociationService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    @PostMapping("/query")
    fun query(@RequestBody query: AssociationQuery): List<GenAssociationView> =
        sqlClient.queryAssociation(query, GenAssociationView::class)

    @PostMapping("/queryByTable")
    fun queryByTable(@RequestBody query: AssociationTableQuery): List<GenAssociationView> =
        sqlClient.queryAssociation(query, GenAssociationView::class)

    @PostMapping("/queryByColumn")
    fun queryByColumn(@RequestBody query: AssociationColumnQuery): List<GenAssociationView> =
        sqlClient.queryAssociation(query, GenAssociationView::class)

    private fun <T : View<GenAssociation>> KSqlClient.queryAssociation(query: Query<GenAssociation>, viewClass: KClass<T>): List<T> =
        executeQuery(GenAssociation::class) {
            where(query)
            select(table.fetch(viewClass))
        }
}
