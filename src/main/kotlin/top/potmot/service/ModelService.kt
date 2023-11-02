package top.potmot.service

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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.liquibase.createSql
import top.potmot.enumeration.DataBaseType
import top.potmot.model.GenDataSource
import top.potmot.model.GenModel
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableColumnsInput

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    val sqlClient: KSqlClient,
) {
    @GetMapping
    fun list(): List<GenModelView> {
        return sqlClient.entities.findAllViews(GenModelView::class) {}
    }

    @GetMapping("/type")
    fun listDataBaseType(): List<Pair<String, Int>> =
        DataBaseType.values().map {
            Pair(it.name, it.code)
        }

    @PostMapping
    @Transactional
    fun save(
        @RequestBody input: GenModelInput
    ): Long {
        return sqlClient.save(input).modifiedEntity.id
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenModel::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }

    @PostMapping("/load")
    @Transactional
    fun loadToDataSource(
        @RequestParam id: Long,
        @RequestParam tables: List<GenTableColumnsInput>,
        @RequestParam associations: List<GenAssociationModelInput>
    ): String? {
        return sqlClient.findById(GenDataSource::class, id)?.createSql(tables, associations)
    }
}
