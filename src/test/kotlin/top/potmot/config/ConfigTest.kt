package top.potmot.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.context.cleanContextGenConfig
import top.potmot.context.getContextGenConfig
import top.potmot.context.merge
import top.potmot.context.toProperties
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenConfigProperties
import top.potmot.service.ConfigService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AssociationMatchTest(
    @Autowired val configService: ConfigService
) {
    val global = GlobalConfig.common

    /**
     * 测试配置编辑功能是否符合预期
     */
    @Test
    @Order(1)
    fun testEquals() {
        val context = getContextGenConfig()

        assert(global == context)

        cleanContextGenConfig()
    }

    /**
     * 测试配置编辑功能是否符合预期
     */
    @Test
    @Order(2)
    fun testConfig() {
        val context = getContextGenConfig()

        assertEquals(GenLanguage.KOTLIN, global.language)

        val testAuthor = "AUTHOR"
        configService.setConfig(GenConfigProperties(author = testAuthor))
        assertEquals(testAuthor, global.author)

        configService.setConfig(GenConfigProperties(language = GenLanguage.JAVA))
        assertEquals(GenLanguage.JAVA, global.language)

        val testColumnPrefix = "C_"
        configService.setConfig(GenConfigProperties(columnNamePrefixes = testColumnPrefix))
        assertEquals(testColumnPrefix, global.columnNamePrefixes)

        assertEquals(testColumnPrefix, context.columnNamePrefixes)
        assertEquals(testColumnPrefix, global.columnNamePrefixes)

        cleanContextGenConfig()
    }

    /**
     * 测试 global 和 context 的 toProperties 和 merge
     */
    @Test
    @Order(3)
    fun testToPropertiesMerge() {
        val context = getContextGenConfig()

        context.merge(GenConfigProperties(author = "abc"))

        assert(global != context)

        context.merge(global.toProperties())

        assert(global == context)

        global.merge(GenConfigProperties(author = "c"))

        assert(global != context)

        global.merge(context.toProperties())

        assert(global == context)

        cleanContextGenConfig()
    }
}
