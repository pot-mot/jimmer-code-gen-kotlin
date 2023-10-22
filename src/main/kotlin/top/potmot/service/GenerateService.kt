package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.convert.columnToProperties
import top.potmot.core.convert.tableToEntity
import top.potmot.core.generate.generateCode
import top.potmot.core.generate.toZipByteArray
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.copy
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationView
import top.potmot.model.entityId
import top.potmot.model.id
import top.potmot.model.query.EntityQuery
import top.potmot.model.tableId

@RestController
@RequestMapping("/generate")
class GenerateService(
    @Autowired val sqlClient: KSqlClient,
) {
    @PostMapping("/convert")
    @Transactional
    fun convert(@RequestBody tableIds: List<Long>): List<Long> {
        val result = mutableListOf<Long>()

        sqlClient.createQuery(GenTable::class) {
            where(
                table.id valueIn tableIds
            )
            select(table.fetch(GenTableAssociationView::class))
        }.forEach {
            sqlClient.createDelete(GenEntity::class) {
                where(table.tableId valueIn tableIds)
            }.execute()
            result += sqlClient.insert(
                convertTableToEntity(it)
            ).modifiedEntity.id
        }

        return result
    }

    @GetMapping("/preview/{entityIds}")
    fun preview(
        @PathVariable entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): Map<String, String> {
        val result = mutableMapOf<String, String>()

        sqlClient.createQuery(GenEntity::class) {
            where(table.id valueIn entityIds)
            select(table.fetch(GenEntityPropertiesView::class))
        }.forEach {
            val codeMap =
                if (language != null) generateCode(it, language) else generateCode(it)
            result += codeMap
        }

        return result
    }

    @PostMapping("/entity")
    @Transactional
    fun generate(
        @RequestBody entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        return preview(entityIds, language)
            .toZipByteArray()
    }

    @PostMapping("/table")
    @Transactional
    fun generateByTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        sqlClient.createQuery(GenTable::class) {
            where(table.id valueIn tableIds)
            where(table.entityId.isNull())
            select(table.id)
        }.execute().apply {
            convert(this)
        }

        sqlClient.createQuery(GenEntity::class) {
            where(table.tableId valueIn tableIds)
            select(table.id)
        }.execute().apply {
            return generate(this, language)
        }
    }

    fun convertTableToEntity(
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
