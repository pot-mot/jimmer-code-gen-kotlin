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
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.core.entity.generate.generateEntityCode
import top.potmot.core.genPackage.generate.TreeItem
import top.potmot.core.genPackage.generate.generatePackage
import top.potmot.core.genPackage.generate.packageGenerateTreeFetcher
import top.potmot.core.genPackage.generate.toZipByteArray
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenPackage
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.orderKey

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

            val typeMappings = sqlClient.createQuery(GenTypeMapping::class) {
                orderBy(table.orderKey)
                select(table.fetch(GenTypeMappingView::class))
            }.execute()

            tables.map { it.toGenEntity(typeMappings) }.forEach {
                result += sqlClient.save(it).modifiedEntity.id
            }
        }

        return result
    }

    @GetMapping("/preview")
    fun preview(
        @RequestParam entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): List<Pair<String, String>> =
        sqlClient.createQuery(GenEntity::class) {
            where(table.id valueIn entityIds)
            select(table.fetch(GenEntityPropertiesView::class))
        }.execute().flatMap {
            generateEntityCode(it, language)
        }

    @PostMapping("/preview/table")
    fun previewByTable(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): List<Pair<String, String>> {
        val entityIds = convert(tableIds)
        return preview(entityIds.toList(), language)
    }

    @GetMapping("/preview/model")
    fun previewByModel(
        @RequestParam modelId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): List<Pair<String, String>> {
        val tableIds = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.id)
        }.execute()
        return previewByTable(tableIds, language)
    }

//    @GetMapping("/preview/package")
    fun previewByPackage(
        @RequestParam packageIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): List<TreeItem<String>> =
        sqlClient.createQuery(GenPackage::class) {
            where(table.id valueIn packageIds)
            select(table.fetch(packageGenerateTreeFetcher))
        }.execute().map {
            generatePackage(it, language)
        }

    @PostMapping("/entity")
    fun generate(
        @RequestBody entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        preview(entityIds, language)
            .toZipByteArray()

    @PostMapping("/table")
    fun generateByTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewByTable(tableIds, language)
            .toZipByteArray()

    @PostMapping("/model")
    fun generateByModel(
        @RequestBody modelId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewByModel(modelId, language)
            .toZipByteArray()

    @PostMapping("/package")
    fun generateByPackage(
        @RequestBody packageIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewByPackage(packageIds = packageIds, language)
            .toZipByteArray()
}
