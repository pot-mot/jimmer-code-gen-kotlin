package top.potmot.entity.convert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.entity.convert.tableNameToClassName

/**
 * 校验名称转换是否符合预期
 */
@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
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
        assertEquals("HelloWorld", tableNameToClassName("`hello_world`"))
        assertEquals("HelloWorld", tableNameToClassName("  `hello_world`  "))
        assertEquals("HelloWorld", tableNameToClassName("\"hello_world\""))
        assertEquals("HelloWorld1", tableNameToClassName("(hello_world1)"))
    }
}
