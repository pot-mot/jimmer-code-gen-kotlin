package top.potmot.service

import org.babyfish.jimmer.kt.merge
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.context.useContext
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.entity.GenEntity
import top.potmot.entity.GenModel
import top.potmot.entity.GenTable
import top.potmot.entity.GenTypeMapping
import top.potmot.entity.by
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.orderKey
import top.potmot.entity.tableId
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException

@RestController
@RequestMapping("/convert")
class ConvertService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/table")
    @Throws(ConvertEntityException::class, ColumnTypeException::class)
    fun convertTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Long> {
        val result = mutableListOf<Long>()

        transactionTemplate.execute {
            useContext(properties) {
                val tables = sqlClient.getTableViews(tableIds)

                val typeMappings = sqlClient.getTypeMappings()

                val tableEntityPairs = mutableListOf<Pair<GenTableConvertView, GenEntity>>()

                tables.forEach { table ->
                    val entity = table.toGenEntity(modelId, typeMappings)
                    val savedEntity = sqlClient.save(entity).modifiedEntity

                    tableEntityPairs += table to savedEntity

                    result += savedEntity.id
                }

                tableEntityPairs.forEach { (table, entity) ->
                    table.superTables?.map { it.id }?.let { superTableIds ->
                        val superEntityIds = sqlClient.getEntityIds(superTableIds)

                        if (superTableIds.size != superEntityIds.size)
                            throw ConvertEntityException.superTable("SuperTableIds [${superTableIds}] can't match superEntityIds [${superEntityIds}]")

                        sqlClient.getAssociations(GenEntity::superEntities)
                            .saveAll(listOf(entity.id), superEntityIds)
                    }
                }
            }
        }

        return result
    }

    @PostMapping("/model")
    @Throws(ConvertEntityException::class, ColumnTypeException::class)
    fun convertModel(
        @RequestParam id: Long,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Long> {
        val currentProperties = sqlClient.getModelProperties(id)
        val tableIds = sqlClient.getModelTableIds(id)

        val mergedProperties = GenConfigProperties(
            merge(currentProperties?.toEntity(), properties?.toEntity())
        )

        return convertTable(tableIds, id, mergedProperties)
    }

    private fun KSqlClient.getModelProperties(id: Long) =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(GenConfigProperties::class))
        }.fetchOneOrNull()

    private fun KSqlClient.getModelTableIds(modelId: Long) =
        createQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.id)
        }.execute()

    private fun KSqlClient.getTableViews(ids: List<Long>): List<GenTableConvertView> =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenTable::class) {
            where(table.id valueIn ids)
            select(table.fetch(oneDepthSuperTableFetcher))
        }.execute().map {
            GenTableConvertView(it)
        }

    private val oneDepthSuperTableFetcher =
        newFetcher(GenTable::class).by(GenTableConvertView.METADATA.fetcher) {
            `superTables*` { depth(1) }
        }

    private fun KSqlClient.getEntityIds(tableIds: List<Long>): List<Long> =
        if (tableIds.isEmpty()) emptyList()
        else createQuery(GenEntity::class) {
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
