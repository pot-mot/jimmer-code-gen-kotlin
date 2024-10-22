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
import org.babyfish.jimmer.sql.Transient
import top.potmot.enumeration.EnumType
import top.potmot.entity.base.BaseEntity
import top.potmot.entity.resolver.GenEnumDefaultItemsResolver

/**
 * 生成枚举
 *
 * @author
 * @since 2023-09-30T19:31:11.933
 */
@Entity
@Table(name = "gen_enum")
interface GenEnum : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 模型
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val model: GenModel?

    /**
     * 模型 ID 视图
     */
    @IdView("model")
    val modelId: Long?

    /**
     * 包路径
     */
    val packagePath: String

    /**
     * 枚举名
     */
    @Key
    val name: String

    /**
     * 枚举注释
     */
    val comment: String

    /**
     * 枚举类型
     */
    val enumType: EnumType?

    /**
     * 备注
     */
    val remark: String

    /**
     * 生成枚举元素
     */
    @OneToMany(mappedBy = "enum", orderedProps = [OrderedProp("orderKey")])
    val items: List<GenEnumItem>

    /**
     * 生成枚举元素 ID 视图
     */
    @IdView("items")
    val itemIds: List<Long>

    /**
     * 默认枚举元素
     */
    @Transient(GenEnumDefaultItemsResolver::class)
    val defaultItems: List<GenEnumItem>
}
