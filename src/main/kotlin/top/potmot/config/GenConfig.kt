package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 读取代码生成相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "gen")
class GenConfig {
    fun setAuthor(author: String) {
        Companion.author = author
    }

    fun setPackageName(packageName: String) {
        Companion.packageName = packageName
    }

    fun setAutoRemovePre(autoRemovePre: Boolean) {
        Companion.autoRemovePre = autoRemovePre
    }

    fun setTablePrefix(tablePrefix: String) {
        Companion.tablePrefix = tablePrefix
    }

    fun setShowSql(showSql: Boolean) {
        Companion.showSql = showSql
    }

    fun setLanguage(language: String) {
        Companion.language = language
    }

    companion object {
        /** 作者  */
        var author: String = ""

        /** 生成包路径  */
        var packageName: String = ""

        /** 自动去除表前缀，默认是true  */
        var autoRemovePre = false

        /** 表前缀(类名不会包含表前缀)  */
        var tablePrefix: String = ""

        /** 是否展示sql, 在查询关联时请尽量关闭，避免栈溢出 */
        var showSql: Boolean = false

        /** 语言，java/kotlin */
        var language: String = "java"
    }
}