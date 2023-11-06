package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.ast.tuple.Tuple2
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
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
import top.potmot.enumeration.DataBaseType
import top.potmot.extension.createSql
import top.potmot.model.GenDataSource
import top.potmot.model.GenModel
import top.potmot.model.copy
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.modifiedTime

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    val sqlClient: KSqlClient,
) {
    @GetMapping
    fun list(): List<GenModelView> {
        return sqlClient.createQuery(GenModel::class) {
            orderBy(table.modifiedTime.desc())
            select(table.fetch(GenModelView::class))
        }.execute()
    }

    @GetMapping("/type")
    fun listDataBaseType(): Map<String, Int> =
        DataBaseType.values().associate {
            Pair(it.name, it.code)
        }

    @PostMapping
    @Transactional
    fun save(
        @RequestBody input: GenModelInput
    ): Long {
        return sqlClient.save(input).modifiedEntity.id
    }

    @PostMapping("/insert/{schemaId}")
    @Transactional
    fun insertToSchema(
        @PathVariable schemaId: Long,
        @RequestParam tables: List<GenTableColumnsInput>,
        @RequestParam associations: List<GenAssociationModelInput>,
    ): Tuple2<List<Long>, List<Long>> {
        val tableIds = mutableListOf<Long>()

        val associationIds = mutableListOf<Long>()

        tables
            .map {
                it.toEntity().copy {
                    this.schemaId = schemaId
                }
            }
            .forEach {
                tableIds += sqlClient.insert(it).modifiedEntity.id
            }

        associations
            .map {
                it.toEntity().copy {
                    this.sourceColumn().table().schemaId = schemaId
                    this.targetColumn().table().schemaId = schemaId
                }
            }
            .forEach {
                associationIds += sqlClient.insert(it).modifiedEntity.id
            }

        return Tuple2(tableIds, associationIds)
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenModel::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @PostMapping("/sql")
    fun toSql(
        @RequestParam id: Long,
        @RequestParam dataSourceId: Long,
    ): String? {
        val model = sqlClient.findById(GenModelView::class, id)
        val dataSource = sqlClient.findById(GenDataSource::class, dataSourceId)

        return dataSource?.let {
            model?.createSql(it)
        }
    }

    @PostMapping("/load")
    @Transactional
    fun loadToDataSource(
        @RequestParam id: Long,
        @RequestParam dataSourceId: Long,
    ): String? {
        TODO()
    }
}
