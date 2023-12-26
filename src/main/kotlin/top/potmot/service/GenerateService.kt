package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.entity.generate.generateEntityCode
import top.potmot.utils.zip.toZipByteArray
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.packagePath
import top.potmot.model.syncConvertEntity
import top.potmot.model.tableId

@RestController
@RequestMapping("/generate")
class GenerateService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val convertService: ConvertService
) {
    fun getTableIdsByModelId(modelId: Long): List<Long> =
        sqlClient.createQuery(GenTable::class) {
            where(
                table.modelId eq modelId
            )
            select(table.id)
        }.execute()

    fun getEntityIdsByModelId(modelId: Long): List<Long> =
        sqlClient.createQuery(GenEntity::class) {
            where(
                table.tableId valueIn subQuery(GenTable::class){
                    where(table.modelId eq modelId)
                    select(table.id)
                }
            )
            select(table.id)
        }.execute()

    @GetMapping("/preview")
    fun previewByEntity(
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
        @RequestParam(required = false) packagePath: String?,
        @RequestParam(required = false) language: GenLanguage?
    ): List<Pair<String, String>> {
        val entityIds = convertService.convert(tableIds, packagePath, language)
        return previewByEntity(entityIds.toList(), language)
    }

    val previewModelFetcher = newFetcher(GenModel::class).by {
        syncConvertEntity()
        packagePath()
    }

    @GetMapping("/preview/model")
    fun previewByModel(
        @RequestParam modelId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): List<Pair<String, String>> {
        val model = sqlClient.createQuery(GenModel::class) {
            where(table.id eq modelId)
            select(table.fetch(previewModelFetcher))
        }.execute().firstOrNull()
            ?: return emptyList()

        return if (model.syncConvertEntity) {
            val tableIds = getTableIdsByModelId(modelId)
            previewByTable(tableIds, model.packagePath , language)
        } else {
            val entityIds = getEntityIdsByModelId(modelId)
            previewByEntity(entityIds, language)
        }
    }

    @PostMapping("/entity")
    fun generate(
        @RequestBody entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewByEntity(entityIds, language)
            .toZipByteArray()

    @PostMapping("/table")
    fun generateByTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) packagePath: String?,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewByTable(tableIds, packagePath, language)
            .toZipByteArray()

    @PostMapping("/model")
    fun generateByModel(
        @RequestBody modelId: Long,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewByModel(modelId, language)
            .toZipByteArray()
}
