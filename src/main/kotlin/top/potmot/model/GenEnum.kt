package top.potmot.model

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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.EnumType
import top.potmot.model.base.BaseEntity

/**
 * 生成枚举
 *
 * @author
 * @since 2023-09-30T19:31:11.933
 */
@Entity
@Table(name = "jimmer_code_gen.gen_enum")
interface GenEnum : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

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
     * 生成包
     */
    @Key
    @ManyToOne
    @JoinColumn(name = "package_id")
    @OnDissociate(DissociateAction.SET_NULL)
    val genPackage: GenPackage?

    /**
     * 生成包 ID 视图
     */
    @IdView("genPackage")
    val packageId: Long?

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
     * 自定排序
     */
    val orderKey: Long
}
