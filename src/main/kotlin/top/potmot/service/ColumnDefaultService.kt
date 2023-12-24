package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.DataSourceType
import top.potmot.model.GenColumnDefault
import top.potmot.model.GenTypeMapping
import top.potmot.model.dataSourceType
import top.potmot.model.dto.GenColumnDefaultInput
import top.potmot.model.dto.GenColumnDefaultView
import top.potmot.model.orderKey
import java.sql.JDBCType

@RestController
@RequestMapping("/columnDefault")
class ColumnDefaultService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/type")
    fun listDatabaseType(): Map<String, Int> =
        JDBCType.values().associate {
            Pair(it.name, it.vendorTypeNumber)
        }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenColumnDefaultView? {
        return sqlClient.findById(GenColumnDefaultView::class, id)
    }

    @GetMapping
    fun list(
        @RequestParam(required = false) dataSourceType: DataSourceType? = null
    ): List<GenColumnDefaultView> {
        return sqlClient.createQuery(GenColumnDefault::class) {
            if (dataSourceType != null) {
                where(table.dataSourceType eq dataSourceType)
            }
            orderBy(table.orderKey)
            select(table.fetch(GenColumnDefaultView::class))
        }.execute()
    }

    @PostMapping
    @Transactional
    fun saveAll(@RequestBody typeMappings: List<GenColumnDefaultInput>): List<Long> {
        val result = mutableListOf<Long>()

        sqlClient.createDelete(GenTypeMapping::class) {}.execute()

        typeMappings.forEach {
            result += sqlClient.save(it).modifiedEntity.id
        }

        return result
    }
}
