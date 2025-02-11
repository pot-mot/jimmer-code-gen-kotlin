package top.potmot.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.base.BaseEntity
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.enumeration.ViewType

/**
 * 生成模型
 *
 * @author
 * @since 2023-11-02 20:13:10
 */
@Entity
@Table(name = "gen_model")
interface GenModel : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 名称
     */
    val name: String

    /**
     * Graph 数据
     */
    val graphData: String

    /**
     * 语言
     */
    val language: GenLanguage

    /**
     * 数据源类型
     */
    val dataSourceType: DataSourceType

    /**
     * 视图类型
     */
    val viewType: ViewType

    /**
     * 作者
     */
    val author: String

    /**
     * 包路径
     */
    val packagePath: String

    /**
     * 表路径
     */
    val tablePath: String

    /**
     * 数据库命名策略
     */
    val databaseNamingStrategy: DatabaseNamingStrategyType

    /**
     * 启用真实外键
     */
    val realFk: Boolean

    /**
     * 生成 IdView 属性
     */
    val idViewProperty: Boolean

    /**
     * 默认 ID 类型
     */
    val defaultIdType: Int

    /**
     * 生成 ID 注解
     */
    val generatedIdAnnotation: AnnotationWithImports

    /**
     * 逻辑删除注解
     */
    val logicalDeletedAnnotation: AnnotationWithImports

    /**
     * 在前端视图中进行日期格式化
     */
    val dateTimeFormatInView: Boolean

    /**
     * 生成 Table 注解
     */
    val tableAnnotation: Boolean

    /**
     * 生成 Column 注解
     */
    val columnAnnotation: Boolean

    /**
     * 生成 JoinTable 注解
     */
    val joinTableAnnotation: Boolean

    /**
     * 生成 JoinColumn 注解
     */
    val joinColumnAnnotation: Boolean

    /**
     * 转换实体时移除的表名前缀
     */
    val tableNamePrefixes: String

    /**
     * 转换实体时移除的表名后缀
     */
    val tableNameSuffixes: String

    /**
     * 转换实体时移除的表注释前缀
     */
    val tableCommentPrefixes: String

    /**
     * 转换实体时移除的表注释后缀
     */
    val tableCommentSuffixes: String

    /**
     * 转换属性时移除的列名前缀
     */
    val columnNamePrefixes: String

    /**
     * 转换属性时移除的列名后缀
     */
    val columnNameSuffixes: String

    /**
     * 转换属性时移除的列注释前缀
     */
    val columnCommentPrefixes: String

    /**
     * 转换属性时移除的列注释后缀
     */
    val columnCommentSuffixes: String

    /**
     * 备注
     */
    val remark: String

    /**
     * 表
     */
    @OneToMany(mappedBy = "model", orderedProps = [OrderedProp("name")])
    val tables: List<GenTable>

    /**
     * 表 ID 视图
     */
    @IdView("tables")
    val tableIds: List<Long>

    /**
     * 关联
     */
    @OneToMany(mappedBy = "model", orderedProps = [OrderedProp("name")])
    val associations: List<GenAssociation>

    /**
     * 关联 ID 视图
     */
    @IdView("associations")
    val associationIds: List<Long>

    /**
     * 实体
     */
    @OneToMany(mappedBy = "model", orderedProps = [OrderedProp("name")])
    val entities: List<GenEntity>

    /**
     * 实体 ID 视图
     */
    @IdView("entities")
    val entityIds: List<Long>

    /**
     * 枚举
     */
    @OneToMany(mappedBy = "model", orderedProps = [OrderedProp("name")])
    val enums: List<GenEnum>

    /**
     * 枚举 ID 视图
     */
    @IdView("enums")
    val enumIds: List<Long>
}
