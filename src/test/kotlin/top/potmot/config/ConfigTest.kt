package top.potmot.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.context.useContext
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenConfigProperties
import top.potmot.service.ConfigService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
class AssociationMatchTest(
    @Autowired val configService: ConfigService
) {
    /**
     * 测试配置编辑功能是否符合预期
     */
    @Test
    fun testConfigEdit() {
        useContext {
            assertEquals(GenLanguage.KOTLIN, GlobalConfig.common.language)

            val testAuthor = "AUTHOR"
            configService.setConfig(GenConfigProperties(author = testAuthor))
            assertEquals(testAuthor, GlobalConfig.common.author)

            configService.setConfig(GenConfigProperties(language = GenLanguage.JAVA))
            assertEquals(GenLanguage.JAVA, GlobalConfig.common.language)

            val testColumnPrefix = "C_"
            configService.setConfig(GenConfigProperties(columnNamePrefixes = testColumnPrefix))
            assertEquals(testColumnPrefix, GlobalConfig.common.columnNamePrefixes)

            assertEquals(testColumnPrefix, GlobalConfig.common.columnNamePrefixes)
        }
    }
}
