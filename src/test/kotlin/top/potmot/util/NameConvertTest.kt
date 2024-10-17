package top.potmot.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import top.potmot.utils.string.camelToUpperSnake
import top.potmot.utils.string.snakeToUpperCamel

/**
 * 校验名称转换是否符合预期
 */
class NameConvertTest {
    @ParameterizedTest
    @MethodSource("snakeToUpperCamelArgument")
    fun testSnakeToUpperCamel(argument: Pair<String, String>) {
        assertEquals(argument.second, snakeToUpperCamel(argument.first))
    }

    @ParameterizedTest
    @MethodSource("camelToUpperSnakeArgument")
    fun testCamelToUpperSnake(argument: Pair<String, String>) {
        assertEquals(argument.second, camelToUpperSnake(argument.first))
    }

    companion object {
        @JvmStatic
        fun snakeToUpperCamelArgument(): List<Pair<String, String>> =
            listOf(
                "CREATE_USER_ID" to "CreateUserId",
                "HELLO_WORLD" to "HelloWorld",
                "hello_world" to "HelloWorld",
                "hELLO_wORLD" to "HELLOWORLD",
                "Hello_World" to "HelloWorld",
                "HelloWorld" to "HelloWorld",
            )

        @JvmStatic
        fun camelToUpperSnakeArgument(): List<Pair<String, String>> =
            listOf(
                "createUserId" to "CREATE_USER_ID",
                "HelloWorld" to "HELLO_WORLD",
                "Helloworld" to "HELLOWORLD",
                "helloWorld" to "HELLO_WORLD",
                "helloworld" to "HELLOWORLD",
            )
    }
}
