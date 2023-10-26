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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import top.potmot.model.base.BaseEntity
import top.potmot.model.base.TreeNode

/**
 * 生成表分组实体类
 *
 * @author potmot
 * @since 2023-08-12 10:51:24
 */
@Entity
@Table(name = "jimmer_code_gen.gen_package")
interface GenPackage : BaseEntity, TreeNode<GenPackage> {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 父包 ID
     */
    @IdView
    override val parentId: Long?

    /**
     * 父包
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    override val parent: GenPackage?

    /**
     * 子包
     */
    @OneToMany(mappedBy = "parent", orderedProps = [OrderedProp("orderKey")])
    override val children: List<GenPackage>

    /**
     * 表
     */
    @OneToMany(mappedBy = "genPackage", orderedProps = [OrderedProp("orderKey")])
    val entities: List<GenEntity>

    /**
     * 生成实体ID视图
     */
    @IdView("entities")
    val entityIds: List<Long>

    /**
     * 生成枚举
     */
    @OneToMany(mappedBy = "genPackage", orderedProps = [OrderedProp("orderKey")])
    val enums: List<GenEnum>

    /**
     * 生成枚举ID视图
     */
    @IdView("enums")
    val enumIds: List<Long>

    /**
     * 组名称
     */
    @Key
    val name: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

