package top.potmot.model.associations.logicalDelete.oneToMany

import org.springframework.boot.test.context.SpringBootTest
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.associations.AssociationsBaseTest
import top.potmot.model.associations.logicalDelete.ONE_TO_MANY
import top.potmot.model.createBaseModel
import top.potmot.entity.dto.GenConfig

@SpringBootTest
class TestOneToManyAssociations : AssociationsBaseTest() {
    override fun getBaseModel() =
        createBaseModel(ONE_TO_MANY)

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
