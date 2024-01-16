package top.potmot.service

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.database.load.getGraphEntities
import top.potmot.core.database.load.parseGraphData
import top.potmot.core.database.load.toInput
import top.potmot.core.database.load.toInputPart
import top.potmot.error.ModelLoadException
import top.potmot.model.GenModel
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.createdTime
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.GenModelInput
import top.potmot.model.dto.GenModelSimpleView
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableModelInput

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
    fun getValueData(@PathVariable id: Long): Pair<List<GenTableModelInput>, List<GenAssociationModelInput>>? {
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
        /**
         * 1. 创建或更新 model
         */
        val model = sqlClient.save(input).modifiedEntity

        /**
         * 2. 保存关联数据
         */
        if (!input.graphData.isNullOrBlank()) {
            parseGraphData(model.id, input.graphData!!).let { (tables, associations) ->
                /**
                 * 2.1
                 * 创建 enum name -> id map，用于映射 table.columns.enum
                 */
                val enumNameIdMap = model.enums.associate { it.name to it.id }

                /**
                 * 2.2
                 * 保存 tables
                 */
                val tableIndexesPairs = tables.map {
                    it.toInputPart(enumNameIdMap)
                }.toMutableList()

                /**
                 * 2.2.1
                 * 保存 table，忽视 indexes
                 */
                val modelWithTables = new(GenModel::class).by {
                    this.id = model.id
                    this.tables = tableIndexesPairs.map { it.first }
                }
                val savedModelWithTables = sqlClient.update(modelWithTables).modifiedEntity

                /**
                 * 2.2.2
                 * 保存余下的 indexes
                 */
                val savedTables = savedModelWithTables.tables
                val savedTableMap = savedTables.associateBy { it.name }
                tableIndexesPairs.forEach { (table, indexes) ->
                    if (!savedTableMap.containsKey(table.name)) {
                        throw ModelLoadException.index("Indexes [${indexes.joinToString(",") { it.name }}] recreate fail: \nTable [${table.name}] not found")
                    }
                    val savedTable = savedTableMap[table.name]!!
                    sqlClient.update(savedTable.copy {
                        this.indexes =
                            indexes.map { index -> index.toInput(savedTable.columns.associate { it.name to it.id }) }
                    })
                }

                /**
                 * 2.3 保存 association
                 */
                val associationInputs = associations.map { it.toInput(savedTables) }
                val modelWithAssociations = new(GenModel::class).by {
                    this.id = model.id
                    this.associations = associationInputs.map { it.toEntity() }
                }
                sqlClient.update(modelWithAssociations).modifiedEntity

                if (!model.syncConvertEntity) {
                    convertService.convert(
                        savedTables.map { it.id },
                        model.id,
                        GenConfigProperties(model)
                    )
                }
            }
        }

        return model.id
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenModel::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }
}
