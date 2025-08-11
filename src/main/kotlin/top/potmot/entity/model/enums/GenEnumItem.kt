package top.potmot.entity.model.enums

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 枚举元素
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_enum_item")
interface GenEnumItem {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 枚举
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "enum_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val enum: GenEnum

    /**
     * 枚举 ID View
     */
    @IdView("enum")
    val enumId: Int

    /**
     * 名称
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 值
     */
    @Column(name = "mapped_value")
    @get:Length(max = 500)
    val mappedValue: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 500)
    val comment: String

    /**
     * 排序键
     */
    @Column(name = "order_key")
    @get:Max(value = 2147483647, message = "排序键不可大于2147483647")
    val orderKey: Int

    /**
     * 是否默认
     */
    @Column(name = "default_item")
    val defaultItem: Boolean

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String
}
