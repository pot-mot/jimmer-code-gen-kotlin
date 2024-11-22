package top.potmot.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.context.useContext
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.service.ConfigService

@SpringBootTest
@ActiveProfiles("test", "h2")
class AssociationMatchTest(
    @Autowired val configService: ConfigService
) {
    /**
     * 测试配置编辑功能是否符合预期
     */
    @Test
    fun testConfigEdit() {
        useContext {
            assertEquals(GenLanguage.KOTLIN, GlobalGenConfig.language)

            val testAuthor = "AUTHOR"
            val oldAuthor = GlobalGenConfig.author
            configService.setConfig(GenConfigProperties(author = testAuthor))
            assertEquals(testAuthor, GlobalGenConfig.author)
            configService.setConfig(GenConfigProperties(author = oldAuthor))

            val oldLanguage = GlobalGenConfig.language
            configService.setConfig(GenConfigProperties(language = GenLanguage.JAVA))
            assertEquals(GenLanguage.JAVA, GlobalGenConfig.language)
            configService.setConfig(GenConfigProperties(language = oldLanguage))

            val testColumnPrefix = "C_"
            val oldColumnPrefix = GlobalGenConfig.columnNamePrefixes
            configService.setConfig(GenConfigProperties(columnNamePrefixes = testColumnPrefix))
            assertEquals(testColumnPrefix, GlobalGenConfig.columnNamePrefixes)
            configService.setConfig(GenConfigProperties(columnNamePrefixes = oldColumnPrefix))
            assertEquals(oldColumnPrefix, GlobalGenConfig.columnNamePrefixes)

        }
    }
}
