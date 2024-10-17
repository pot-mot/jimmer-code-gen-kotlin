package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.model.load.ModelInputEntities
import top.potmot.core.model.load.getGraphEntities
import top.potmot.core.model.load.parseGraphData
import top.potmot.core.model.load.toInput
import top.potmot.core.model.load.toInputs
import top.potmot.entity.GenModel
import top.potmot.entity.GenTable
import top.potmot.entity.GenTableIndex
import top.potmot.entity.createdTime
import top.potmot.entity.dto.GenModelInput
import top.potmot.entity.dto.GenModelSimpleView
import top.potmot.entity.dto.GenModelView
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.tableId
import top.potmot.error.ModelLoadException

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenModelView? =
        sqlClient.findById(GenModelView::class, id)

    @GetMapping("/valueData/{id}")
    fun getValueData(@PathVariable id: Long): ModelInputEntities? =
        sqlClient.findById(GenModelView::class, id)?.getGraphEntities()

    @GetMapping
    fun list(): List<GenModelSimpleView> =
        sqlClient.createQuery(GenModel::class) {
            orderBy(table.createdTime)
            select(table.fetch(GenModelSimpleView::class))
        }.execute()

    @PostMapping
    @Throws(ModelLoadException::class)
    fun save(
        @RequestBody input: GenModelInput
    ): Long =
        transactionTemplate.execute {
            // 保存 model 和 enums
            val savedModel = sqlClient.save(input, if (input.id == null) SaveMode.INSERT_ONLY else SaveMode.UPDATE_ONLY).modifiedEntity

            parseGraphData(savedModel.id, input.graphData).let { (tableModelInputs, associationModelInputs) ->
                // 创建 enum name -> id map，用于映射 table.columns.enum
                val enumNameIdMap = savedModel.enums.associate { it.name to it.id }

                val tableInputsList =
                    tableModelInputs.map {
                        it.toInputs(enumNameIdMap)
                    }

                // 保存 tables
                val tables = tableInputsList.map { it.table }
                val savedTables = sqlClient.entities.saveInputs(tables).simpleResults.map { it.modifiedEntity }

                // 移除遗留 tables
                sqlClient.createDelete(GenTable::class) {
                    where(table.modelId eq savedModel.id)
                    where(table.id valueNotIn savedTables.map { it.id })
                }.execute()

                val savedTableNameMap = savedTables.associateBy { it.name }

                // 保存 superTables
                tableInputsList.forEach { (table, _, superTableNames) ->
                    val inheritTable = savedTableNameMap[table.name]
                        ?: throw ModelLoadException.table("Table [${table.name}] not found")

                    val superTables = superTableNames.map { superTableName ->
                        savedTableNameMap[superTableName]
                            ?: throw ModelLoadException.table("Super table [${superTableName}] not found")
                    }

                    sqlClient.getAssociations(GenTable::superTables)
                        .saveAll(listOf(inheritTable.id), superTables.map { it.id })
                }

                // 保存 indexes
                tableInputsList.forEach { (table, indexes) ->
                    val savedTable = savedTableNameMap[table.name]
                        ?: throw ModelLoadException.index("Indexes [${indexes.joinToString(",") { it.name }}] recreate fail: \nTable [${table.name}] not found")

                    val columnNameIdMap = savedTable.columns.associate { it.name to it.id }

                    val indexInputs = indexes.map { it.toInput(savedTable.id, columnNameIdMap) }
                    val savedIndexes =
                        sqlClient.entities.saveInputs(indexInputs).simpleResults.map { it.modifiedEntity }

                    // 移除遗留 indexes
                    sqlClient.createDelete(GenTableIndex::class) {
                        where(this.table.tableId eq savedTable.id)
                        where(this.table.id valueNotIn savedIndexes.map { it.id })
                    }.execute()
                }

                // 保存 associations
                val associationInputs = associationModelInputs.map { it.toInput(savedTables) }
                sqlClient.entities.saveEntities(associationInputs.map { it.toEntity { modelId = savedModel.id } })
            }

            savedModel.id
        }!!

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int =
        transactionTemplate.execute {
            sqlClient.deleteByIds(GenModel::class, ids).affectedRowCount(GenModel::class)
        }!!
}
