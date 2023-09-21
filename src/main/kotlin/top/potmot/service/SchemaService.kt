package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenSchema
import top.potmot.model.dataSourceId
import top.potmot.model.dto.GenSchemaView

@RestController
@RequestMapping("/schema")
class SchemaService (
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping
    fun list(dataSourceId: Long): List<GenSchemaView> {
        return sqlClient
            .createQuery(GenSchema::class) {
                where(table.dataSourceId eq dataSourceId)
                select(table.fetch(GenSchemaView::class))
            }
            .execute()
    }

    @Transactional
    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenSchema::class, ids).totalAffectedRowCount
    }
}
