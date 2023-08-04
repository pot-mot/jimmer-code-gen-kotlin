package top.potmot.mock

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.dao.GenTableColumnRepository
import top.potmot.dao.GenTableRepository

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class MockGenTable(
    @Autowired
    val genTableRepository: GenTableRepository,
    @Autowired
    val genTableColumnRepository: GenTableColumnRepository
) {
    @Order(1)
    @Test
    fun mockGenTable() {

    }
}
