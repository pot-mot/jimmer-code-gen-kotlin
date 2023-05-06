package top.potmot.jimmercodegen.gentable

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.jimmercodegen.service.GenService

@SpringBootTest
class GenServiceTest (
    @Autowired val genService: GenService,
) {
    @Test
    fun importGenTable() {
        val tables = ArrayList<String>()
        tables.add("block")
        println(genService.importGenTable(tables))
    }

}