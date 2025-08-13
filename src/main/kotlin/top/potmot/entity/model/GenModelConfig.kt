package top.potmot.entity.model

import jakarta.validation.Valid
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
import org.hibernate.validator.constraints.Length

/**
 * 模型配置
 * 
 * @author potmot
 */
@Entity
interface GenModelConfig {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @get:Length(max = 500)
    val basePackagePath: String

    /**
     * 基础表路径
     */
    @get:Length(max = 500)
    val baseTablePath: String

    /**
     * 默认脱钩行为
     */
    @get:Length(max = 500)
    val defaultDissociateAction: String

    /**
     * 默认使用真实外键
     */
    val defaultUseRealFk: Boolean

    /**
     * 默认ID类型
     */
    val defaultIdType: Int

    /**
     * 默认ID生成注解
     */
    @get:Length(max = 500)
    val defaultGeneratedIdAnnotation: String

    /**
     * 默认逻辑删除注解
     */
    @get:Length(max = 500)
    val defaultLogicalDeletedAnnotation: String

    /**
     * 生成 Table 注解
     */
    val generateTableAnnotation: Boolean

    /**
     * 生成 Column 注解
     */
    val generateColumnAnnotation: Boolean

    /**
     * 表转换实体时移除的表名前缀
     */
    @get:Length(max = 500)
    val tableToEntityRemovedNamePrefixes: String

    /**
     * 表转换实体时移除的表名后缀
     */
    @get:Length(max = 500)
    val tableToEntityRemovedNameSuffixes: String

    /**
     * 表转换实体时移除的表注释前缀
     */
    @get:Length(max = 500)
    val tableToEntityRemovedCommentPrefixes: String

    /**
     * 表转换实体时移除的表注释后缀
     */
    @get:Length(max = 500)
    val tableToEntityRemovedCommentSuffixes: String

    /**
     * 列转换属性时移除的列名前缀
     */
    @get:Length(max = 500)
    val columnToPropertyRemovedNamePrefixes: String

    /**
     * 转换属性时移除的列名后缀
     */
    @get:Length(max = 500)
    val columnToPropertyRemovedNameSuffixes: String

    /**
     * 转换属性时移除的列注释前缀
     */
    @get:Length(max = 500)
    val columnToPropertyRemovedCommentPrefixes: String

    /**
     * 转换属性时移除的列注释后缀
     */
    @get:Length(max = 500)
    val columnToPropertyRemovedCommentSuffixes: String

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String
}
