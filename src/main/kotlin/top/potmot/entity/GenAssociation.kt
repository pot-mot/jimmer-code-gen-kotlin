package top.potmot.entity

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
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.AssociationType
import top.potmot.entity.base.BaseEntity

/**
 * 生成关联
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
    val id: Long

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
    @OneToMany(mappedBy = "association", orderedProps = [OrderedProp(value = "orderKey")])
    val columnReferences: List<GenColumnReference>

    /**
     * 列引用 ID 视图
     */
    @IdView("columnReferences")
    val columnReferenceIds: List<Long>

    /**
     * 类型
     */
    val type: AssociationType


    /**
     * 脱钩行为
     */
    val dissociateAction: DissociateAction?

    /**
     * 更新行为
     */
    val updateAction: String

    /**
     * 删除行为
     */
    val deleteAction: String

    /**
     * 是否伪外键
     */
    val fake: Boolean

    /**
     * 备注
     */
    val remark: String
}

