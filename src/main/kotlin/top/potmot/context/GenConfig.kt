package top.potmot.context

import top.potmot.config.GlobalGenConfig
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage

data class GenConfig(
    /** 数据源类型 */
    var dataSourceType: DataSourceType,

    /** 语言，java/kotlin */
    var language: GenLanguage,

    /** 作者  */
    var author: String,

    /** 默认包路径  */
    var packagePath: String,

    /** 默认启用小写命名 */
    var lowerCaseName: Boolean,

    /** 默认真实外键 */
    var realFk: Boolean,

    /** 是否生成 IdView 注释 */
    var idViewProperty: Boolean,

    /** 逻辑删除默认配置 */
    var logicalDeletedAnnotation: String,

    /** 是否生成 Table 注释 */
    var tableAnnotation: Boolean,

    /** 是否生成 Column 注释 */
    var columnAnnotation: Boolean,

    /** 是否生成 JoinTable 注释 */
    var joinTableAnnotation: Boolean,

    /** 是否生成 JoinColumn 注释 */
    var joinColumnAnnotation: Boolean,

    /** 表名前缀 */
    var tablePrefix: String,

    /** 表名后缀 */
    var tableSuffix: String,

    /** 表注释前缀 */
    var tableCommentPrefix: String,

    /** 表注释后缀 */
    var tableCommentSuffix: String,

    /** 列名前缀 */
    var columnPrefix: String,

    /** 列名后缀 */
    var columnSuffix: String,

    /** 列注释前缀 */
    var columnCommentPrefix: String,

    /** 列注释后缀 */
    var columnCommentSuffix: String,
) {
    val tablePrefixes: List<String>
        get() = GlobalGenConfig.tablePrefix.split(",").map { it.trim() }

    val tableSuffixes: List<String>
        get() = GlobalGenConfig.tableSuffix.split(",").map { it.trim() }

    val tableCommentPrefixes: List<String>
        get() = GlobalGenConfig.tableCommentPrefix.split(",").map { it.trim() }

    val tableCommentSuffixes: List<String>
        get() = GlobalGenConfig.tableCommentSuffix.split(",").map { it.trim() }

    val columnPrefixes: List<String>
        get() = GlobalGenConfig.columnPrefix.split(",").map { it.trim() }

    val columnSuffixes: List<String>
        get() = GlobalGenConfig.columnSuffix.split(",").map { it.trim() }

    val columnCommentPrefixes: List<String>
        get() = GlobalGenConfig.columnCommentPrefix.split(",").map { it.trim() }

    val columnCommentSuffixes: List<String>
        get() = GlobalGenConfig.columnCommentSuffix.split(",").map { it.trim() }
}
