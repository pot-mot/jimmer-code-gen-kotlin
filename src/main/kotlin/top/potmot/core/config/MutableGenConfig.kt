package top.potmot.core.config

import java.sql.Types
import top.potmot.entity.GenModel
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType

open class MutableGenConfig(
    /**
     * 语言
     */
    var language: GenLanguage = GenLanguage.KOTLIN,

    /**
     * 数据源类型
     */
    var dataSourceType: DataSourceType = DataSourceType.PostgreSQL,

    /**
     * 视图类型
     */
    var viewType: ViewType = ViewType.VUE3_ELEMENT_PLUS,

    /**
     * 作者
     */
    var author: String = "",

    /**
     * 包路径
     */
    var packagePath: String = "com.example",

    /**
     * 表路径
     */
    var tablePath: String = "",

    /**
     * 数据库命名策略
     */
    var databaseNamingStrategy: DatabaseNamingStrategyType = DatabaseNamingStrategyType.RAW,

    /**
     * 启用真实外键
     */
    var realFk: Boolean = true,

    /**
     * 生成 IdView 属性
     */
    var idViewProperty: Boolean = true,

    /**
     * 默认 Id 类型
     */
    var defaultIdType: Int = Types.INTEGER,

    /**
     * Id 注解
     */
    var generatedIdAnnotation: AnnotationWithImports = AnnotationWithImports(
        imports = listOf(
            "org.babyfish.jimmer.sql.GeneratedValue",
            "org.babyfish.jimmer.sql.GenerationType",
        ),
        annotations = listOf(
            "@GeneratedValue(strategy = GenerationType.IDENTITY)"
        )
    ),

    /**
     * 逻辑删除注解
     */
    var logicalDeletedAnnotation: AnnotationWithImports = AnnotationWithImports(
        imports = listOf(
            "org.babyfish.jimmer.sql.LogicalDeleted",
        ),
        annotations = listOf(
            "@LogicalDeleted"
        )
    ),

    /**
     * 在前端视图中进行日期格式化
     */
    var dateTimeFormatInView: Boolean = true,

    /**
     * 生成 Table 注解
     */
    var tableAnnotation: Boolean = true,

    /**
     * 生成 Column 注解
     */
    var columnAnnotation: Boolean = true,

    /**
     * 生成 JoinTable 注解
     */
    var joinTableAnnotation: Boolean = true,

    /**
     * 生成 JoinColumn 注解
     */
    var joinColumnAnnotation: Boolean = true,

    /**
     * 转换实体时移除的表名前缀
     */
    var tableNamePrefixes: String = "",

    /**
     * 转换实体时移除的表名后缀
     */
    var tableNameSuffixes: String = "",

    /**
     * 转换实体时移除的表注释前缀
     */
    var tableCommentPrefixes: String = "",

    /**
     * 转换实体时移除的表注释后缀
     */
    var tableCommentSuffixes: String = "",

    /**
     * 转换属性时移除的列名前缀
     */
    var columnNamePrefixes: String = "",

    /**
     * 转换属性时移除的列名后缀
     */
    var columnNameSuffixes: String = "",

    /**
     * 转换属性时移除的列注释前缀
     */
    var columnCommentPrefixes: String = "",

    /**
     * 转换属性时移除的列注释后缀
     */
    var columnCommentSuffixes: String = "",
) {
    fun toEntity(data: MutableGenConfig = this) = GenModel {
        this.language = data.language
        this.dataSourceType = data.dataSourceType
        this.viewType = data.viewType
        this.author = data.author
        this.packagePath = data.packagePath
        this.tablePath = data.tablePath
        this.databaseNamingStrategy = data.databaseNamingStrategy
        this.realFk = data.realFk
        this.idViewProperty = data.idViewProperty
        this.defaultIdType = data.defaultIdType
        this.generatedIdAnnotation = data.generatedIdAnnotation
        this.logicalDeletedAnnotation = data.logicalDeletedAnnotation
        this.dateTimeFormatInView = data.dateTimeFormatInView
        this.tableAnnotation = data.tableAnnotation
        this.columnAnnotation = data.columnAnnotation
        this.joinTableAnnotation = data.joinTableAnnotation
        this.joinColumnAnnotation = data.joinColumnAnnotation
        this.tableNamePrefixes = data.tableNamePrefixes
        this.tableNameSuffixes = data.tableNameSuffixes
        this.tableCommentPrefixes = data.tableCommentPrefixes
        this.tableCommentSuffixes = data.tableCommentSuffixes
        this.columnNamePrefixes = data.columnNamePrefixes
        this.columnNameSuffixes = data.columnNameSuffixes
        this.columnCommentPrefixes = data.columnCommentPrefixes
        this.columnCommentSuffixes = data.columnCommentSuffixes
    }

    fun toProperties() =
        GenConfigProperties(
            language = language,
            dataSourceType = dataSourceType,
            viewType = viewType,
            author = author,
            packagePath = packagePath,
            tablePath = tablePath,
            databaseNamingStrategy = databaseNamingStrategy,
            realFk = realFk,
            idViewProperty = idViewProperty,
            defaultIdType = defaultIdType,
            generatedIdAnnotation = generatedIdAnnotation,
            logicalDeletedAnnotation = logicalDeletedAnnotation,
            dateTimeFormatInView = dateTimeFormatInView,
            tableAnnotation = tableAnnotation,
            columnAnnotation = columnAnnotation,
            joinTableAnnotation = joinTableAnnotation,
            joinColumnAnnotation = joinColumnAnnotation,
            tableNamePrefixes = tableNamePrefixes,
            tableNameSuffixes = tableNameSuffixes,
            tableCommentPrefixes = tableCommentPrefixes,
            tableCommentSuffixes = tableCommentSuffixes,
            columnNamePrefixes = columnNamePrefixes,
            columnNameSuffixes = columnNameSuffixes,
            columnCommentPrefixes = columnCommentPrefixes,
            columnCommentSuffixes = columnCommentSuffixes,
        )

    fun toConfig() =
        GenConfig(
            language = language,
            dataSourceType = dataSourceType,
            viewType = viewType,
            author = author,
            packagePath = packagePath,
            tablePath = tablePath,
            databaseNamingStrategy = databaseNamingStrategy,
            realFk = realFk,
            idViewProperty = idViewProperty,
            defaultIdType = defaultIdType,
            generatedIdAnnotation = generatedIdAnnotation,
            logicalDeletedAnnotation = logicalDeletedAnnotation,
            dateTimeFormatInView = dateTimeFormatInView,
            tableAnnotation = tableAnnotation,
            columnAnnotation = columnAnnotation,
            joinTableAnnotation = joinTableAnnotation,
            joinColumnAnnotation = joinColumnAnnotation,
            tableNamePrefixes = tableNamePrefixes,
            tableNameSuffixes = tableNameSuffixes,
            tableCommentPrefixes = tableCommentPrefixes,
            tableCommentSuffixes = tableCommentSuffixes,
            columnNamePrefixes = columnNamePrefixes,
            columnNameSuffixes = columnNameSuffixes,
            columnCommentPrefixes = columnCommentPrefixes,
            columnCommentSuffixes = columnCommentSuffixes,
        )

    fun merge(properties: GenConfigProperties) {
        properties.language?.let { language = it }
        properties.dataSourceType?.let { dataSourceType = it }
        properties.viewType?.let { viewType = it }
        properties.author?.let { author = it }
        properties.packagePath?.let { packagePath = it }
        properties.tablePath?.let { tablePath = it }
        properties.databaseNamingStrategy?.let { databaseNamingStrategy = it }
        properties.realFk?.let { realFk = it }
        properties.idViewProperty?.let { idViewProperty = it }
        properties.defaultIdType?.let { defaultIdType = it }
        properties.generatedIdAnnotation?.let { generatedIdAnnotation = it }
        properties.logicalDeletedAnnotation?.let { logicalDeletedAnnotation = it }
        properties.dateTimeFormatInView?.let { dateTimeFormatInView = it }
        properties.tableAnnotation?.let { tableAnnotation = it }
        properties.columnAnnotation?.let { columnAnnotation = it }
        properties.joinTableAnnotation?.let { joinTableAnnotation = it }
        properties.joinColumnAnnotation?.let { joinColumnAnnotation = it }
        properties.tableNamePrefixes?.let { tableNamePrefixes = it }
        properties.tableNameSuffixes?.let { tableNameSuffixes = it }
        properties.tableCommentPrefixes?.let { tableCommentPrefixes = it }
        properties.tableCommentSuffixes?.let { tableCommentSuffixes = it }
        properties.columnNamePrefixes?.let { columnNamePrefixes = it }
        properties.columnNameSuffixes?.let { columnNameSuffixes = it }
        properties.columnCommentPrefixes?.let { columnCommentPrefixes = it }
        properties.columnCommentSuffixes?.let { columnCommentSuffixes = it }
    }
}