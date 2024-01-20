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
    /** 同步转换实体 */
    var syncConvertEntity: Boolean = true

    /** 数据源类型 */
    var dataSourceType: DataSourceType = DataSourceType.MySQL

    /** 语言，java/kotlin */
    var language: GenLanguage = GenLanguage.KOTLIN

    /** 真实外键 */
    var realFk: Boolean = true

    /** 启用小写命名 */
    var lowerCaseName: Boolean = true

    /** 作者  */
    var author: String = ""

    /** 包路径  */
    var packagePath: String = "com.example"

    /** 表路径 */
    var tablePath: String = ""

    /** 生成 IdView 属性 */
    var idViewProperty: Boolean = true

    /** 逻辑删除注解 */
    var logicalDeletedAnnotation: String = "@LogicalDeleted(\"true\")"

    /** 生成 Table 注释 */
    var tableAnnotation: Boolean = true

    /** 生成 Column 注释 */
    var columnAnnotation: Boolean = true

    /** 生成 JoinTable 注释 */
    var joinTableAnnotation: Boolean = true

    /** 生成 JoinColumn 注释 */
    var joinColumnAnnotation: Boolean = true

    /**
     * 表名前缀
     * 关联匹配 与 实体名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableNamePrefixes: String = ""

    /**
     * 表名后缀
     * 关联匹配 与 实体名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableNameSuffixes: String = ""

    /**
     * 表注释前缀
     * 实体注释生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableCommentPrefixes: String = ""

    /**
     * 表注释后缀
     * 实体注释生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableCommentSuffixes: String = ""

    /**
     * 列名前缀
     * 属性名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnNamePrefixes: String = ""

    /**
     * 列名后缀
     * 属性名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnNameSuffixes: String = ""

    /**
     * 列注释前缀
     * 属性名生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnCommentPrefixes: String = ""

    /**
     * 列注释后缀
     * 属性注释生成 时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnCommentSuffixes: String = ""
}
