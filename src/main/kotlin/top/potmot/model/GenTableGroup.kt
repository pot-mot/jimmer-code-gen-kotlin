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
import top.potmot.model.base.BaseEntity
import top.potmot.model.base.TreeNode

/**
 * 生成表分组实体类
 *
 * @author potmot
 * @since 2023-08-12 10:51:24
 */
@Entity
interface GenTableGroup: BaseEntity, TreeNode<GenTableGroup> {
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
     * 生成表
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("orderKey")])
    val genTables: List<GenTable>

    /**
     * 组名称
     */
    @Key
    val groupName: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

