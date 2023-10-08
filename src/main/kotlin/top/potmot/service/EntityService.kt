package top.potmot.service

import org.babyfish.jimmer.View
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
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GenConfig
import top.potmot.core.convert.columnToProperties
import top.potmot.core.convert.tableToEntity
import top.potmot.core.generate.stringify
import top.potmot.core.generate.stringifyJava
import top.potmot.enum.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenTypeMapping
import top.potmot.model.comment
import top.potmot.model.copy
import top.potmot.model.createdTime
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationView
import top.potmot.model.id
import top.potmot.model.name
import top.potmot.model.query.EntityQuery
import top.potmot.model.query.TableQuery
import top.potmot.model.tableId
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.reflect.KClass

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val tableService: TableService,
) {
    @PostMapping("/mapping")
    @Transactional
    fun mapping(@RequestBody tableIds: List<Long>): List<Long> {
        val result = mutableListOf<Long>()

        tableService.query(TableQuery(ids = tableIds), GenTableAssociationView::class).forEach {
            sqlClient.createDelete(GenEntity::class) {
                where(table.tableId valueIn tableIds)
            }.execute()
            result += sqlClient.insert(
                mappingEntity(it)
            ).modifiedEntity.id
        }

        return result
    }

    @GetMapping("/preview/{entityIds}")
    fun preview(
        @PathVariable entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()

        query(EntityQuery(ids = entityIds)).forEach { entity ->
            when (language ?: GenConfig.language) {
                GenLanguage.KOTLIN -> map["${entity.name}.kt"] = entity.stringify()
                GenLanguage.JAVA -> map["${entity.name}.java"] = entity.stringifyJava()
            }
        }

        return map
    }

    @PostMapping("/generate")
    @Transactional
    fun generate(
        @RequestBody entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        val zipFileStream = ByteArrayOutputStream()

        val zip = ZipOutputStream(zipFileStream)

        query(EntityQuery(ids = entityIds)).forEach { entity ->
            when (language ?: GenConfig.language) {
                GenLanguage.KOTLIN -> {
                    zip.putNextEntry(ZipEntry("${entity.name}.kt"))
                    zip.write(entity.stringify().toByteArray())
                    zip.closeEntry()
                }

                GenLanguage.JAVA -> {
                    zip.putNextEntry(ZipEntry("${entity.name}.java"))
                    zip.write(entity.stringifyJava().toByteArray())
                    zip.closeEntry()
                }
            }

        }

        return zipFileStream.toByteArray()
    }

    @PutMapping("/config")
    @Transactional
    fun config(@RequestBody entity: GenEntityConfigInput): Int {
        return sqlClient.save(entity).totalAffectedRowCount
    }

    @GetMapping("/query")
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
                            table.name ilike it,
                            table.comment ilike it,
                        )
                    )
                }
            }

            query.names?.takeIf { it.isNotEmpty() }?.let {
                query.names.forEach {
                    where(table.name eq it)
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

    fun mappingEntity(
        table: GenTableAssociationView
    ): GenEntity {
        val baseEntity = tableToEntity(table)

        val typeMappings = sqlClient.createQuery(GenTypeMapping::class) {
            select(this.table)
        }.execute()

        val properties = table.columns.flatMap { column ->
            columnToProperties(
                column,
                typeMappings
            )
        }

        return baseEntity.copy {
            this.properties = properties
        }
    }
}
