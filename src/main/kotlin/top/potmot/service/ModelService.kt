package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.enumeration.DataBaseType
import top.potmot.model.dto.GenTableColumnsInput

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    val sqlClient: KSqlClient,
) {
    @GetMapping("/type")
    fun listType(): List<Pair<String, Int>> =
        DataBaseType.values().map {
            Pair(it.name, it.code)
        }

    @PostMapping
    @Transactional
    fun create(
        @RequestBody input: GenTableColumnsInput
    ): Long {
        return sqlClient.insert(input).modifiedEntity.id
    }
}
