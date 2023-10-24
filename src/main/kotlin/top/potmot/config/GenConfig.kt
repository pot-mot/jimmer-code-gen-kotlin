package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.error.ConfigException

/**
 * 读取代码生成相关配置
 */
@Component
@ConfigurationProperties(prefix = "gen")
object GenConfig {
    /** 数据源类型 */
    var dataSourceType: DataSourceType = DataSourceType.MySQL

    /** 分隔符 */
    var separator: String = "_"

    /** 语言，java/kotlin */
    var language: GenLanguage = GenLanguage.KOTLIN

    /** 作者  */
    var author: String = ""

    /** 逻辑删除默认配置 */
    var logicalDeletedAnnotation: String = "@LogicalDeleted(\"true\")"

    /**
     * 表名前缀
     * 自动匹配关联与实体名生成时生效
     * 配置文件中由 , 进行分割 */
    var tablePrefix: String = ""

    /**
     * 表名后缀
     * 自动匹配关联与实体名生成时生效
     * 配置文件中由 , 进行分割 */
    var tableSuffix: String = ""

    /** 生成实体时是否依照 tablePrefix 进行前缀移除 */
    var removeTablePrefix: Boolean = false

    /** 生成实体时是否依照 tableSuffix 进行后缀移除 */
    var removeTableSuffix: Boolean = false

    /**
     * 列名前缀
     * 属性体名生成时生效
     * 配置文件中由 , 进行分割 */
    var columnPrefix: String = ""

    /**
     * 列名后缀
     * 属性体名生成时生效
     * 配置文件中由 , 进行分割 */
    var columnSuffix: String = ""

    /** 生成属性时是否依照 columnPrefix 进行前缀移除 */
    var removeColumnPrefix: Boolean = false

    /** 生成属性时是否依照 columnSuffix 进行后缀移除 */
    var removeColumnSuffix: Boolean = false

    fun tablePrefixes(): List<String> {
        return tablePrefix.split(",").map { it.trim() }
    }

    fun tableSuffixes(): List<String> {
        return tableSuffix.split(",").map { it.trim() }
    }

    fun columnPrefixes(): List<String> {
        return columnPrefix.split(",").map { it.trim() }
    }

    fun columnSuffixes(): List<String> {
        return columnSuffix.split(",").map { it.trim() }
    }

    fun setLanguage(language: String) {
        when (language.lowercase()) {
            GenLanguage.JAVA.value -> {
                this.language = GenLanguage.JAVA
            }

            GenLanguage.KOTLIN.value -> {
                this.language = GenLanguage.KOTLIN
            }

            else -> {
                throw ConfigException("暂不支持 java 和 kotlin 之外的语言")
            }
        }
    }

    fun setDataSourceType(dataSourceType: String) {
        when (dataSourceType.lowercase()) {
            DataSourceType.MySQL.name.lowercase() -> {
                this.dataSourceType = DataSourceType.MySQL
            }

            DataSourceType.PostgreSQL.name.lowercase() -> {
                this.dataSourceType = DataSourceType.PostgreSQL
            }

            else -> {
                throw ConfigException("暂不支持 MySQL 和 PostgreSQL 之外的其他数据源")
            }
        }
    }

    fun merge(newConfig: GenConfigProperties) {
        newConfig.separator?.let {
            separator = it
        }
        newConfig.language?.let {
            language = it
        }
        newConfig.author?.let {
            author = it
        }
        newConfig.logicalDeletedAnnotation?.let {
            logicalDeletedAnnotation = it
        }
        newConfig.tablePrefix?.let {
            tablePrefix = it
        }
        newConfig.tableSuffix?.let {
            tableSuffix = it
        }
        newConfig.removeTablePrefix?.let {
            removeTablePrefix = it
        }
        newConfig.removeTableSuffix?.let {
            removeTableSuffix = it
        }
        newConfig.columnPrefix?.let {
            columnPrefix = it
        }
        newConfig.columnSuffix?.let {
            columnSuffix = it
        }
        newConfig.removeColumnPrefix?.let {
            removeColumnPrefix = it
        }
        newConfig.removeColumnSuffix?.let {
            removeColumnSuffix = it
        }
    }
}
