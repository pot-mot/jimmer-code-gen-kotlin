package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.enum.Language
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

        /** 生成包路径  */
        var packageName: String = ""

        /** 表前缀，配置中由 , 进行分割 */
        var tablePrefix: List<String> = listOf("")

        /** 表后缀，配置中由 , 进行分割 */
        var tableSuffix: List<String> = listOf("")

        /** 是否展示sql, 在查询关联时为性能考考虑请关闭 */
        var showSql: Boolean = false

        /** 表名分隔符 */
        var separator: String = "_"

        /** 语言，java/kotlin */
        var language: Language = Language.JAVA

        /** 默认类型 */
        var defaultType: String = "String"
    }

    fun setAuthor(author: String) {
        Companion.author = author
    }

    fun setPackageName(packageName: String) {
        Companion.packageName = packageName
    }

    fun setTablePrefix(tablePrefix: String) {
        Companion.tablePrefix = tablePrefix.split(",").map { it.trim() }
    }

    fun setTableSuffix(tablePrefix: String) {
        Companion.tableSuffix = tablePrefix.split(",").map { it.trim() }
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
            Language.JAVA.value -> {
                Companion.language = Language.JAVA
            }
            Language.KOTLIN.value -> {
                Companion.language = Language.KOTLIN
            }
            else -> {
                throw ConfigException("暂不支持 java 和 kotlin 之外的语言")
            }
        }
    }
}
