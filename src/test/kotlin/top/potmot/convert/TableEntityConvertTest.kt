package top.potmot.convert

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enum.GenLanguage
import top.potmot.service.EntityService

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TableEntityConvertTest(
    @Autowired val entityService: EntityService
) {
    @Test
    @Order(1)
    fun testEntityStringifyJava() {
        entityService.preview(listOf(1L), GenLanguage.JAVA).values.forEach{ println(it)}
    }

    @Test
    @Order(2)
    fun testEntityStringifyKotlin() {
        entityService.preview(listOf(1L), GenLanguage.KOTLIN).values.forEach{ println(it)}
    }
}
