package top.potmot.convert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.convert.columnNameToPropertyName
import top.potmot.core.convert.tableNameToClassName
import top.potmot.core.convert.tableNameToPropertyName

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NameConvertTest {
    @Test
    @Order(1)
    fun testTableNameToClassName() {
        assertEquals("HelloWorld", tableNameToClassName("HELLO_WORLD"))
        assertEquals("HelloWorld", tableNameToClassName("hello_world"))
        assertEquals("HelloWorld", tableNameToClassName("hELLO_wORLD"))
        assertEquals("HelloWorld", tableNameToClassName("Hello_World"))
        assertEquals("Helloworld", tableNameToClassName("HelloWorld"))
    }

    @Test
    @Order(2)
    fun testTableNameToPropertyName() {
        assertEquals("helloWorld", tableNameToPropertyName("HELLO_WORLD"))
        assertEquals("helloWorld", tableNameToPropertyName("hello_world"))
        assertEquals("helloWorld", tableNameToPropertyName("hELLO_wORLD"))
        assertEquals("helloWorld", tableNameToPropertyName("Hello_World"))
        assertEquals("helloworld", tableNameToPropertyName("HelloWorld"))
    }

    @Test
    @Order(3)
    fun testColumnNameToPropertyName() {
        assertEquals("helloWorld", columnNameToPropertyName("HELLO_WORLD"))
        assertEquals("helloWorld", columnNameToPropertyName("hello_world"))
        assertEquals("helloWorld", columnNameToPropertyName("hELLO_wORLD"))
        assertEquals("helloWorld", columnNameToPropertyName("Hello_World"))
        assertEquals("helloworld", columnNameToPropertyName("HelloWorld"))
    }
}
