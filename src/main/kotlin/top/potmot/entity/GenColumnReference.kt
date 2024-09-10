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
import org.babyfish.jimmer.sql.Table
import top.potmot.entity.base.BaseEntity

/**
 * 生成关联
 *
 * @author potmot
 * @since 2023-08-12 10:47:36
 */
@Entity
@Table(name = "gen_column_reference")
interface GenColumnReference : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 关联
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val association: GenAssociation

    /**
     * 关联 ID 视图
     */
    @IdView("association")
    val associationId: Long

    /**
     * 主列
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val sourceColumn: GenColumn

    /**
     * 主列 ID 视图
     */
    @IdView
    val sourceColumnId: Long

    /**
     * 从列
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val targetColumn: GenColumn

    /**
     * 从列 ID 视图
     */
    @IdView
    val targetColumnId: Long

    /**
     * 排序键
     */
    val orderKey: Long

    /**
     * 备注
     */
    val remark: String
}

