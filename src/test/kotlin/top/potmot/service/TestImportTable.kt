package top.potmot.service

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.service.impl.ImportServiceImpl
import top.potmot.util.LogUtils

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestImportTable(
    @Autowired val importTableService: ImportServiceImpl
) {
    @Test
    fun getEntities() {
        importTableService.getEntities().forEach {
            LogUtils.logEntity(it)
        }
    }

    @Test
    fun importTables() {
        importTableService.importTables("gen_table")
    }

    @Test
    fun clearTables() {
        importTableService.clearTables()
    }
}
