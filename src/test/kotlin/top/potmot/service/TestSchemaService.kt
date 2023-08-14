package top.potmot.service

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Order
import org.springframework.test.context.ActiveProfiles
import top.potmot.constant.DataSourceType
import top.potmot.dao.GenDataSourceRepository
import top.potmot.dao.GenTableRepository
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenSchemaInsertInput

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
class TestSchemaService(
    @Autowired val genDataSourceRepository: GenDataSourceRepository,
    @Autowired val schemaService: SchemaService,
    @Autowired val genTableRepository: GenTableRepository
) {
    @Order(1)
    @Test
    fun testPreviewTables() {
        val dataSource = genDataSourceRepository.save(GenDataSourceInput(
            DataSourceType.MYSQL, "localhost", "localhost", "3307", "root", "root"
        ))

        schemaService.importTables(
            GenSchemaInsertInput (
                "jimmer-code-gen\\S*",
                dataSource.id
            )
        )
//
//        assertTrue(!ImmutableObjects.isLoaded(table, "id"))
//        assertEquals("single_test_table", table.tableName.lowercase())
//        assertEquals("TABLE", table.tableType)
//        assertTrue(ImmutableObjects.isLoaded(table, "columns"))
//        assertEquals(45, table.columns.size)
//
//        val id = table.getColumn("id")
//        assertTrue(id.isPresent)
//        assertTrue(id.get().isPk)
//        assertTrue(id.get().isAutoIncrement)
//        assertTrue(id.get().isUnique)
//        assertTrue(id.get().isNotNull)
//
//        val age = table.getColumn("age")
//        assertTrue(age.isPresent)
//        assertEquals(Types.INTEGER, age.get().columnTypeCode)
//        assertEquals(null, age.get().columnDefault)
//        assertEquals(10, age.get().columnDisplaySize)
//        assertEquals(0, age.get().columnPrecision)
//
//        val salary = table.getColumn("salary")
//        assertTrue(salary.isPresent)
//        assertEquals(Types.DECIMAL, salary.get().columnTypeCode)
//        assertEquals("1000.00", salary.get().columnDefault)
//        assertEquals(10, salary.get().columnDisplaySize)
//        assertEquals(2, salary.get().columnPrecision)
//
//        val bio = table.getColumn("bio")
//        assertTrue(bio.isPresent)
//        assertEquals(Types.LONGVARCHAR, bio.get().columnTypeCode)
//        assertEquals("MEDIUMTEXT", bio.get().columnType)
//
//        val gender = table.getColumn("gender")
//        assertTrue(gender.isPresent)
//        assertEquals(Types.CHAR, gender.get().columnTypeCode)
//        assertEquals("ENUM", gender.get().columnType)
//        assertEquals("Other", gender.get().columnDefault)
//
//        val skillSet = table.getColumn("skill_set")
//        assertTrue(skillSet.isPresent)
//        assertEquals(Types.LONGVARCHAR, skillSet.get().columnTypeCode)
//        assertEquals("JSON", skillSet.get().columnType)
//        assertEquals(null, skillSet.get().columnDefault)
//
//        val zipcode = table.getColumn("zipcode")
//        assertTrue(zipcode.isPresent)
//        assertEquals(Types.VARCHAR, zipcode.get().columnTypeCode)
//        assertEquals("VARCHAR", zipcode.get().columnType)
//        assertEquals("12345", zipcode.get().columnDefault)
//
//        val isApproved = table.getColumn("is_approved")
//        assertTrue(isApproved.isPresent)
//        assertEquals(Types.BIT, isApproved.get().columnTypeCode)
//        assertEquals("1", isApproved.get().columnDefault)
    }
}
