package top.potmot.convert

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.service.EntityService

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TableEntityConvertTest(
    @Autowired val entityService: EntityService
) {
    @Test
    @Order(1)
    fun testEntityConvert() {
        entityService.mapping(listOf(7L))
    }
}
