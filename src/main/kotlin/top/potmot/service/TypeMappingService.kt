package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenTypeMappingInput

@RestController
@RequestMapping("/typeMapping")
class TypeMappingService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenTypeMapping? {
        return sqlClient.findById(GenTypeMapping::class, id)
    }

    @GetMapping
    fun list(): List<GenTypeMapping> {
        return sqlClient.createQuery(GenTypeMapping::class) {select(table)}.execute()
    }

    @PostMapping
    fun add(@RequestBody typeMapping: GenTypeMappingInput): Long {
        return sqlClient.insert(typeMapping).modifiedEntity.id
    }

    @PutMapping
    @Transactional
    fun update(@RequestBody typeMapping: GenTypeMapping): Long {
        return sqlClient.update(typeMapping).modifiedEntity.id
    }

    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenTypeMapping::class, ids).totalAffectedRowCount
    }

}
