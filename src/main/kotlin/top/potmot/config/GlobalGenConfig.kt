package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage

/**
 * 代码生成配置
 */
@Component
@ConfigurationProperties(prefix = "gen")
object GlobalGenConfig {
    /** 数据源类型 */
    var dataSourceType: DataSourceType = DataSourceType.MySQL

    /** 语言，java/kotlin */
    var language: GenLanguage = GenLanguage.KOTLIN

    /** 默认真实外键 */
    var realFk: Boolean = true

    /** 默认启用小写命名 */
    var lowerCaseName: Boolean = true

    /** 作者  */
    var author: String = ""

    /** 默认包路径  */
    var packagePath: String = "com.example"

    /** 是否生成 IdView 属性 */
    var idViewProperty: Boolean = true

    /** 逻辑删除默认配置 */
    var logicalDeletedAnnotation: String = "@LogicalDeleted(\"true\")"

    /** 是否生成 Table 注释 */
    var tableAnnotation: Boolean = true

    /** 是否生成 Column 注释 */
    var columnAnnotation: Boolean = true

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

    val tablePrefixes: List<String>
        get() = tablePrefix.split(",").map { it.trim() }

    val tableSuffixes: List<String>
        get() = tableSuffix.split(",").map { it.trim() }

    val tableCommentPrefixes: List<String>
        get() = tableCommentPrefix.split(",").map { it.trim() }

    val tableCommentSuffixes: List<String>
        get() = tableCommentSuffix.split(",").map { it.trim() }

    val columnPrefixes: List<String>
        get() = columnPrefix.split(",").map { it.trim() }

    val columnSuffixes: List<String>
        get() = columnSuffix.split(",").map { it.trim() }

    val columnCommentPrefixes: List<String>
        get() = columnCommentPrefix.split(",").map { it.trim() }

    val columnCommentSuffixes: List<String>
        get() = columnCommentSuffix.split(",").map { it.trim() }
}
