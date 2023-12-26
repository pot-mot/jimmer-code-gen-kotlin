package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GenConfig
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView
import top.potmot.model.id
import top.potmot.model.orderKey

@RestController
@RequestMapping("/convert")
class ConvertService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    @PostMapping
    fun convert(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) packagePath: String?,
        @RequestParam(required = false) language: GenLanguage?,
    ): List<Long> {
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

            tables
                .map { it.toGenEntity(packagePath ?: GenConfig.defaultPackagePath, typeMappings, language ?: GenConfig.language) }
                .forEach { result += sqlClient.save(it).modifiedEntity.id }
        }

        return result
    }
}
