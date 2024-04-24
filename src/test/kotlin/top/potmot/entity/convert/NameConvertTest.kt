package top.potmot.entity.convert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import top.potmot.core.entity.convert.snakeToUpperCamel

/**
 * 校验名称转换是否符合预期
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NameConvertTest {
    @Test
    @Order(1)
    fun testSnakeToUpperCamel() {
        assertEquals("CreateUserId", snakeToUpperCamel("CREATE_USER_ID"))
        assertEquals("HelloWorld", snakeToUpperCamel("HELLO_WORLD"))
        assertEquals("HelloWorld", snakeToUpperCamel("hello_world"))
        assertEquals("HELLOWORLD", snakeToUpperCamel("hELLO_wORLD"))
        assertEquals("HelloWorld", snakeToUpperCamel("Hello_World"))
        assertEquals("HelloWorld", snakeToUpperCamel("HelloWorld"))
        assertEquals("HelloWorld", snakeToUpperCamel("`hello_world`"))
        assertEquals("HelloWorld", snakeToUpperCamel("  `hello_world`  "))
        assertEquals("HelloWorld", snakeToUpperCamel("\"hello_world\""))
        assertEquals("HelloWorld1", snakeToUpperCamel("(hello_world1)"))
    }
}
