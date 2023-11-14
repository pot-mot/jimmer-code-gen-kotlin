package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.convert.toGenEntity
import top.potmot.core.generate.generateCode
import top.potmot.core.generate.toZipByteArray
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.tableId

@RestController
@RequestMapping("/generate")
class GenerateService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/convert")
    fun convert(@RequestBody tableIds: List<Long>): List<Long> {
        val result = mutableListOf<Long>()

        transactionTemplate.execute {
            val tables = sqlClient.createQuery(GenTable::class) {
                where(
                    table.id valueIn tableIds
                )
                select(table.fetch(GenTableAssociationsView::class))
            }.execute()

            sqlClient.createDelete(GenEntity::class) {
                where(table.tableId valueIn tables.map { table -> table.id })
            }.execute()

            val typeMappings = sqlClient.createQuery(GenTypeMapping::class) {
                select(table)
            }.execute()

            tables.map { it.toGenEntity(typeMappings) }.forEach {
                result += sqlClient.insert(it).modifiedEntity.id
            }
        }

        return result
    }

    @GetMapping("/preview")
    fun preview(
        @RequestParam entityIds: List<Long>,
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

    @PostMapping("/preview/table")
    fun previewByTable(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): Map<String, String> {
        val entityIds = convert(tableIds)
        return preview(entityIds.toList(), language)
    }

    @GetMapping("/preview/model")
    fun previewByModel(
        @RequestParam modelId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): Map<String, String> {
        val tableIds = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.id)
        }.execute()
        return previewByTable(tableIds, language)
    }

    @PostMapping("/entity")
    fun generate(
        @RequestBody entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        return preview(entityIds, language)
            .toZipByteArray()
    }

    @PostMapping("/model")
    fun generateByModel(
        @RequestBody modelId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        return previewByModel(modelId, language)
            .toZipByteArray()
    }

    @PostMapping("/package")
    fun generateByPackage(
        @RequestBody packageId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        TODO()
    }

    @PostMapping("/table")
    fun generateByTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray {
        return previewByTable(tableIds, language)
            .toZipByteArray()
    }
}
