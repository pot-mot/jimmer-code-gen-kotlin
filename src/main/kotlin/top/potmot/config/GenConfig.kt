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

    /** 生成 tableDefine 时携带外键 */
    var tableDefineWithFk: Boolean = true

    /** 作者  */
    var author: String = ""

    /** 默认包路径  */
    var defaultPackagePath: String = "com.example"

    /** 是否生成 IdView 属性 */
    var idViewProperty: Boolean = true

    /** 逻辑删除默认配置 */
    var logicalDeletedAnnotation: String = "@LogicalDeleted(\"true\")"

    /** 是否生成 Table 注释 */
    var tableAnnotation: Boolean = true

    /** 是否生成 Column 注释 */
    var columnAnnotation: Boolean = false

    /** 是否生成 JoinTable 注释 */
    var joinTableAnnotation: Boolean = true

    /** 是否生成 JoinColumn 注释 */
    var joinColumnAnnotation: Boolean = true

    /**
     * 表名前缀
     * 关联匹配 与 实体名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tablePrefix: String = ""

    /**
     * 表名后缀
     * 关联匹配 与 实体名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableSuffix: String = ""

    /**
     * 表注释前缀
     * 实体注释生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableCommentPrefix: String = ""

    /**
     * 表注释后缀
     * 实体注释生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableCommentSuffix: String = ""

    /**
     * 列名前缀
     * 属性名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnPrefix: String = ""

    /**
     * 列名后缀
     * 属性名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnSuffix: String = ""

    /**
     * 列注释前缀
     * 属性名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnCommentPrefix: String = ""

    /**
     * 列注释后缀
     * 属性注释生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnCommentSuffix: String = ""

    fun tablePrefixes(): List<String> {
        return tablePrefix.split(",").map { it.trim() }
    }

    fun tableSuffixes(): List<String> {
        return tableSuffix.split(",").map { it.trim() }
    }

    fun tableCommentPrefixes(): List<String> {
        return tableCommentPrefix.split(",").map { it.trim() }
    }

    fun tableCommentSuffixes(): List<String> {
        return tableCommentSuffix.split(",").map { it.trim() }
    }

    fun columnPrefixes(): List<String> {
        return columnPrefix.split(",").map { it.trim() }
    }

    fun columnSuffixes(): List<String> {
        return columnSuffix.split(",").map { it.trim() }
    }

    fun columnCommentPrefixes(): List<String> {
        return columnCommentPrefix.split(",").map { it.trim() }
    }

    fun columnCommentSuffixes(): List<String> {
        return columnCommentSuffix.split(",").map { it.trim() }
    }

    fun setLanguage(language: String) {
        when (language.uppercase()) {
            GenLanguage.JAVA.name -> {
                this.language = GenLanguage.JAVA
            }

            GenLanguage.KOTLIN.name -> {
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
        newConfig.dataSourceType?.let {
            dataSourceType = it
        }
        newConfig.separator?.let {
            separator = it
        }

        newConfig.language?.let {
            language = it
        }

        newConfig.tableDefineWithFk?.let {
            tableDefineWithFk = it
        }

        newConfig.author?.let {
            author = it
        }

        newConfig.defaultPackagePath?.let {
            defaultPackagePath = it
        }

        newConfig.logicalDeletedAnnotation?.let {
            logicalDeletedAnnotation = it
        }

        newConfig.idViewProperty?.let {
            idViewProperty = it
        }

        newConfig.tableAnnotation?.let {
            tableAnnotation = it
        }

        newConfig.columnAnnotation?.let {
            columnAnnotation = it
        }

        newConfig.joinTableAnnotation?.let {
            joinTableAnnotation = it
        }

        newConfig.joinColumnAnnotation?.let {
            joinColumnAnnotation = it
        }

        newConfig.tablePrefix?.let {
            tablePrefix = it
        }

        newConfig.tableSuffix?.let {
            tableSuffix = it
        }

        newConfig.tableCommentPrefix?.let {
            tableCommentPrefix = it
        }

        newConfig.tableCommentSuffix?.let {
            tableCommentSuffix = it
        }

        newConfig.columnPrefix?.let {
            columnPrefix = it
        }

        newConfig.columnSuffix?.let {
            columnSuffix = it
        }

        newConfig.columnCommentPrefix?.let {
            columnCommentPrefix = it
        }

        newConfig.columnCommentSuffix?.let {
            columnCommentSuffix = it
        }
    }
}
