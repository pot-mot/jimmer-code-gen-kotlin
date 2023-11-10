package top.potmot.model

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.extension.valueToData
import top.potmot.service.ModelService

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToData (
    @Autowired val modelService: ModelService
) {
    @Test
    @Order(1)
    fun testValueData() {
        val model = modelService.get(1)
        println(model?.valueToData())
    }
}
