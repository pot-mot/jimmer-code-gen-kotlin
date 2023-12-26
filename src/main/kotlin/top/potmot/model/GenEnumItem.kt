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
import org.babyfish.jimmer.sql.Table
import top.potmot.model.base.BaseEntity

/**
 * 生成枚举元素
 *
 * @author
 * @since 2023-09-30T19:31:11.935
 */
@Entity
@Table(name = "jimmer_code_gen.gen_enum_item")
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
    @Key
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
    @Key
    val name: String

    /**
     * 元素值
     */
    val value: String

    /**
     * 元素注释
     */
    val comment: String

    /**
     * 自定排序
     */
    val orderKey: Long
}
