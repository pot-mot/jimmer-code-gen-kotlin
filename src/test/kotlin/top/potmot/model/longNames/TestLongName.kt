package top.potmot.model.longNames

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.context.multiple
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.BaseTest
import top.potmot.model.createBaseModel
import top.potmot.model.databaseNamingStrategyProperties
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.service.ConvertService
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2", "hide-sql")
class TestLongName(
    @Autowired modelService: ModelService,
    @Autowired convertService: ConvertService,
    @Autowired previewService: PreviewService
) : BaseTest(
    modelService,
    convertService,
    previewService,
) {
    override fun getBaseModel() =
        createBaseModel(LONG_NAMES)

    override val entityTestProperties: List<GenConfigProperties>
        get() = super.entityTestProperties.multiple(databaseNamingStrategyProperties).map {
            // 控制应用 Postgres 的 identifierFilter 处理注解对应名称
            it.copy(dataSourceType = DataSourceType.PostgreSQL)
        }

    override val tableDefineTestProperties: List<GenConfigProperties>
        get() = super.tableDefineTestProperties.multiple(databaseNamingStrategyProperties)

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
}
