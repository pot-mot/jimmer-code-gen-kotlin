package top.potmot.context

import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage

data class GenConfigProperties(
    /** 数据源类型 */
    var dataSourceType: DataSourceType? = null,

    /** 语言，java/kotlin */
    var language: GenLanguage? = null,

    /** 作者  */
    var author: String? = null,

    /** 默认包路径  */
    var packagePath: String? = null,

    /** 默认启用小写命名 */
    var lowerCaseName: Boolean? = null,

    /** 默认真实外键 */
    var realFk: Boolean? = null,

    /** 是否生成 IdView 注释 */
    var idViewProperty: Boolean? = null,

    /** 逻辑删除默认配置 */
    var logicalDeletedAnnotation: String? = null,

    /** 是否生成 Table 注释 */
    var tableAnnotation: Boolean? = null,

    /** 是否生成 Column 注释 */
    var columnAnnotation: Boolean? = null,

    /** 是否生成 JoinTable 注释 */
    var joinTableAnnotation: Boolean? = null,

    /** 是否生成 JoinColumn 注释 */
    var joinColumnAnnotation: Boolean? = null,

    /**
     * 表名前缀
     * 关联匹配与实体名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var tablePrefix: String? = null,

    /**
     * 表名后缀
     * 关联匹配与实体名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableSuffix: String? = null,

    /**
     * 表注释前缀
     * 实体注释生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableCommentPrefix: String? = null,

    /**
     * 表注释后缀
     * 实体注释生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var tableCommentSuffix: String? = null,

    /**
     * 列名前缀
     * 属性名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnPrefix: String? = null,

    /**
     * 列名后缀
     * 属性名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnSuffix: String? = null,

    /**
     * 列注释前缀
     * 属性名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnCommentPrefix: String? = null,

    /**
     * 列注释后缀
     * 属性注释生成时生效
     * 配置文件中由 ',' 进行分割
     */
    var columnCommentSuffix: String? = null,
)
