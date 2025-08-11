package top.potmot.entity.model.embeddables

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
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 深层复合属性列覆盖
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_deep_embeddable_property_override")
interface GenDeepEmbeddablePropertyOverride {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 深层属性
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "deep_property_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val deepProperty: GenEmbeddableTypeDeepProperty

    /**
     * 深层属性 ID View
     */
    @IdView("deepProperty")
    val deepPropertyId: Int

    /**
     * 覆盖属性
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "override_property_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val overrideProperty: GenEmbeddableTypeProperty

    /**
     * 覆盖属性 ID View
     */
    @IdView("overrideProperty")
    val overridePropertyId: Int

    /**
     * 覆盖后列名
     */
    @Column(name = "column_name")
    @get:Length(max = 255)
    val columnName: String
}
