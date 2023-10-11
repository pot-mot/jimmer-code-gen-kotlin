package top.potmot.config

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enum.GenLanguage
import top.potmot.service.ConfigService

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AssociationMatchTest(
    @Autowired val configService: ConfigService
) {
    @Test
    @Order(1)
    fun testConfig() {
        val config = configService.getConfig()

        Assertions.assertEquals("_", config.separator)
        Assertions.assertEquals(GenLanguage.KOTLIN, config.language)

        val testAuthor = "AUTHOR"
        configService.setConfig(GenConfigProperties(author = testAuthor))
        Assertions.assertEquals(testAuthor, GenConfig.author)

        configService.setConfig(GenConfigProperties(language = GenLanguage.JAVA))
        Assertions.assertEquals(GenLanguage.JAVA, GenConfig.language)

        val testColumnPrefix = listOf("C_")
        configService.setConfig(GenConfigProperties(columnPrefix = testColumnPrefix))
        Assertions.assertEquals(testColumnPrefix, GenConfig.columnPrefix)

        Assertions.assertEquals("_", GenConfig.separator)

        Assertions.assertEquals(GenConfig, config)
    }
}
