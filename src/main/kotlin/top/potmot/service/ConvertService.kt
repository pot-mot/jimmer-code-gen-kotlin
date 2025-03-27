package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.config.useContext
import top.potmot.core.entity.convert.convertTablesToEntities
import top.potmot.entity.GenAssociation
import top.potmot.entity.GenEntity
import top.potmot.entity.GenModel
import top.potmot.entity.GenTable
import top.potmot.entity.GenTypeMapping
import top.potmot.entity.dto.GenAssociationConvertView
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenEntityExistView
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.entity.dto.IdName
import top.potmot.core.config.merge
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.orderKey
import top.potmot.entity.tableId
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException
import top.potmot.utils.transaction.executeNotNull

@RestController
@RequestMapping("/convert")
class ConvertService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate,
) {
    private val superTableAssociations = sqlClient.getAssociations(GenEntity::superEntities)

    @PostMapping("/model")
    @Throws(ConvertException::class, ConvertException::class, ColumnTypeException::class)
    fun convertModel(
        @RequestParam id: Long,
        @RequestBody(required = false) properties: GenConfigProperties? = null,
    ): List<Long> = transactionTemplate.executeNotNull {
        val result = mutableListOf<Long>()

        val mergedProperties = sqlClient.getModelProperties(id)?.merge(properties)
            ?: throw ConvertException.modelNotFound(modelId = id)

        useContext(mergedProperties) {
            val tables = sqlClient.listTable(id)
            val associations = sqlClient.listAssociation(id)
            val typeMappings = sqlClient.listTypeMapping()

            val tableIdMap = tables.associateBy { it.id }
            val columnIdMap = tables.flatMap { it.columns }.associateBy { it.id }

            val tableIdEntityMap = sqlClient.listTableIdEntityTuple(tableIdMap.keys)
                .associate { it._1 to it._2 }

            val (
                insertEntities,
                updateEntities,
            ) = convertTablesToEntities(
                modelId = id,
                tableIdMap = tableIdMap,
                columnIdMap = columnIdMap,
                associations = associations,
                tableIdEntityMap = tableIdEntityMap,
                typeMappings = typeMappings
            )

            val tableEntityIdPairs = mutableListOf<Pair<GenTableConvertView, Long>>()

            listOf(
                sqlClient.insertInputs(insertEntities).items,
                sqlClient.updateInputs(updateEntities, AssociatedSaveMode.REPLACE).items
            ).flatten().forEach {
                val entity = it.modifiedEntity
                val table = tableIdMap[entity.tableId]
                    ?: throw ConvertException.entityMatchTableNotFound(
                        "entity [${entity}]",
                        entity = IdName(entity.id, entity.name),
                        tableId = entity.tableId,
                    )
                tableEntityIdPairs += table to entity.id
                result += entity.id
            }

            tableEntityIdPairs.forEach { (table, entityId) ->
                val superTableIds = table.superTableIds

                val superEntityIds = tableEntityIdPairs
                    .filter { it.first.id in superTableIds }
                    .map { it.second }

                if (superTableIds.size != superEntityIds.size)
                    throw ConvertException.superTableSuperEntityNotMatch(
                        "SuperTableIds $superTableIds can't match superEntityIds $superEntityIds",
                        table = IdName(table.id, table.name),
                        superTableIds = superTableIds,
                        superEntityIds = superEntityIds,
                    )

                superTableAssociations
                    .saveAll(listOf(entityId), superEntityIds)
            }
        }

        result
    }

    private fun KSqlClient.getModelProperties(id: Long) =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(GenConfigProperties::class))
        }.fetchOneOrNull()

    private fun KSqlClient.listTable(modelId: Long): List<GenTableConvertView> =
        executeQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenTableConvertView::class))
        }

    private fun KSqlClient.listAssociation(modelId: Long): List<GenAssociationConvertView> =
        executeQuery(GenAssociation::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenAssociationConvertView::class))
        }

    private fun KSqlClient.listTableIdEntityTuple(tableIds: Collection<Long>) =
        if (tableIds.isEmpty()) emptyList()
        else executeQuery(GenEntity::class) {
            where(
                table.tableId valueIn tableIds
            )
            select(table.tableId, table.fetch(GenEntityExistView::class))
        }

    private fun KSqlClient.listTypeMapping(): List<GenTypeMappingView> =
        executeQuery(GenTypeMapping::class) {
            orderBy(table.orderKey)
            select(table.fetch(GenTypeMappingView::class))
        }
}
