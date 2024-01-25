package top.potmot.dataSourceLoad

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.query.TableQuery
import top.potmot.service.DataSourceService
import top.potmot.service.SchemaService
import top.potmot.service.TableService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SchemaLoadTest(
    @Autowired val dataSourceService: DataSourceService,
    @Autowired val schemaService: SchemaService,
    @Autowired val tableService: TableService
) {
    @Test
    @Order(1)
    fun testLoadMysqlSchema() {
        val savedDataSourceId = dataSourceService.create(
            GenDataSourceInput(
                name = "test",
                host = "127.0.0.1",
                port = "3306",
                urlSuffix = "",
                type = DataSourceType.MySQL,
                username = "root",
                password = "root",
                remark = "test"
            )
        )

        val schema = schemaService.preview(savedDataSourceId).first { it.name == "jimmer_code_gen" }

        val savedSchemaIds = schemaService.load(savedDataSourceId, schema.name)

        assert(savedSchemaIds.size == 1)

        val saveTables = tableService.queryIdView(TableQuery(schemaIds = savedSchemaIds))

        assert(saveTables.size == 15)
    }

    @Test
    @Order(2)
    fun testLoadPostgreSchema() {
        val savedDataSourceId = dataSourceService.create(
            GenDataSourceInput(
                name = "test",
                host = "127.0.0.1",
                port = "5432",
                urlSuffix = "/postgres",
                type = DataSourceType.PostgreSQL,
                username = "postgres",
                password = "root",
                remark = "test"
            )
        )

        val schema = schemaService.preview(savedDataSourceId).first { it.name == "jimmer_code_gen" }

        val savedSchemaIds = schemaService.load(savedDataSourceId, schema.name)

        assert(savedSchemaIds.size == 1)

        val saveTables = tableService.queryIdView(TableQuery(schemaIds = savedSchemaIds))

        assert(saveTables.size == 15)
    }
}
