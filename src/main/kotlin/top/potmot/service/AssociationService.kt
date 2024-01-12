package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenAssociation
import top.potmot.model.base.Query
import top.potmot.model.dto.GenAssociationInput
import top.potmot.model.dto.GenAssociationView
import top.potmot.model.query.AssociationColumnQuery
import top.potmot.model.query.AssociationQuery
import top.potmot.model.query.AssociationTableQuery
import kotlin.reflect.KClass

@RestController
@RequestMapping("/association")
class AssociationService(
    @Autowired val sqlClient: KSqlClient,
) {
    @PostMapping("/query")
    fun query(@RequestBody query: AssociationQuery): List<GenAssociationView> {
        return executeQuery(query, GenAssociationView::class)
    }

    @PostMapping("/queryByTable")
    fun queryByTable(@RequestBody query: AssociationTableQuery): List<GenAssociationView> {
        return executeQuery(query, GenAssociationView::class)
    }

    @PostMapping("/queryByColumn")
    fun queryByColumn(@RequestBody query: AssociationColumnQuery): List<GenAssociationView> {
        return executeQuery(query, GenAssociationView::class)
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

    fun <T : View<GenAssociation>> executeQuery(query: Query<GenAssociation>, viewClass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenAssociation::class) {
            where(*query.toPredicates(table))
            select(table.fetch(viewClass))
        }.execute()
    }
}
