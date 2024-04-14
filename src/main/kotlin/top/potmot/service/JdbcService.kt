package top.potmot.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.JDBCType

@RestController
@RequestMapping("/jdbc")
class JdbcService {
    @GetMapping("/type")
    fun listType(): Map<String, Int> =
        JDBCType.entries.associate {
            Pair(it.name, it.vendorTypeNumber)
        }
}
