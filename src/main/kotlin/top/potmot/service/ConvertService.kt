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
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
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
    fun getTableByModel(tableIds: List<Long>): List<GenTableAssociationsView> =
        sqlClient.createQuery(GenTable::class) {
            where(
                table.id valueIn tableIds
            )
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

    fun getTypeMappings(): List<GenTypeMappingView> =
        sqlClient.createQuery(GenTypeMapping::class) {
            orderBy(table.orderKey)
            select(table.fetch(GenTypeMappingView::class))
        }.execute()


    @PostMapping
    @Throws(ConvertEntityException::class, ColumnTypeException::class)
    fun convert(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) dataSourceType: DataSourceType?,
        @RequestParam(required = false) language: GenLanguage?,
        @RequestParam(required = false) packagePath: String?,
    ): List<Long> {
        val result = mutableListOf<Long>()

        transactionTemplate.execute {
            val tables = getTableByModel(tableIds)

            val typeMappings = getTypeMappings()

            tables
                .map {
                    it.toGenEntity(
                        modelId,
                        dataSourceType ?: GenConfig.dataSourceType,
                        language ?: GenConfig.language,
                        packagePath ?: GenConfig.defaultPackagePath, typeMappings,
                    )
                }
                .forEach {
                    result += sqlClient.save(it).modifiedEntity.id
                }
        }

        return result
    }
}
