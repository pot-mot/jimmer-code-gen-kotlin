package top.potmot.dataSource.load

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.dataSource.h2DataSource
import top.potmot.dataSource.mysqlDataSource
import top.potmot.dataSource.postgresDataSource
import top.potmot.query.TableQuery
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
            mysqlDataSource
        )

        val schema = schemaService.preview(savedDataSourceId).first { it.name == "jimmer_code_gen" }

        val savedSchemaIds = schemaService.load(savedDataSourceId, schema.name)

        assert(savedSchemaIds.size == 1)

        val saveTables = tableService.queryIdView(TableQuery(schemaIds = savedSchemaIds))

        assert(saveTables.size == 15)
    }

    @Test
    @Order(2)
    fun testLoadPostgresSchema() {
        val savedDataSourceId = dataSourceService.create(
            postgresDataSource
        )

        val schema = schemaService.preview(savedDataSourceId).first { it.name == "jimmer_code_gen" }

        val savedSchemaIds = schemaService.load(savedDataSourceId, schema.name)

        assert(savedSchemaIds.size == 1)

        val saveTables = tableService.queryIdView(TableQuery(schemaIds = savedSchemaIds))

        assert(saveTables.size == 15)
    }

    @Test
    @Order(3)
    fun testLoadH2Schema() {
        val savedDataSourceId = dataSourceService.create(
            h2DataSource
        )

        val schema = schemaService.preview(savedDataSourceId).first { it.name == "jimmer_code_gen.jimmer_code_gen" }

        val savedSchemaIds = schemaService.load(savedDataSourceId, schema.name)

        assert(savedSchemaIds.size == 1)

        val saveTables = tableService.queryIdView(TableQuery(schemaIds = savedSchemaIds))

        assert(saveTables.size == 15)
    }
}
