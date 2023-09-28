package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.enum.GenLanguage
import top.potmot.error.ConfigException

/**
 * 读取代码生成相关配置
 */
@Component
@ConfigurationProperties(prefix = "gen")
class GenConfig {
    companion object {
        /** 作者  */
        var author: String = ""

        /** 表名匹配时忽略的前缀，配置中由 , 进行分割 */
        var tableMatchPrefix: List<String> = listOf("")

        /** 表名匹配时忽略后缀，配置中由 , 进行分割 */
        var tableMatchSuffix: List<String> = listOf("")

        /** 生成实体时是否依照 tableMatchPrefix 进行前缀移除 */
        var removeTablePrefixes: Boolean = false

        /** 生成实体时是否依照 tableMatchSuffix 进行后缀移除 */
        var removeTableSuffixes: Boolean = false

        /** 是否展示sql, 在查询关联时为性能考考虑请关闭 */
        var showSql: Boolean = false

        /** 表名分隔符 */
        var separator: String = "_"

        /** 语言，java/kotlin */
        var language: GenLanguage = GenLanguage.JAVA

        /** 默认类型 */
        var defaultType: String = "String"
    }

    fun setAuthor(author: String) {
        Companion.author = author
    }

    fun setTableMatchPrefix(tableMatchPrefix: String) {
        Companion.tableMatchPrefix = tableMatchPrefix.split(",").map { it.trim() }
    }

    fun setTableMatchSuffix(tableMatchPrefix: String) {
        Companion.tableMatchSuffix = tableMatchPrefix.split(",").map { it.trim() }
    }

    fun setRemoveTablePrefixes(removeTablePrefixes: Boolean) {
        Companion.removeTablePrefixes = removeTablePrefixes
    }

    fun setRemoveTableSuffixes(removeTableSuffixes: Boolean) {
        Companion.removeTableSuffixes = removeTableSuffixes
    }

    fun setShowSql(showSql: Boolean) {
        Companion.showSql = showSql
    }

    fun setSeparator(separator: String) {
        Companion.separator = separator
    }

    fun setDefaultType(defaultType: String) {
        Companion.defaultType = defaultType
    }

    fun setLanguage(language: String) {
        when (language.lowercase()) {
            GenLanguage.JAVA.value -> {
                Companion.language = GenLanguage.JAVA
            }

            GenLanguage.KOTLIN.value -> {
                Companion.language = GenLanguage.KOTLIN
            }

            else -> {
                throw ConfigException("暂不支持 java 和 kotlin 之外的语言")
            }
        }
    }
}
