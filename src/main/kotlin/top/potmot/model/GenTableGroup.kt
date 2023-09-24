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
interface GenTableGroup : BaseEntity, TreeNode<GenTableGroup> {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 父组 ID
     */
    @IdView
    override val parentId: Long?

    /**
     * 父组
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    override val parent: GenTableGroup?

    /**
     * 子组
     */
    @OneToMany(mappedBy = "parent", orderedProps = [OrderedProp("orderKey")])
    override val children: List<GenTableGroup>

    /**
     * 表
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("orderKey")])
    val tables: List<GenTable>

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

