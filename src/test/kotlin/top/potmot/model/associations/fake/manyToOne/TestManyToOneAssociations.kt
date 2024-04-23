package top.potmot.model.associations.fake.manyToOne

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.associations.AssociationsBaseTest
import top.potmot.model.associations.fake.MANY_TO_ONE
import top.potmot.model.createBaseModel
import top.potmot.model.dto.GenConfig

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2", "hide-sql")
class TestManyToOneAssociations : AssociationsBaseTest() {
    override fun getBaseModel() =
        createBaseModel(MANY_TO_ONE)

    override fun getEntityResult(config: GenConfig) =
        when (config.language) {
            GenLanguage.KOTLIN -> if (config.realFk) kotlinRealFkResult else kotlinFakeFkResult
            GenLanguage.JAVA -> if (config.realFk) javaRealFkResult else javaFakeFkResult
        }

    override fun getTableDefineResult(config: GenConfig) =
        when (config.dataSourceType) {
            DataSourceType.MySQL -> mysqlResult
            DataSourceType.PostgreSQL -> postgresResult
            DataSourceType.H2 -> h2Result
        }
}
