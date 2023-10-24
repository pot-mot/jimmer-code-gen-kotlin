package top.potmot.config

import top.potmot.enumeration.GenLanguage

data class GenConfigProperties(
    /** 分隔符 */
    val separator: String? = null,

    /** 语言，java/kotlin */
    val language: GenLanguage? = null,

    /** 作者  */
    val author: String? = null,

    /** 逻辑删除默认配置 */
    val logicalDeletedAnnotation: String? = null,

    /**
     * 表名前缀
     * 自动匹配关联与实体名生成时生效
     * 配置文件中由 , 进行分割 */
    val tablePrefix: String? = null,

    /**
     * 表名后缀
     * 自动匹配关联与实体名生成时生效
     * 配置文件中由 , 进行分割 */
    val tableSuffix: String? = null,

    /** 生成实体时是否依照 tablePrefix 进行前缀移除 */
    val removeTablePrefix: Boolean? = null,

    /** 生成实体时是否依照 tableSuffix 进行后缀移除 */
    val removeTableSuffix: Boolean? = null,

    /**
     * 列名前缀
     * 属性体名生成时生效
     * 配置文件中由 , 进行分割 */
    val columnPrefix: String? = null,

    /**
     * 列名后缀
     * 属性体名生成时生效
     * 配置文件中由 , 进行分割 */
    val columnSuffix: String? = null,

    /** 生成属性时是否依照 columnPrefix 进行前缀移除 */
    val removeColumnPrefix: Boolean? = null,

    /** 生成属性时是否依照 columnSuffix 进行后缀移除 */
    val removeColumnSuffix: Boolean? = null,
)
