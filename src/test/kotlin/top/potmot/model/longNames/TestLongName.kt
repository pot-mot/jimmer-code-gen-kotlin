package top.potmot.model.longNames

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.context.multiple
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.BaseTest
import top.potmot.model.createBaseModel
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.lowerCaseNameProperties
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
class TestLongName(
    @Autowired modelService: ModelService,
    @Autowired previewService: PreviewService
): BaseTest(
    modelService,
    previewService,
) {
    override fun getBaseModel() =
        createBaseModel(LONG_NAMES)

    override val entityTestProperties: List<GenConfigProperties>
        get() = super.entityTestProperties.multiple(lowerCaseNameProperties)

    override val tableDefineTestProperties: List<GenConfigProperties>
        get() = super.tableDefineTestProperties.multiple(lowerCaseNameProperties)

    override fun getEntityResult(config: GenConfig) =
        when(config.language) {
            GenLanguage.KOTLIN -> if (config.lowerCaseName) kotlinLowerResult else kotlinUpperResult
            GenLanguage.JAVA -> if (config.lowerCaseName) javaLowerResult else javaUpperResult
        }

    override fun getTableDefineResult(config: GenConfig) =
        when(config.dataSourceType) {
            DataSourceType.MySQL -> if (config.lowerCaseName) mysqlLowerResult else mysqlUpperResult
            DataSourceType.PostgreSQL -> if (config.lowerCaseName) postgresLowerResult else postgresUpperResult
            DataSourceType.H2 -> if (config.lowerCaseName) mysqlLowerResult else mysqlUpperResult
        }
}
