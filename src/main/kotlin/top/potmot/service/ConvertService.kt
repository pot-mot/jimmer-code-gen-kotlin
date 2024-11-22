package top.potmot.service

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
import top.potmot.context.useContext
import top.potmot.core.entity.convert.mergeExistAndConvertEntity
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.entity.GenEntity
import top.potmot.entity.GenModel
import top.potmot.entity.GenTable
import top.potmot.entity.GenTypeMapping
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.entity.dto.IdName
import top.potmot.entity.extension.merge
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.orderKey
import top.potmot.entity.tableId
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException

@RestController
@RequestMapping("/convert")
class ConvertService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/table")
    @Throws(ConvertException::class, ColumnTypeException::class)
    fun convertTable(
        @RequestBody tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) properties: GenConfigProperties? = null,
    ): List<Long> {
        val result = mutableListOf<Long>()

        transactionTemplate.execute {
            useContext(properties) {
                val tables = sqlClient.listTable(tableIds)
                val tableIdMap = tables.associateBy { it.id }
                val typeMappings = sqlClient.listTypeMapping()
                val tableEntityIdMap = sqlClient.listTableIdEntityTuple(tableIds).associate { it._1 to it._2 }

                val tableEntityIdPairs = mutableListOf<Pair<GenTableConvertView, Long>>()
                val insertEntities = mutableListOf<GenEntityInput>()
                val updateEntities = mutableListOf<GenEntity>()

                tables.forEach { table ->
                    val existEntity = tableEntityIdMap[table.id]
                    val convertEntity = table.toGenEntity(modelId, typeMappings)

                    if (existEntity == null) {
                        insertEntities += convertEntity
                    } else {
                        updateEntities += mergeExistAndConvertEntity(existEntity, convertEntity)
                    }
                }

                sqlClient.insertInputs(insertEntities).items.forEach {
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
                sqlClient.saveEntities(updateEntities).items.forEach {
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

                    val superEntityIds = sqlClient.listEntityId(superTableIds)

                    if (superTableIds.size != superEntityIds.size)
                        throw ConvertException.superTableSuperEntityNotMatch(
                            "SuperTableIds $superTableIds can't match superEntityIds $superEntityIds",
                            table = IdName(table.id, table.name),
                            superTableIds = superTableIds,
                            superEntityIds = superEntityIds,
                        )

                    sqlClient.getAssociations(GenEntity::superEntities)
                        .saveAll(listOf(entityId), superEntityIds)
                }
            }
        }

        return result
    }

    @PostMapping("/model")
    @Throws(ConvertException::class, ConvertException::class, ColumnTypeException::class)
    fun convertModel(
        @RequestParam id: Long,
        @RequestParam(required = false) properties: GenConfigProperties? = null,
    ): List<Long> = convertTable(
        sqlClient.listTableId(id),
        id,
        sqlClient.getModelProperties(id)?.merge(properties)
            ?: throw ConvertException.modelNotFound(modelId = id)
    )

    private fun KSqlClient.getModelProperties(id: Long) =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(GenConfigProperties::class))
        }.fetchOneOrNull()

    private fun KSqlClient.listTableId(modelId: Long) =
        createQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.id)
        }.execute()

    private fun KSqlClient.listTable(ids: List<Long>): List<GenTableConvertView> =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenTable::class) {
            where(table.id valueIn ids)
            select(table.fetch(GenTableConvertView::class))
        }.execute()

    private fun KSqlClient.listTableIdEntityTuple(tableIds: List<Long>) =
        if (tableIds.isEmpty()) emptyList()
        else createQuery(GenEntity::class) {
            where(
                table.tableId valueIn tableIds
            )
            select(table.tableId, table.fetch(GenEntityDetailView::class))
        }.execute()

    private fun KSqlClient.listEntityId(tableIds: List<Long>): List<Long> =
        if (tableIds.isEmpty()) emptyList()
        else createQuery(GenEntity::class) {
            where(
                table.tableId valueIn tableIds
            )
            select(table.id)
        }.execute()

    private fun KSqlClient.listTypeMapping(): List<GenTypeMappingView> =
        createQuery(GenTypeMapping::class) {
            orderBy(table.orderKey)
            select(table.fetch(GenTypeMappingView::class))
        }.execute()
}
