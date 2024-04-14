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
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView
import top.potmot.model.extension.GenTableAssociationsOneDepthSuperTableFetcher
import top.potmot.model.id
import top.potmot.model.orderKey
import top.potmot.model.tableId

@RestController
@RequestMapping("/convert")
class ConvertService(
    @Autowired val sqlClient: KSqlClient
) {
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
            val tables = sqlClient.getTableViews(tableIds)

            val typeMappings = sqlClient.getTypeMappings()

            val tableEntityPairs = mutableListOf<Pair<GenTableAssociationsView, GenEntity>>()

            tables.forEach { table ->
                val entity = table.toGenEntity(modelId, typeMappings)
                val savedEntity = sqlClient.save(entity).modifiedEntity

                tableEntityPairs += table to savedEntity

                result += savedEntity.id
            }

            tableEntityPairs.forEach { (table, entity) ->
                // FIXME 基于关联属性装填超级类 id 并保存，但是发生 N+1 查询
                table.superTables?.map { it.id }?.let { superTableIds ->
                    val superEntityIds = sqlClient.getEntityIds(superTableIds)

                    if (superTableIds.size != superEntityIds.size) {
                        throw ConvertEntityException.superTable("SuperTableIds [${superTableIds}] can't match superEntityIds [${superEntityIds}]")
                    }

                    sqlClient.getAssociations(GenEntity::superEntities)
                        .saveAll(listOf(entity.id), superEntityIds)
                }
            }
        }

        return result
    }

    private fun KSqlClient.getTableViews(tableIds: List<Long>): List<GenTableAssociationsView> =
        if (tableIds.isEmpty()) emptyList()
        else createQuery(GenTable::class) {
            where(
                table.id valueIn tableIds
            )
            select(table.fetch(GenTableAssociationsOneDepthSuperTableFetcher))
        }.execute().map {
            GenTableAssociationsView(it)
        }

    private fun KSqlClient.getEntityIds(tableIds: List<Long>): List<Long> =
        createQuery(GenEntity::class) {
            where(
                table.tableId valueIn tableIds
            )
            select(table.id)
        }.execute()

    private fun KSqlClient.getTypeMappings(): List<GenTypeMappingView> =
        createQuery(GenTypeMapping::class) {
            orderBy(table.orderKey)
            select(table.fetch(GenTypeMappingView::class))
        }.execute()
}
