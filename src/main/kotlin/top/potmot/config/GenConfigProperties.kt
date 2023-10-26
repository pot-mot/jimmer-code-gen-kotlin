package top.potmot.config

import top.potmot.enumeration.GenLanguage

data class GenConfigProperties(
    /** 分隔符 */
    val separator: String? = null,

    /** 语言，java/kotlin */
    val language: GenLanguage? = null,

    /** 作者  */
    val author: String? = null,

    /** 生成 tableDefine 时携带外键 */
    val tableDefineWithFk: Boolean? = null,

    /** 逻辑删除默认配置 */
    val logicalDeletedAnnotation: String? = null,

    /**
     * 表名前缀
     * 关联匹配与实体名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val tablePrefix: String? = null,

    /**
     * 表名后缀
     * 关联匹配与实体名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val tableSuffix: String? = null,

    /**
     * 表注释前缀
     * 实体注释生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val tableCommentPrefix: String? = null,

    /**
     * 表注释后缀
     * 实体注释生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val tableCommentSuffix: String? = null,

    /**
     * 列名前缀
     * 属性名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val columnPrefix: String? = null,

    /**
     * 列名后缀
     * 属性名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val columnSuffix: String? = null,

    /**
     * 列注释前缀
     * 属性名生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val columnCommentPrefix: String? = null,

    /**
     * 列注释后缀
     * 属性注释生成时生效
     * 配置文件中由 ',' 进行分割
     */
    val columnCommentSuffix: String? = null,
)
