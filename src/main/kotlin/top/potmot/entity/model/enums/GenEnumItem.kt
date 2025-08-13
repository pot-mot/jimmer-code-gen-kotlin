package top.potmot.entity.model.enums

import jakarta.validation.Valid
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
import org.hibernate.validator.constraints.Length

/**
 * 枚举元素
 * 
 * @author potmot
 */
@Entity
interface GenEnumItem {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @get:Length(max = 500)
    val name: String

    /**
     * 值
     */
    @get:Length(max = 500)
    val mappedValue: String

    /**
     * 注释
     */
    @get:Length(max = 500)
    val comment: String

    /**
     * 是否默认
     */
    val defaultItem: Boolean

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 排序键
     */
    val orderKey: Int
}
