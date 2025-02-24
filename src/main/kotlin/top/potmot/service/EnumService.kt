package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.dto.GenEnumItemsView

@RestController
@RequestMapping("/enum")
class EnumService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenEnumItemsView? =
        sqlClient.findById(GenEnumItemsView::class, id)
}
