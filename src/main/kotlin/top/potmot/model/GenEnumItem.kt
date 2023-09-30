package top.potmot.model

import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import kotlin.Long
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.IdView
import top.potmot.model.base.BaseEntity
import kotlin.String

/**
 * 生成枚举元素
 *
 * @author
 * @since 2023-09-30T19:31:11.935
 */
interface GenEnumItem : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 生成枚举
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val enum: GenEnum

    /**
     * 生成枚举 ID 视图
     */
    @IdView("enum")
    val enumId: Long

    /**
     * 元素名
     */
    val name: String?

    /**
     * 元素值
     */
    val value: String?

    /**
     * 元素注释
     */
    val comment: String?
}
