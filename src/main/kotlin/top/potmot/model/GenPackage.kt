package top.potmot.model

import org.babyfish.jimmer.sql.*
import top.potmot.model.base.BaseEntity
import top.potmot.model.base.TreeNode

/**
 * 生成表分组实体类
 *
 * @author potmot
 * @since 2023-08-12 10:51:24
 */
@Entity
@Table(name = "jimmer-code-gen.gen_package")
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
     * 组名称
     */
    @Key
    val name: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

