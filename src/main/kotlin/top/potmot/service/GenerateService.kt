package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.utils.zip.toZipByteArray

@RestController
@RequestMapping("/generate")
class GenerateService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val previewService: PreviewService
) {
    @PostMapping("/entity")
    fun generateEntity(
        @RequestBody entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?
    ): ByteArray =
        previewService.previewEntity(entityIds, language, true)
            .toZipByteArray()

    @PostMapping("/entity/byTable")
    fun generateEntityByTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) dataSourceType: DataSourceType?,
        @RequestParam(required = false) language: GenLanguage?,
        @RequestParam(required = false) packagePath: String?,
    ): ByteArray =
        previewService.previewEntityByTable(tableIds, modelId, dataSourceType, language, packagePath, true)
            .toZipByteArray()

    @PostMapping("/model/sql")
    fun generateModelSql(
        @RequestBody id: Long,
    ): ByteArray =
        previewService.previewModelSql(id)
            .toZipByteArray()

    @PostMapping("/model/entity")
    fun generateModelEntity(
        @RequestBody id: Long,
    ): ByteArray =
        previewService.previewModelEntity(id, true)
            .toZipByteArray()


    @PostMapping("/model")
    fun generateModel(
        @RequestBody id: Long,
    ): ByteArray =
        previewService.previewModel(id)
            .toZipByteArray()
}
