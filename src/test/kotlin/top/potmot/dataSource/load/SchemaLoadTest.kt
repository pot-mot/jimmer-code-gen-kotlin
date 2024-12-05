package top.potmot.dataSource.load

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.junit.jupiter.api.Assertions.assertEquals
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
import top.potmot.entity.GenTable
import top.potmot.entity.id
import top.potmot.enumeration.SelectType
import top.potmot.entity.query.AssociationTableQuery
import top.potmot.entity.schemaId
import top.potmot.service.AssociationService
import top.potmot.service.DataSourceService
import top.potmot.service.SchemaService

private const val SAVED_SCHEMA_SIZE = 1

private const val SAVED_TABLE_SIZE = 17

private const val SAVED_ASSOCIATION_SIZE = 26

@SpringBootTest
@ActiveProfiles("test", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SchemaLoadTest(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val dataSourceService: DataSourceService,
    @Autowired val schemaService: SchemaService,
    @Autowired val associationService: AssociationService
) {
    fun validateResult(dataSourceId: Long, schemaName: String = "jimmer_code_gen") {
        val schema = schemaService.preview(dataSourceId).first { it.name.split(".").last() == schemaName }

        val savedSchemaIds = schemaService.load(dataSourceId, schema.name)
        assertEquals(SAVED_SCHEMA_SIZE, savedSchemaIds.size)

        val saveTableIds = sqlClient.executeQuery(GenTable::class) {
            where(table.schemaId valueIn savedSchemaIds)
            select(table.id)
        }
        assertEquals(SAVED_TABLE_SIZE, saveTableIds.size)

        val saveAssociations = associationService.queryByTable(
            AssociationTableQuery(
                tableIds = saveTableIds,
                selectType = SelectType.AND
            )
        )
        assertEquals(SAVED_ASSOCIATION_SIZE, saveAssociations.size)
    }

    @Test
    @Order(1)
    fun testLoadMysqlSchema() {
        val dataSourceId = dataSourceService.save(
            mysqlDataSource
        )

        validateResult(dataSourceId)
    }

    @Test
    @Order(2)
    fun testLoadPostgresSchema() {
        val dataSourceId = dataSourceService.save(
            postgresDataSource
        )

        validateResult(dataSourceId)
    }

    @Test
    @Order(3)
    fun testLoadH2Schema() {
        val dataSourceId = dataSourceService.save(
            h2DataSource
        )

        validateResult(dataSourceId, "PUBLIC")
    }
}
