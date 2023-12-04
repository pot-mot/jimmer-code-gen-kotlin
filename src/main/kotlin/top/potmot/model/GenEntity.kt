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
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import top.potmot.model.base.BaseEntity

/**
 * 生成实体实体类
 *
 * @author potmot
 * @since 2023-08-12 10:48:54
 */
@Entity
@Table(name = "jimmer_code_gen.gen_entity")
interface GenEntity : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 所属包 ID
     */
    @IdView("genPackage")
    val packageId: Long?

    /**
     * 所属包
     */
    @ManyToOne
    @JoinColumn(name = "package_id")
    @OnDissociate(DissociateAction.SET_NULL)
    val genPackage: GenPackage?

    /**
     * 对应表 ID
     */
    @IdView
    val tableId: Long

    /**
     * 对应表
     */
    @OneToOne
    @OnDissociate(DissociateAction.DELETE)
    @Key
    val table: GenTable

    /**
     * 类名称
     */
    val name: String

    /**
     * 类注释
     */
    val comment: String

    /**
     * 作者
     */
    val author: String

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 属性 ID
     */
    @IdView("properties")
    val propertyIds: List<Long>

    /**
     * 属性
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("orderKey")])
    val properties: List<GenProperty>
}

