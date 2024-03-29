package top.potmot.service

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.database.load.ModelInputEntities
import top.potmot.core.database.load.getGraphEntities
import top.potmot.core.database.load.parseGraphData
import top.potmot.core.database.load.toInput
import top.potmot.error.ModelLoadException
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.GenTableIndex
import top.potmot.model.by
import top.potmot.model.createdTime
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.GenModelInput
import top.potmot.model.dto.GenModelSimpleView
import top.potmot.model.dto.GenModelView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.tableId

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val convertService: ConvertService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenModelView? {
        return sqlClient.findById(GenModelView::class, id)
    }

    @GetMapping("/valueData/{id}")
    fun getValueData(@PathVariable id: Long): ModelInputEntities? {
        return sqlClient.findById(GenModelView::class, id)?.getGraphEntities()
    }

    @GetMapping
    fun list(): List<GenModelSimpleView> {
        return sqlClient.createQuery(GenModel::class) {
            orderBy(table.createdTime)
            select(table.fetch(GenModelSimpleView::class))
        }.execute()
    }

    @PostMapping
    @Transactional
    @Throws(ModelLoadException::class)
    fun save(
        @RequestBody input: GenModelInput
    ): Long {
        // 保存 model 和 enums
        val savedModel = sqlClient.save(input).modifiedEntity

        // 当 graphData 不为空，进行处理
        if (!input.graphData.isNullOrBlank()) {
            parseGraphData(savedModel.id, input.graphData!!).let { (tables, associations) ->
                // 创建 enum name -> id map，用于映射 table.columns.enum
                val enumNameIdMap = savedModel.enums.associate { it.name to it.id }

                val tableIndexesPairs =
                    tables.map {
                        it.toInput(enumNameIdMap)
                    }

                // 保存 tables
                val tableInputs = tableIndexesPairs.map { it.first }
                val savedTables = sqlClient.entities.saveInputs(tableInputs).simpleResults.map { it.modifiedEntity }

                // 移除遗留 tables
                sqlClient.createDelete(GenTable::class) {
                    where(table.modelId eq savedModel.id)
                    where(table.id valueNotIn savedTables.map { it.id })
                }.execute()

                val savedTableMap = savedTables.associateBy { it.name }

                // 保存 indexes
                tableIndexesPairs.forEach { (table, indexes) ->
                    val savedTable = savedTableMap[table.name]
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
                val associationInputs = associations.map { it.toInput(savedTables) }
                val modelWithAssociations = new(GenModel::class).by {
                    this.id = savedModel.id
                    this.associations = associationInputs.map { it.toEntity() }
                }
                sqlClient.update(modelWithAssociations).modifiedEntity

                // 当模型未开启同步时，仅在初始化时进行转换，后续手动控制模型转换
                if (!savedModel.syncConvertEntity) {
                    convertService.convert(
                        savedTables.map { it.id },
                        savedModel.id,
                        GenConfigProperties(savedModel)
                    )
                }
            }
        }

        return savedModel.id
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenModel::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }
}
