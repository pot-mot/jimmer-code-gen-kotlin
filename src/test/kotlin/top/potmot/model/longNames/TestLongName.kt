package top.potmot.model.longNames

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.context.multiple
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.BaseTest
import top.potmot.model.createBaseModel
import top.potmot.model.dataSourceTypeProperties
import top.potmot.model.databaseNamingStrategyProperties
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.model.languageProperties

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2", "hide-sql")
class TestLongName : BaseTest() {
    override fun getBaseModel() =
        createBaseModel(LONG_NAMES)

    override fun getEntityResult(config: GenConfig) =
        when (config.language) {
            GenLanguage.KOTLIN -> when (config.databaseNamingStrategy) {
                DatabaseNamingStrategyType.LOWER_CASE -> kotlinLowerResult
                DatabaseNamingStrategyType.UPPER_CASE -> kotlinUpperResult
                DatabaseNamingStrategyType.RAW -> kotlinLowerResult
            }

            GenLanguage.JAVA -> when (config.databaseNamingStrategy) {
                DatabaseNamingStrategyType.LOWER_CASE -> javaLowerResult
                DatabaseNamingStrategyType.UPPER_CASE -> javaUpperResult
                DatabaseNamingStrategyType.RAW -> javaLowerResult
            }
        }

    override fun getTableDefineResult(config: GenConfig) =
        when (config.dataSourceType) {
            DataSourceType.MySQL -> when (config.databaseNamingStrategy) {
                DatabaseNamingStrategyType.LOWER_CASE -> mysqlLowerResult
                DatabaseNamingStrategyType.UPPER_CASE -> mysqlUpperResult
                DatabaseNamingStrategyType.RAW -> mysqlLowerResult
            }

            DataSourceType.PostgreSQL -> when (config.databaseNamingStrategy) {
                DatabaseNamingStrategyType.LOWER_CASE -> postgresLowerResult
                DatabaseNamingStrategyType.UPPER_CASE -> postgresUpperResult
                DatabaseNamingStrategyType.RAW -> postgresLowerResult
            }

            DataSourceType.H2 -> when (config.databaseNamingStrategy) {
                DatabaseNamingStrategyType.LOWER_CASE -> h2LowerCaseResult
                DatabaseNamingStrategyType.UPPER_CASE -> h2UpperCaseResult
                DatabaseNamingStrategyType.RAW -> h2LowerCaseResult
            }
        }

    companion object {
        @JvmStatic
        fun entityProperties(): List<GenConfigProperties> = languageProperties.multiple(databaseNamingStrategyProperties).map {
            // 控制应用 Postgres 的 identifierFilter 处理注解对应名称
            it.copy(dataSourceType = DataSourceType.PostgreSQL)
        }

        @JvmStatic
        fun tableDefineProperties(): List<GenConfigProperties> = dataSourceTypeProperties.multiple(databaseNamingStrategyProperties)
    }
}
