package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.database.generate.generateTableDefines
import top.potmot.core.database.load.getGraphEntities
import top.potmot.core.database.load.parseGraphData
import top.potmot.core.database.load.toInput
import top.potmot.core.database.load.toInputPair
import top.potmot.enumeration.DataSourceType
import top.potmot.error.DataSourceErrorCode
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.createdTime
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelInput
import top.potmot.model.dto.GenModelSimpleView
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.modelId

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
    fun getValueData(@PathVariable id: Long): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>>? {
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
    fun save(
        @RequestBody input: GenModelInput
    ): Long {
        /**
         * 1. 创建或更新 model
         */
        val model = if (input.id == null) {
            sqlClient.insert(input).modifiedEntity
        } else {
            sqlClient.update(input).modifiedEntity
        }

        /**
         * 2. 保存关联数据
         */
        if (!input.graphData.isNullOrBlank()) {
            parseGraphData(model.id, input.graphData!!).let { (tables, associations) ->
                /**
                 * 2.1 保存舍弃 indexes 的 table
                 */
                val tableInputPairs = tables.map { it.toInputPair() }.toMutableList()
                val modelWithTables = new(GenModel::class).by {
                    this.id = model.id
                    this.tables = tableInputPairs.map { it.first }
                }
                val savedModelWithTables = sqlClient.update(modelWithTables).modifiedEntity

                /**
                 * 2.2 将 indexes 更新至 table
                 */
                val savedTables = savedModelWithTables.tables
                val savedTableMap = savedTables.associateBy { it.name }
                tableInputPairs.forEach { (table, indexes) ->
                    if (!savedTableMap.containsKey(table.name)) {
                        throw RuntimeException("Load model [${model.name}] fail: \nTable [${table.name}] not found")
                    }
                    val savedTable = savedTableMap[table.name]!!
                    sqlClient.update(savedTable.copy {
                        this.indexes = indexes.map { it.toInput(savedTable) }
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
                    convertService.convert(savedTables.map { it.id }, model.packagePath, model.language)
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

    @PostMapping("/sql")
    @ThrowsAll(DataSourceErrorCode::class)
    fun previewSql(
        @RequestParam id: Long,
        @RequestParam(required = false) type: DataSourceType?
    ): List<Pair<String, String>> {
        val tables = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq id)
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

        val types = listOfNotNull(type)

        return generateTableDefines(tables, types)
    }

//    @PostMapping("/sql")
//    @ThrowsAll(DataSourceErrorCode::class)
//    fun previewSql(
//        @RequestParam id: Long,
//        @RequestParam dataSourceId: Long,
//    ): String? {
//        val model = sqlClient.findById(GenModelView::class, id)
//        val dataSource = sqlClient.findById(GenDataSource::class, dataSourceId)
//
//        return dataSource?.let {
//            model?.createSql(it)
//        }
//    }
//
//    @PostMapping("/sql/execute")
//    @ThrowsAll(DataSourceErrorCode::class)
//    @Transactional
//    fun executeSql(
//        @RequestParam id: Long,
//        @RequestParam dataSourceId: Long,
//        @RequestParam schemaName: String,
//    ): IntArray? {
//        val model = sqlClient.findById(GenModelView::class, id)
//        val dataSource = sqlClient.findById(GenDataSource::class, dataSourceId)
//
//        return dataSource?.let {
//            model?.createSql(it)?.let { sql ->
//                dataSource.execute(schemaName, sql)
//            }
//        }
//    }
}
