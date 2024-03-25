package top.potmot.entity.convert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import top.potmot.core.entity.convert.snakeToCamel

/**
 * 校验名称转换是否符合预期
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NameConvertTest {
    @Test
    @Order(1)
    fun testsnakeToCamel() {
        assertEquals("CreateUserId", snakeToCamel("CREATE_USER_ID"))
        assertEquals("HelloWorld", snakeToCamel("HELLO_WORLD"))
        assertEquals("HelloWorld", snakeToCamel("hello_world"))
        assertEquals("HelloWorld", snakeToCamel("hELLO_wORLD"))
        assertEquals("HelloWorld", snakeToCamel("Hello_World"))
        assertEquals("Helloworld", snakeToCamel("HelloWorld"))
        assertEquals("HelloWorld", snakeToCamel("`hello_world`"))
        assertEquals("HelloWorld", snakeToCamel("  `hello_world`  "))
        assertEquals("HelloWorld", snakeToCamel("\"hello_world\""))
        assertEquals("HelloWorld1", snakeToCamel("(hello_world1)"))
    }
}
