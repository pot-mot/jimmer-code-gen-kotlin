package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.dto.GenEntityCommonView
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.query.EntityQuery
import top.potmot.query.Query
import kotlin.reflect.KClass

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired val sqlClient: KSqlClient,
) {
    /**
     * 列出所有数据源
     */
    @GetMapping
    fun list(): List<GenEntityCommonView> {
        return sqlClient.createQuery(GenEntity::class) {
            select(table.fetch(GenEntityCommonView::class))
        }.execute()
    }

    /**
     * 获取单个数据源
     */
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEntityPropertiesView? {
        return sqlClient.findById(GenEntityPropertiesView::class, id)
    }

    @GetMapping("/language")
    fun listLanguage(): List<GenLanguage> {
        return GenLanguage.entries
    }

    @PostMapping("/query")
    fun query(@RequestBody query: EntityQuery): List<GenEntityPropertiesView> {
        return executeQuery(query, GenEntityPropertiesView::class)
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenEntity::class, ids).totalAffectedRowCount
    }

    fun <T : View<GenEntity>> executeQuery(query: Query<GenEntity>, viewClass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenEntity::class) {
            where(*query.toPredicates(table))
            select(table.fetch(viewClass))
        }.execute()
    }
}
