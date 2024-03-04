package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.context.useContext
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView
import top.potmot.model.id
import top.potmot.model.orderKey

@RestController
@RequestMapping("/convert")
class ConvertService(
    @Autowired val sqlClient: KSqlClient
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
    @Transactional
    @Throws(ConvertEntityException::class, ColumnTypeException::class)
    fun convert(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Long> {
        val result = mutableListOf<Long>()

        useContext(properties) {
            val tables = getTableByModel(tableIds)

            val typeMappings = getTypeMappings()

            tables
                .map {
                    it.toGenEntity(modelId, typeMappings)
                }
                .forEach {
                    result += sqlClient.save(it).modifiedEntity.id
                }
        }

        return result
    }
}
