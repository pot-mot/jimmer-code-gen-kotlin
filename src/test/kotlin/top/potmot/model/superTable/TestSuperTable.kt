package top.potmot.model.superTable

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.BaseTest
import top.potmot.model.createBaseModel
import top.potmot.model.dto.GenConfig
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
class TestSuperTable(
    @Autowired modelService: ModelService,
    @Autowired previewService: PreviewService
): BaseTest(
    modelService,
    previewService
) {
    override fun getBaseModel() =
        createBaseModel(GRAPH_DATA)

    override fun getEntityResult(config: GenConfig) =
        when(config.language) {
            GenLanguage.KOTLIN -> kotlinResult
            GenLanguage.JAVA -> javaResult
        }

    override fun getTableDefineResult(config: GenConfig) =
        when(config.dataSourceType) {
            DataSourceType.MySQL -> mysqlResult
            DataSourceType.PostgreSQL -> postgresResult
            DataSourceType.H2 -> h2Result
        }
}
