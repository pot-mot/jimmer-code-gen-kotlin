package top.potmot.entity.model.entities

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.entities.properties.GenProperty

/**
 * 实体索引
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_entity_index")
interface GenEntityIndex {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 实体业务键组
     * 
     * @see top.potmot.entity.model.entities.GenEntityKeyGroup.index
     */
    @OneToOne(mappedBy = "index")
    @get:Valid
    val entityKeyGroup: GenEntityKeyGroup?

    /**
     * 实体业务键组 ID View
     */
    @IdView("entityKeyGroup")
    val entityKeyGroupId: Int?

    /**
     * 归属表
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "entity_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val entity: GenEntity

    /**
     * 归属表 ID View
     */
    @IdView("entity")
    val entityId: Int

    /**
     * 名称
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 是否是唯一索引
     */
    @Column(name = "unique_index")
    val uniqueIndex: Boolean

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String

    /**
     * 排序键
     */
    @Column(name = "order_key")
    val orderKey: Int

    /**
     * 属性
     */
    @ManyToMany(orderedProps = [OrderedProp("id")])
    @JoinTable(
        name = "gen_entity_index_gen_property_mapping",
        joinColumnName = "gen_entity_index_id",
        inverseJoinColumnName = "gen_property_id"
    )
    @get:Valid
    val properties: List<GenProperty>

    /**
     * 属性 ID View
     */
    @IdView("properties")
    val propertyIds: List<Int>
}
