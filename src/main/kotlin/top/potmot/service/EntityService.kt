package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityDetailView
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.tableId

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired val sqlClient: KSqlClient,
) {
    @GetMapping("/language")
    fun listLanguage(): List<GenLanguage> =
        GenLanguage.entries

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEntityDetailView? =
        sqlClient.findById(GenEntityDetailView::class, id)


    @GetMapping("/table/{tableId}")
    fun getByTableId(@PathVariable tableId: Long): GenEntityDetailView? =
        sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOneOrNull()

    @PutMapping
    fun config(@RequestBody input: GenEntityConfigInput): Long =
        sqlClient.update(input).modifiedEntity.id
}
