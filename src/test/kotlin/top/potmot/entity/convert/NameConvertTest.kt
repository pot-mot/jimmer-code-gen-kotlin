package top.potmot.entity.convert

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import top.potmot.core.entity.convert.snakeToUpperCamel

/**
 * 校验名称转换是否符合预期
 */
class NameConvertTest {
    @ParameterizedTest
    @MethodSource("arguments")
    fun testSnakeToUpperCamel(argument: Pair<String, String>) {
        assertEquals(argument.second, snakeToUpperCamel(argument.first))
    }

    companion object {
        @JvmStatic
        fun arguments(): List<Pair<String, String>> =
            listOf(
                "CREATE_USER_ID" to "CreateUserId",
                "HELLO_WORLD" to "HelloWorld",
                "hello_world" to "HelloWorld",
                "hELLO_wORLD" to "HELLOWORLD",
                "Hello_World" to "HelloWorld",
                "HelloWorld" to "HelloWorld",
                "`hello_world`" to "HelloWorld",
                "  `hello_world`  " to "HelloWorld",
                "\"hello_world\"" to "HelloWorld",
                "(hello_world1)" to "HelloWorld1"
            )
    }
}
