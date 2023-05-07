package top.potmot.jimmercodegen.gentable

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.jimmercodegen.service.TableService

@SpringBootTest
class TableServiceTest (
    @Autowired val tableService: TableService,
) {
    @Test
    fun importGenTable() {
        val tables = ArrayList<String>()
        tables.add("block")
        tables.add("item")
        tables.add("root_item")
        tableService.importGenTable(tables)
    }

}