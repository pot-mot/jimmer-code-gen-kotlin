package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.GenTypeMapping
import top.potmot.entity.dto.GenTypeMappingInput
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.entity.orderKey
import top.potmot.utils.transaction.executeNotNull

@RestController
@RequestMapping("/typeMapping")
class TypeMappingService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenTypeMappingView? =
        sqlClient.findById(GenTypeMappingView::class, id)

    @GetMapping
    fun list(): List<GenTypeMappingView> =
        sqlClient.executeQuery(GenTypeMapping::class) {
            orderBy(table.orderKey)
            select(table.fetch(GenTypeMappingView::class))
        }

    @PostMapping
    fun saveAll(@RequestBody typeMappings: List<GenTypeMappingInput>): List<Long> =
        transactionTemplate.executeNotNull {
            sqlClient.executeDelete(GenTypeMapping::class) {}
            sqlClient.saveInputs(typeMappings).items.map { it.modifiedEntity.id }
        }
}
