package top.potmot.jimmercodegen.util

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.external.util.GenUtils

@SpringBootTest
class NameConvertTest {
    @Test
    fun testCamelCaseParse() {

        // 空字符串
        var result: String = GenUtils.convertFieldName("")
        println(result)
        assert(result == "")
        result = GenUtils.convertClassName("")
        println(result)
        assert(result == "")

        // 不包含下划线的字符串
        result = GenUtils.convertFieldName("username")
        println(result)
        assert(result == "username")
        result = GenUtils.convertClassName("Username")
        println(result)
        assert(result == "Username")

        // 仅包含下划线的字符串
        result = GenUtils.convertFieldName("_")
        println(result)
        assert(result == "")
        result = GenUtils.convertClassName("_")
        println(result)
        assert(result == "")

        // 下划线位于字符串开头或结尾的情况
        result = GenUtils.convertFieldName("_username")
        println(result)
        assert(result == "Username")
        result = GenUtils.convertFieldName("username_")
        println(result)
        assert(result == "username")
        result = GenUtils.convertClassName("_Hello_World_")
        println(result)
        assert(result == "HelloWorld")
        result = GenUtils.convertClassName("_hello_world_")
        println(result)
        assert(result == "HelloWorld")

        // 多个连续的下划线的情况
        result = GenUtils.convertFieldName("user__name")
        println(result)
        assert(result == "userName")
        result = GenUtils.convertClassName("hello__world")
        println(result)
        assert(result == "HelloWorld")

        // 大小写
        result = GenUtils.convertFieldName("user_name")
        println(result)
        assert(result == "userName")
        result = GenUtils.convertFieldName("USER_NAME")
        println(result)
        assert(result == "userName")
        result = GenUtils.convertFieldName("usEr_nAme")
        println(result)
        assert(result == "userName")

        result = GenUtils.convertClassName("HELLO_WORLD")
        println(result)
        assert(result == "HelloWorld")
        result = GenUtils.convertClassName("hello_world")
        println(result)
        assert(result == "HelloWorld")
        result = GenUtils.convertClassName("hELLO_wOrld")
        println(result)
        assert(result == "HelloWorld")

        // 含有数字或其他特殊字符的情况
        result = GenUtils.convertFieldName("user_123_name")
        println(result)
        assert(result == "user123Name")
        result = GenUtils.convertClassName("hello-world")
        println(result)
        assert(result == "Hello-world")
    }
}
