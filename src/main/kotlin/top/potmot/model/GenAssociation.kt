package top.potmot.model

import org.babyfish.jimmer.sql.*
import top.potmot.enum.AssociationType
import top.potmot.model.base.BaseEntity

/**
 * 生成关联实体类
 *
 * @author potmot
 * @since 2023-08-12 10:47:36
 */
@Entity
@Table(name = "jimmer-code-gen.gen_association")
interface GenAssociation : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 关联注释
     */
    val comment: String

    /**
     * 主列 ID
     */
    @IdView
    val sourceColumnId: Long

    /**
     * 主列
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val sourceColumn: GenColumn

    /**
     * 从列 ID
     */
    @IdView
    val targetColumnId: Long

    /**
     * 从列
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val targetColumn: GenColumn

    /**
     * 关联类型
     */
    val associationType: AssociationType?


    /**
     * 脱钩行为
     */
    val dissociateAction: DissociateAction?

    /**
     * 自定排序
     */
    val orderKey: Long

}

