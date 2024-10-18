package top.potmot.model.superTable

import org.springframework.boot.test.context.SpringBootTest
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.associations.AbstractAssociationsTest
import top.potmot.model.createBaseModel
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.model.languageProperties

@SpringBootTest
class TestSuperTables : AbstractAssociationsTest() {
    override fun getBaseModel() =
        createBaseModel(GRAPH_DATA)

    override fun getEntityResult(config: GenConfig) =
        when (config.language) {
            GenLanguage.KOTLIN -> kotlinResult
            GenLanguage.JAVA -> javaResult
        }

    override fun getTableDefineResult(config: GenConfig) =
        when (config.dataSourceType) {
            DataSourceType.MySQL -> mysqlResult
            DataSourceType.PostgreSQL -> postgresResult
            DataSourceType.H2 -> h2Result
        }

    companion object {
        @JvmStatic
        fun entityProperties(): List<GenConfigProperties> = languageProperties
    }
}
