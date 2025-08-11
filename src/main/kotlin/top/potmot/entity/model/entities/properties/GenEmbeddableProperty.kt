package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
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
import top.potmot.entity.model.embeddables.GenEmbeddableType
import top.potmot.entity.model.entities.GenEntity

/**
 * 复合属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_embeddable_property")
interface GenEmbeddableProperty {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 属性
     */
    @Key
    @OneToOne
    @JoinColumn(
        name = "property_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val property: GenProperty

    /**
     * 属性 ID View
     */
    @IdView("property")
    val propertyId: Int

    /**
     * 实体
     */
    @ManyToOne
    @JoinColumn(
        name = "entity_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val entity: GenEntity

    /**
     * 实体 ID View
     */
    @IdView("entity")
    val entityId: Int

    /**
     * 复合类型
     */
    @ManyToOne
    @JoinColumn(
        name = "type_embeddable_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val typeEmbeddable: GenEmbeddableType

    /**
     * 复合类型 ID View
     */
    @IdView("typeEmbeddable")
    val typeEmbeddableId: Int

    /**
     * 复合属性列覆盖
     * 
     * @see top.potmot.entity.model.entities.properties.GenEmbeddablePropertyOverride.embeddableProperty
     */
    @OneToMany(mappedBy = "embeddableProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddablePropertyOverrides: List<GenEmbeddablePropertyOverride>

    /**
     * 复合属性列覆盖 ID View
     */
    @IdView("embeddablePropertyOverrides")
    val embeddablePropertyOverrideIds: List<Int>
}
