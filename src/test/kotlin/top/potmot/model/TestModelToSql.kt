package top.potmot.model

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.service.DataSourceService
import top.potmot.service.ModelService

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToSql (
    @Autowired val dataSourceService: DataSourceService,
    @Autowired val modelService: ModelService
) {
    @Test
    @Order(1)
    fun testCreateSql() {
        val dataSources = dataSourceService.list()

        assert(dataSources.isNotEmpty())

        val models = modelService.list()

        assert(models.isNotEmpty())

        val sql = modelService.previewSql(models[0].id, dataSources[0].id)

        assert(sql != null)

        println(sql)
    }
}
