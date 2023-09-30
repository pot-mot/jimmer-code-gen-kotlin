package top.potmot.import

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enum.DataSourceType
import top.potmot.extension.getCatalog
import top.potmot.extension.toSource
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.service.DataSourceService

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DataSourceImportTest(
    @Autowired val dataSourceService: DataSourceService
) {
    @Test
    @Order(1)
    fun testImportSchema() {
        val insertId = dataSourceService.insert(
            GenDataSourceInput(
                name = "test",
                host = "127.0.0.1",
                port = "3306",
                type = DataSourceType.MySQL,
                username = "root",
                password = "root",
                orderKey = 0L,
                remark = "test"
            )
        )

        val viewSchemas = dataSourceService.viewSchemas(insertId)

        if (viewSchemas.isNotEmpty()) {
            dataSourceService.importSchema(insertId, viewSchemas[0].name)
        }
    }
}
