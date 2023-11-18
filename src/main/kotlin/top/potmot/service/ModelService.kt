package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
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
import top.potmot.core.generate.generateTableDefine
import top.potmot.core.generate.generateTableDefines
import top.potmot.enumeration.DataSourceType
import top.potmot.error.DataSourceErrorCode
import top.potmot.extension.valueToData
import top.potmot.model.GenAssociation
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.modelId
import top.potmot.model.modifiedTime
import java.sql.JDBCType

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenModelView? {
        return sqlClient.findById(GenModelView::class, id)
    }

    @GetMapping("/valueData/{id}")
    fun getValueData(@PathVariable id: Long): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>>? {
        return sqlClient.findById(GenModelView::class, id)?.valueToData()
    }


    @GetMapping
    fun list(): List<GenModelView> {
        return sqlClient.createQuery(GenModel::class) {
            orderBy(table.modifiedTime.desc())
            select(table.fetch(GenModelView::class))
        }.execute()
    }

    @GetMapping("/type")
    fun listDataBaseType(): Map<String, Int> =
        JDBCType.values().associate {
            Pair(it.name, it.vendorTypeNumber)
        }

    @PostMapping
    @Transactional
    fun save(
        @RequestBody input: GenModelInput
    ): Long {
        val modelId = if (input.id == null) {
            sqlClient.insert(input).modifiedEntity.id
        } else {
            sqlClient.update(input).modifiedEntity.id
        }

        if (!input.value.isNullOrBlank()) {
            getValueData(modelId)?.apply {
                // 差量更新 table
                first.forEach { sqlClient.save(it) }

                // 全量更新 association
                sqlClient.createDelete(GenAssociation::class) {
                    where(table.modelId eq modelId)
                }.execute()

                second.forEach { sqlClient.insert(it)}
            }
        }

        return modelId
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
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()

        val tables = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq id)
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

        val types = listOfNotNull(type)

        map += generateTableDefines(tables, types)

        tables.forEach {
            map += generateTableDefine(it, types)
        }

        return map
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
