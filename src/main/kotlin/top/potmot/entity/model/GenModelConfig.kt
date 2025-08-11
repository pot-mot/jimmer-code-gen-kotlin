package top.potmot.entity.model

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 模型配置
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_model_config")
interface GenModelConfig {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 模型
     */
    @Key
    @OneToOne
    @JoinColumn(
        name = "model_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val model: GenModel

    /**
     * 模型 ID View
     */
    @IdView("model")
    val modelId: Int

    /**
     * 基础包路径
     */
    @Column(name = "base_package_path")
    @get:Length(max = 500)
    val basePackagePath: String

    /**
     * 基础表路径
     */
    @Column(name = "base_table_path")
    @get:Length(max = 500)
    val baseTablePath: String

    /**
     * 默认脱钩行为
     */
    @Column(name = "default_dissociate_action")
    @get:Length(max = 500)
    val defaultDissociateAction: String

    /**
     * 默认使用真实外键
     */
    @Column(name = "default_use_real_fk")
    val defaultUseRealFk: Boolean

    /**
     * 默认ID类型
     */
    @Column(name = "default_id_type")
    @get:Max(value = 2147483647, message = "默认ID类型不可大于2147483647")
    @get:Min(value = 0, message = "默认ID类型不可小于0")
    val defaultIdType: Int

    /**
     * 默认ID生成注解
     */
    @Column(name = "default_generated_id_annotation")
    @get:Length(max = 500)
    val defaultGeneratedIdAnnotation: String

    /**
     * 默认逻辑删除注解
     */
    @Column(name = "default_logical_deleted_annotation")
    @get:Length(max = 500)
    val defaultLogicalDeletedAnnotation: String

    /**
     * 生成 Table 注解
     */
    @Column(name = "generate_table_annotation")
    val erateTableAnnotation: Boolean

    /**
     * 生成 Column 注解
     */
    @Column(name = "generate_column_annotation")
    val erateColumnAnnotation: Boolean

    /**
     * 表转换实体时移除的表名前缀
     */
    @Column(name = "table_to_entity_removed_name_prefixes")
    @get:Length(max = 500)
    val tableToEntityRemovedNamePrefixes: String

    /**
     * 表转换实体时移除的表名后缀
     */
    @Column(name = "table_to_entity_removed_name_suffixes")
    @get:Length(max = 500)
    val tableToEntityRemovedNameSuffixes: String

    /**
     * 表转换实体时移除的表注释前缀
     */
    @Column(name = "table_to_entity_removed_comment_prefixes")
    @get:Length(max = 500)
    val tableToEntityRemovedCommentPrefixes: String

    /**
     * 表转换实体时移除的表注释后缀
     */
    @Column(name = "table_to_entity_removed_comment_suffixes")
    @get:Length(max = 500)
    val tableToEntityRemovedCommentSuffixes: String

    /**
     * 列转换属性时移除的列名前缀
     */
    @Column(name = "column_to_property_removed_name_prefixes")
    @get:Length(max = 500)
    val columnToPropertyRemovedNamePrefixes: String

    /**
     * 转换属性时移除的列名后缀
     */
    @Column(name = "column_to_property_removed_name_suffixes")
    @get:Length(max = 500)
    val columnToPropertyRemovedNameSuffixes: String

    /**
     * 转换属性时移除的列注释前缀
     */
    @Column(name = "column_to_property_removed_comment_prefixes")
    @get:Length(max = 500)
    val columnToPropertyRemovedCommentPrefixes: String

    /**
     * 转换属性时移除的列注释后缀
     */
    @Column(name = "column_to_property_removed_comment_suffixes")
    @get:Length(max = 500)
    val columnToPropertyRemovedCommentSuffixes: String

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String
}
