package top.potmot.model

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.service.ModelService

@SpringBootTest
@ActiveProfiles("test-kotlin", "postgresql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToSql (
    @Autowired val modelService: ModelService
) {
    @Test
    @Order(1)
    fun testCreateSql() {
        modelService.toSql(8, 2)?.let {
            println(it)
        }
    }
}
