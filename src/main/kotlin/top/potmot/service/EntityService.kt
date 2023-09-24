package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import top.potmot.model.*
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableConvertView
import top.potmot.model.query.EntityQuery
import top.potmot.model.query.TableQuery
import top.potmot.util.convert.tableToEntity
import kotlin.reflect.KClass

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val tableService: TableService,
) {
    @PostMapping("/mapping")
    @Transactional
    fun mapping(tableIds: List<Long>): List<Long> {
        val result = mutableListOf<Long>()

        tableService.query(TableQuery(ids = tableIds), GenTableConvertView::class).forEach {
            result += sqlClient.save(tableToEntity(it.toEntity())).modifiedEntity.id
        }

        return result
    }

    @PutMapping("/config")
    @Transactional
    fun config(entity: GenEntityConfigInput): Int {
        return sqlClient.save(entity).totalAffectedRowCount
    }

    @PostMapping("/query")
    fun query(query: EntityQuery): List<GenEntityPropertiesView> {
        return query(query, GenEntityPropertiesView::class)
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenEntity::class, ids).totalAffectedRowCount
    }

    fun <T : View<GenEntity>> query(query: EntityQuery, viewClass: KClass<T>): List<T> {
        return sqlClient.createQuery(GenEntity::class) {
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(
                        or(
                            table.className ilike it,
                            table.classComment ilike it,
                            table.packageName ilike it
                        )
                    )
                }
            }

            query.names?.takeIf { it.isNotEmpty() }?.let {
                query.names.forEach {
                    where(table.className eq it)
                }
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
}
