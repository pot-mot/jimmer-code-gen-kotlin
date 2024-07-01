package top.potmot.entity.dto

import com.fasterxml.jackson.`annotation`.JsonProperty
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic
import org.babyfish.jimmer.View
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.fetcher.DtoMetadata
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.GenModel
import top.potmot.entity.`by`

open class MutableGenConfig(
    /**
     * 语言
     */
    @JsonProperty(required = true)
    var language: GenLanguage,
    /**
     * 数据源类型
     */
    @JsonProperty(required = true)
    var dataSourceType: DataSourceType,
    /**
     * 作者
     */
    @JsonProperty(required = true)
    var author: String,
    /**
     * 包路径
     */
    @JsonProperty(required = true)
    var packagePath: String,
    /**
     * 表路径
     */
    @JsonProperty(required = true)
    var tablePath: String,
    /**
     * 数据库命名策略
     */
    @JsonProperty(required = true)
    var databaseNamingStrategy: DatabaseNamingStrategyType,
    /**
     * 启用真实外键
     */
    @JsonProperty(required = true)
    var realFk: Boolean,
    /**
     * 生成 IdView 属性
     */
    @JsonProperty(required = true)
    var idViewProperty: Boolean,
    /**
     * 逻辑删除注解
     */
    @JsonProperty(required = true)
    var logicalDeletedAnnotation: String,
    /**
     * 生成 Table 注解
     */
    @JsonProperty(required = true)
    var tableAnnotation: Boolean,
    /**
     * 生成 Column 注解
     */
    @JsonProperty(required = true)
    var columnAnnotation: Boolean,
    /**
     * 生成 JoinTable 注解
     */
    @JsonProperty(required = true)
    var joinTableAnnotation: Boolean,
    /**
     * 生成 JoinColumn 注解
     */
    @JsonProperty(required = true)
    var joinColumnAnnotation: Boolean,
    /**
     * 转换实体时移除的表名前缀
     */
    @JsonProperty(required = true)
    var tableNamePrefixes: String,
    /**
     * 转换实体时移除的表名后缀
     */
    @JsonProperty(required = true)
    var tableNameSuffixes: String,
    /**
     * 转换实体时移除的表注释前缀
     */
    @JsonProperty(required = true)
    var tableCommentPrefixes: String,
    /**
     * 转换实体时移除的表注释后缀
     */
    @JsonProperty(required = true)
    var tableCommentSuffixes: String,
    /**
     * 转换属性时移除的列名前缀
     */
    @JsonProperty(required = true)
    var columnNamePrefixes: String,
    /**
     * 转换属性时移除的列名后缀
     */
    @JsonProperty(required = true)
    var columnNameSuffixes: String,
    /**
     * 转换属性时移除的列注释前缀
     */
    @JsonProperty(required = true)
    var columnCommentPrefixes: String,
    /**
     * 转换属性时移除的列注释后缀
     */
    @JsonProperty(required = true)
    var columnCommentSuffixes: String,
) : View<GenModel> {
    constructor(base: GenModel) : this(
        base.language,
        base.dataSourceType,
        base.author,
        base.packagePath,
        base.tablePath,
        base.databaseNamingStrategy,
        base.realFk,
        base.idViewProperty,
        base.logicalDeletedAnnotation,
        base.tableAnnotation,
        base.columnAnnotation,
        base.joinTableAnnotation,
        base.joinColumnAnnotation,
        base.tableNamePrefixes,
        base.tableNameSuffixes,
        base.tableCommentPrefixes,
        base.tableCommentSuffixes,
        base.columnNamePrefixes,
        base.columnNameSuffixes,
        base.columnCommentPrefixes,
        base.columnCommentSuffixes)

    override fun toEntity(): GenModel = new(GenModel::class).by {
        val that = this@MutableGenConfig
        language = that.language
        dataSourceType = that.dataSourceType
        author = that.author
        packagePath = that.packagePath
        tablePath = that.tablePath
        databaseNamingStrategy = that.databaseNamingStrategy
        realFk = that.realFk
        idViewProperty = that.idViewProperty
        logicalDeletedAnnotation = that.logicalDeletedAnnotation
        tableAnnotation = that.tableAnnotation
        columnAnnotation = that.columnAnnotation
        joinTableAnnotation = that.joinTableAnnotation
        joinColumnAnnotation = that.joinColumnAnnotation
        tableNamePrefixes = that.tableNamePrefixes
        tableNameSuffixes = that.tableNameSuffixes
        tableCommentPrefixes = that.tableCommentPrefixes
        tableCommentSuffixes = that.tableCommentSuffixes
        columnNamePrefixes = that.columnNamePrefixes
        columnNameSuffixes = that.columnNameSuffixes
        columnCommentPrefixes = that.columnCommentPrefixes
        columnCommentSuffixes = that.columnCommentSuffixes
    }

    fun copy(
        language: GenLanguage = this.language,
        dataSourceType: DataSourceType = this.dataSourceType,
        author: String = this.author,
        packagePath: String = this.packagePath,
        tablePath: String = this.tablePath,
        databaseNamingStrategy: DatabaseNamingStrategyType = this.databaseNamingStrategy,
        realFk: Boolean = this.realFk,
        idViewProperty: Boolean = this.idViewProperty,
        logicalDeletedAnnotation: String = this.logicalDeletedAnnotation,
        tableAnnotation: Boolean = this.tableAnnotation,
        columnAnnotation: Boolean = this.columnAnnotation,
        joinTableAnnotation: Boolean = this.joinTableAnnotation,
        joinColumnAnnotation: Boolean = this.joinColumnAnnotation,
        tableNamePrefixes: String = this.tableNamePrefixes,
        tableNameSuffixes: String = this.tableNameSuffixes,
        tableCommentPrefixes: String = this.tableCommentPrefixes,
        tableCommentSuffixes: String = this.tableCommentSuffixes,
        columnNamePrefixes: String = this.columnNamePrefixes,
        columnNameSuffixes: String = this.columnNameSuffixes,
        columnCommentPrefixes: String = this.columnCommentPrefixes,
        columnCommentSuffixes: String = this.columnCommentSuffixes,
    ): MutableGenConfig = MutableGenConfig(language, dataSourceType, author, packagePath,
            tablePath, databaseNamingStrategy, realFk, idViewProperty, logicalDeletedAnnotation,
            tableAnnotation, columnAnnotation, joinTableAnnotation, joinColumnAnnotation,
            tableNamePrefixes, tableNameSuffixes, tableCommentPrefixes, tableCommentSuffixes,
            columnNamePrefixes, columnNameSuffixes, columnCommentPrefixes, columnCommentSuffixes)

    override fun hashCode(): Int {
        var __hash = language.hashCode()
        __hash = 31 * __hash + dataSourceType.hashCode()
        __hash = 31 * __hash + author.hashCode()
        __hash = 31 * __hash + packagePath.hashCode()
        __hash = 31 * __hash + tablePath.hashCode()
        __hash = 31 * __hash + databaseNamingStrategy.hashCode()
        __hash = 31 * __hash + realFk.hashCode()
        __hash = 31 * __hash + idViewProperty.hashCode()
        __hash = 31 * __hash + logicalDeletedAnnotation.hashCode()
        __hash = 31 * __hash + tableAnnotation.hashCode()
        __hash = 31 * __hash + columnAnnotation.hashCode()
        __hash = 31 * __hash + joinTableAnnotation.hashCode()
        __hash = 31 * __hash + joinColumnAnnotation.hashCode()
        __hash = 31 * __hash + tableNamePrefixes.hashCode()
        __hash = 31 * __hash + tableNameSuffixes.hashCode()
        __hash = 31 * __hash + tableCommentPrefixes.hashCode()
        __hash = 31 * __hash + tableCommentSuffixes.hashCode()
        __hash = 31 * __hash + columnNamePrefixes.hashCode()
        __hash = 31 * __hash + columnNameSuffixes.hashCode()
        __hash = 31 * __hash + columnCommentPrefixes.hashCode()
        __hash = 31 * __hash + columnCommentSuffixes.hashCode()
        return __hash
    }

    override fun equals(other: Any?): Boolean {
        val __other = other as? MutableGenConfig ?: return false
        return language == __other.language&&
        dataSourceType == __other.dataSourceType&&
        author == __other.author&&
        packagePath == __other.packagePath&&
        tablePath == __other.tablePath&&
        databaseNamingStrategy == __other.databaseNamingStrategy&&
        realFk == __other.realFk&&
        idViewProperty == __other.idViewProperty&&
        logicalDeletedAnnotation == __other.logicalDeletedAnnotation&&
        tableAnnotation == __other.tableAnnotation&&
        columnAnnotation == __other.columnAnnotation&&
        joinTableAnnotation == __other.joinTableAnnotation&&
        joinColumnAnnotation == __other.joinColumnAnnotation&&
        tableNamePrefixes == __other.tableNamePrefixes&&
        tableNameSuffixes == __other.tableNameSuffixes&&
        tableCommentPrefixes == __other.tableCommentPrefixes&&
        tableCommentSuffixes == __other.tableCommentSuffixes&&
        columnNamePrefixes == __other.columnNamePrefixes&&
        columnNameSuffixes == __other.columnNameSuffixes&&
        columnCommentPrefixes == __other.columnCommentPrefixes&&
        columnCommentSuffixes == __other.columnCommentSuffixes
    }

    override fun toString(): String = "GenConfig(" +
        "language=" + language +
        ", dataSourceType=" + dataSourceType +
        ", author=" + author +
        ", packagePath=" + packagePath +
        ", tablePath=" + tablePath +
        ", databaseNamingStrategy=" + databaseNamingStrategy +
        ", realFk=" + realFk +
        ", idViewProperty=" + idViewProperty +
        ", logicalDeletedAnnotation=" + logicalDeletedAnnotation +
        ", tableAnnotation=" + tableAnnotation +
        ", columnAnnotation=" + columnAnnotation +
        ", joinTableAnnotation=" + joinTableAnnotation +
        ", joinColumnAnnotation=" + joinColumnAnnotation +
        ", tableNamePrefixes=" + tableNamePrefixes +
        ", tableNameSuffixes=" + tableNameSuffixes +
        ", tableCommentPrefixes=" + tableCommentPrefixes +
        ", tableCommentSuffixes=" + tableCommentSuffixes +
        ", columnNamePrefixes=" + columnNamePrefixes +
        ", columnNameSuffixes=" + columnNameSuffixes +
        ", columnCommentPrefixes=" + columnCommentPrefixes +
        ", columnCommentSuffixes=" + columnCommentSuffixes +
        ")"

    companion object {
        @JvmStatic
        val METADATA: DtoMetadata<GenModel, MutableGenConfig> =
            DtoMetadata<GenModel, MutableGenConfig>(
                newFetcher(GenModel::class).by {
                    language()
                    dataSourceType()
                    author()
                    packagePath()
                    tablePath()
                    databaseNamingStrategy()
                    realFk()
                    idViewProperty()
                    logicalDeletedAnnotation()
                    tableAnnotation()
                    columnAnnotation()
                    joinTableAnnotation()
                    joinColumnAnnotation()
                    tableNamePrefixes()
                    tableNameSuffixes()
                    tableCommentPrefixes()
                    tableCommentSuffixes()
                    columnNamePrefixes()
                    columnNameSuffixes()
                    columnCommentPrefixes()
                    columnCommentSuffixes()
                }
            ) {
                MutableGenConfig(it)
            }

    }
}
