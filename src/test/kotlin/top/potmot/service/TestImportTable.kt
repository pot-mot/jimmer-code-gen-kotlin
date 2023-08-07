package top.potmot.service

import org.babyfish.jimmer.ImmutableObjects
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order
import org.springframework.test.context.ActiveProfiles
import top.potmot.util.LogUtils
import java.sql.Types

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
class TestImportTable(
    @Autowired val importTableService: ImportService
) {
    @Order(1)
    @Test
    fun testPreviewTables() {
        val result = importTableService.previewTables("single_test_table")
        result.forEach {
            LogUtils.logTable(it)
        }
        assertNotNull(result)
        assertTrue(result.size == 1)

        val table = result[0]

        assertTrue(!ImmutableObjects.isLoaded(table, "id"))
        assertEquals("single_test_table", table.tableName.lowercase())
        assertEquals("TABLE", table.tableType)
        assertTrue(ImmutableObjects.isLoaded(table, "columns"))
        assertEquals(45, table.columns.size)

        val id = importTableService.getColumn(table, "id")
        assertTrue(id.isPresent)
        assertTrue(id.get().isPk)
        assertTrue(id.get().isAutoIncrement)
        assertTrue(id.get().isUnique)
        assertTrue(id.get().isNotNull)

        val age = importTableService.getColumn(table, "age")
        assertTrue(age.isPresent)
        assertEquals(Types.INTEGER, age.get().columnTypeCode)
        assertEquals(null, age.get().columnDefault)
        assertEquals(10, age.get().columnDisplaySize)
        assertEquals(0, age.get().columnPrecision)

        val salary = importTableService.getColumn(table, "salary")
        assertTrue(salary.isPresent)
        assertEquals(Types.DECIMAL, salary.get().columnTypeCode)
        assertEquals("1000.00", salary.get().columnDefault)
        assertEquals(10, salary.get().columnDisplaySize)
        assertEquals(2, salary.get().columnPrecision)

        val bio = importTableService.getColumn(table, "bio")
        assertTrue(bio.isPresent)
        assertEquals(Types.LONGVARCHAR, bio.get().columnTypeCode)
        assertEquals("MEDIUMTEXT", bio.get().columnType)

        val gender = importTableService.getColumn(table, "gender")
        assertTrue(gender.isPresent)
        assertEquals(Types.CHAR, gender.get().columnTypeCode)
        assertEquals("ENUM", gender.get().columnType)
        assertEquals("Other", gender.get().columnDefault)

        val skillSet = importTableService.getColumn(table, "skill_set")
        assertTrue(skillSet.isPresent)
        assertEquals(Types.LONGVARCHAR, skillSet.get().columnTypeCode)
        assertEquals("JSON", skillSet.get().columnType)
        assertEquals(null, skillSet.get().columnDefault)

        val zipcode =  importTableService.getColumn(table, "zipcode")
        assertTrue(zipcode.isPresent)
        assertEquals(Types.VARCHAR, zipcode.get().columnTypeCode)
        assertEquals("VARCHAR", zipcode.get().columnType)
        assertEquals("12345", zipcode.get().columnDefault)

        val isApproved = importTableService.getColumn(table, "is_approved")
        assertTrue(isApproved.isPresent)
        assertEquals(Types.BIT, isApproved.get().columnTypeCode)
        assertEquals("1", isApproved.get().columnDefault)
    }

    @Order(2)
    @Test
    fun testPreviewEntities() {
        val result = importTableService.previewEntities("single_test_table")
        result.forEach {
            LogUtils.logEntity(it)
        }
        assertNotNull(result)
        assertTrue(result.size == 1)

        val entity = result[0]
        assertTrue(!ImmutableObjects.isLoaded(entity, "id"))
        assertEquals("SingleTestTable", entity.className)
        assertTrue(ImmutableObjects.isLoaded(entity, "properties"))
        assertEquals(45, entity.properties.size)
    }

    @Order(3)
    @Test
    fun testImportEntities() {
        val result = importTableService.importEntities("single_test_table")
        assertNotNull(result)
        assertTrue(result.size == 1)
        val table = importTableService.getTable(result[0].id).get()
        assertNotNull(table)

        LogUtils.logTable(table)
    }
}
