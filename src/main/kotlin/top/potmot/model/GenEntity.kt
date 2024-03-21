package top.potmot.model

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
        name = "jimmer_code_gen.gen_super_entity_mapping",
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
}

