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
    @Autowired val configService: ConfigService,
    @Autowired val globalGenConfig: GlobalGenConfig
) {
    /**
     * 测试配置编辑功能是否符合预期
     */
    @Test
    fun testConfigEdit() {
        useContext {
            assertEquals(GenLanguage.KOTLIN, globalGenConfig.language)

            val testAuthor = "AUTHOR"
            val oldAuthor = globalGenConfig.author
            configService.setConfig(GenConfigProperties(author = testAuthor))
            assertEquals(testAuthor, globalGenConfig.author)
            configService.setConfig(GenConfigProperties(author = oldAuthor))

            val oldLanguage = globalGenConfig.language
            configService.setConfig(GenConfigProperties(language = GenLanguage.JAVA))
            assertEquals(GenLanguage.JAVA, globalGenConfig.language)
            configService.setConfig(GenConfigProperties(language = oldLanguage))

            val testColumnPrefix = "C_"
            val oldColumnPrefix = globalGenConfig.columnNamePrefixes
            configService.setConfig(GenConfigProperties(columnNamePrefixes = testColumnPrefix))
            assertEquals(testColumnPrefix, globalGenConfig.columnNamePrefixes)
            configService.setConfig(GenConfigProperties(columnNamePrefixes = oldColumnPrefix))
            assertEquals(oldColumnPrefix, globalGenConfig.columnNamePrefixes)

        }
    }
}
