package top.potmot.model.associations.fake.oneToOne

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.associations.AssociationsBaseTest
import top.potmot.model.associations.fake.ONE_TO_ONE
import top.potmot.model.createBaseModel
import top.potmot.model.dto.GenConfig
import top.potmot.service.ConvertService
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2", "hide-sql")
class TestOneToOneAssociations(
    @Autowired modelService: ModelService,
    @Autowired convertService: ConvertService,
    @Autowired previewService: PreviewService
): AssociationsBaseTest(
    modelService,
    convertService,
    previewService,
) {
    override fun getBaseModel() =
        createBaseModel(ONE_TO_ONE)

    override fun getEntityResult(config: GenConfig) =
        when(config.language) {
            GenLanguage.KOTLIN -> if (config.realFk) kotlinRealFkResult else kotlinFakeFkResult
            GenLanguage.JAVA -> if (config.realFk) javaRealFkResult else javaFakeFkResult
        }

    override fun getTableDefineResult(config: GenConfig) =
        when(config.dataSourceType) {
            DataSourceType.MySQL -> mysqlResult
            DataSourceType.PostgreSQL -> postgresResult
            DataSourceType.H2 -> h2Result
        }
}
