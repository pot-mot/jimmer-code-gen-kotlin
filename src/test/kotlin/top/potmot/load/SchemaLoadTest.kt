package top.potmot.load

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.service.DataSourceService
import top.potmot.service.SchemaService

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SchemaLoadTest(
    @Autowired val dataSourceService: DataSourceService,
    @Autowired val schemaService: SchemaService
) {
    @Test
    @Order(1)
    fun testLoadMysqlSchema() {
        val insertId = dataSourceService.insert(
            GenDataSourceInput(
                name = "test",
                host = "127.0.0.1",
                port = "3306",
                urlSuffix = "",
                type = DataSourceType.MySQL,
                username = "root",
                password = "root",
                orderKey = 0L,
                remark = "test"
            )
        )

        val viewSchemas = schemaService.preview(insertId)

        if (viewSchemas.isNotEmpty()) {
            schemaService.load(insertId, viewSchemas[0].name)
        }
    }

    @Test
    @Order(2)
    fun testLoadPostgreSchema() {
        val insertId = dataSourceService.insert(
            GenDataSourceInput(
                name = "test",
                host = "127.0.0.1",
                port = "5432",
                urlSuffix = "/postgres",
                type = DataSourceType.PostgreSQL,
                username = "postgres",
                password = "root",
                orderKey = 0L,
                remark = "test"
            )
        )

        val viewSchemas = schemaService.preview(insertId)

        if (viewSchemas.isNotEmpty()) {
            schemaService.load(insertId, viewSchemas[0].name)
        }
    }
}
