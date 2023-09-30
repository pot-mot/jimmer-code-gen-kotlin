package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import top.potmot.config.GenConfig
import top.potmot.core.convert.columnToProperties
import top.potmot.model.*
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationView
import top.potmot.model.query.EntityQuery
import top.potmot.model.query.TableQuery
import top.potmot.core.convert.tableToEntity
import top.potmot.core.generate.stringify
import top.potmot.core.generate.stringifyJava
import top.potmot.enum.GenLanguage
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
            result += sqlClient.save(
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
