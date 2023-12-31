package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.AssociationType
import top.potmot.model.base.BaseEntity

/**
 * 生成关联实体类
 *
 * @author potmot
 * @since 2023-08-12 10:47:36
 */
@Entity
@Table(name = "jimmer_code_gen.gen_association")
interface GenAssociation : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 模型
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val model: GenModel?

    /**
     * 模型 ID 视图
     */
    @IdView("model")
    val modelId: Long?

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * 主表
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val sourceTable: GenTable

    /**
     * 主表 ID 视图
     */
    @IdView
    val sourceTableId: Long

    /**
     * 从表
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val targetTable: GenTable

    /**
     * 从表 ID 视图
     */
    @IdView
    val targetTableId: Long

    /**
     * 列引用
     */
    @OneToMany(mappedBy = "association")
    val columnReferences: List<GenColumnReference>

    /**
     * 列引用 ID 视图
     */
    @IdView("columnReferences")
    val columnReferenceIds: List<Long>

    /**
     * 关联类型
     */
    val associationType: AssociationType


    /**
     * 脱钩行为
     */
    val dissociateAction: DissociateAction?

    /**
     * 是否伪外键
     */
    val fake: Boolean

    /**
     * 自定排序
     */
    val orderKey: Long
}

