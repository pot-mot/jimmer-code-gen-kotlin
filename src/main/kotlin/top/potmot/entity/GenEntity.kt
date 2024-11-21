package top.potmot.entity

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.Transient
import top.potmot.entity.base.BaseEntity
import top.potmot.entity.resolver.GenEntityIdPropertiesResolver
import top.potmot.entity.resolver.GenEntityLogicalDeleteResolver

/**
 * 生成实体
 *
 * @author potmot
 * @since 2023-08-12 10:48:54
 */
@Entity
@Table(name = "gen_entity")
interface GenEntity : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 模型
     */
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
     * 对应表 ID 视图
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
     * 上级实体
     */
    @ManyToMany
    @JoinTable(
        name = "gen_super_entity_mapping",
        joinColumnName = "inherit_entity_id",
        inverseJoinColumnName = "super_entity_id"
    )
    val superEntities: List<GenEntity>

    /**
     * 上级实体 ID 视图
     */
    @IdView("superEntities")
    val superEntityIds: List<Long>

    /**
     * 继承实体
     */
    @ManyToMany(mappedBy = "superEntities")
    val inheritEntities: List<GenEntity>

    /**
     * 继承实体 ID 视图
     */
    @IdView("inheritEntities")
    val inheritEntityIds: List<Long>

    /**
     * 是否为逻辑删除
     */
    @Transient(GenEntityLogicalDeleteResolver::class)
    val logicalDelete: Boolean

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
     * 是否可以创建
     */
    val canAdd: Boolean

    /**
     * 是否可以修改
     */
    val canEdit: Boolean

    /**
     * 是否可以删除
     */
    val canDelete: Boolean

    /**
     * 是否可以查询
     */
    val canQuery: Boolean

    /**
     * 是否具有页面
     */
    val hasPage: Boolean

    /**
     * 备注
     */
    val remark: String

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

    /**
     * id 属性
     */
    @Transient(GenEntityIdPropertiesResolver::class)
    val idProperties: List<GenProperty>
}

