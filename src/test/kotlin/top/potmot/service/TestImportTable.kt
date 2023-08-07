package top.potmot.service

import org.babyfish.jimmer.ImmutableObjects
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.service.impl.ImportServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order
import top.potmot.util.LogUtils
import java.sql.Types

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestImportTable(
    @Autowired val importTableService: ImportServiceImpl
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
        assertEquals("MEDIUMTEXT", bio.get().columnType)

        val gender = importTableService.getColumn(table, "gender")
        assertTrue(gender.isPresent)
        assertEquals(Types.CHAR, gender.get().columnTypeCode)
        assertEquals("ENUM", gender.get().columnType)
        assertEquals("Other", gender.get().columnDefault)

        val skillSet = importTableService.getColumn(table, "skill_set")
        assertTrue(skillSet.isPresent)
        assertEquals("JSON",  skillSet.get().columnType)

    }
}
