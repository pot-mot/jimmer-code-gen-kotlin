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

        /**
         * 表名前缀
         * 自动匹配关联与实体名生成时生效
         * 配置文件中由 , 进行分割 */
        var tablePrefix: List<String> = listOf("")

        /**
         * 表名后缀
         * 自动匹配关联与实体名生成时生效
         * 配置文件中由 , 进行分割 */
        var tableSuffix: List<String> = listOf("")

        /** 生成实体时是否依照 tablePrefix 进行前缀移除 */
        var removeTablePrefix: Boolean = true

        /** 生成实体时是否依照 tableSuffix 进行后缀移除 */
        var removeTableSuffix: Boolean = true

        /**
         * 列名前缀
         * 属性体名生成时生效
         * 配置文件中由 , 进行分割 */
        var columnPrefix: List<String> = listOf("")

        /**
         * 列名后缀
         * 属性体名生成时生效
         * 配置文件中由 , 进行分割 */
        var columnSuffix: List<String> = listOf("")

        /** 生成属性时是否依照 columnPrefix 进行后缀移除 */
        var removeColumnPrefix: Boolean = true

        /** 生成属性时是否依照 columnSuffix 进行后缀移除 */
        var removeColumnSuffix: Boolean = true

        /** 分隔符 */
        var separator: String = "_"

        /** 语言，java/kotlin */
        var language: GenLanguage = GenLanguage.KOTLIN

        /** 默认类型 */
        var defaultType: String = "String"
    }

    fun setAuthor(author: String) {
        Companion.author = author
    }

    fun setTablePrefix(tablePrefix: String) {
        Companion.tablePrefix = tablePrefix.split(",").map { it.trim() }
    }

    fun setTableSuffix(tableSuffix: String) {
        Companion.tableSuffix = tableSuffix.split(",").map { it.trim() }
    }

    fun setRemoveTablePrefixes(removeTablePrefix: Boolean) {
        Companion.removeTablePrefix = removeTablePrefix
    }

    fun setRemoveTableSuffixes(removeTableSuffix: Boolean) {
        Companion.removeTableSuffix = removeTableSuffix
    }

    fun setColumnPrefix(columnPrefix: String) {
        Companion.columnPrefix = columnPrefix.split(",").map { it.trim() }
    }

    fun setColumnSuffix(columnSuffix: String) {
        Companion.columnSuffix = columnSuffix.split(",").map { it.trim() }
    }

    fun setRemoveColumnPrefixes(removeColumnPrefix: Boolean) {
        Companion.removeColumnPrefix = removeColumnPrefix
    }

    fun setRemoveColumnSuffixes(removeColumnSuffix: Boolean) {
        Companion.removeColumnSuffix = removeColumnSuffix
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
