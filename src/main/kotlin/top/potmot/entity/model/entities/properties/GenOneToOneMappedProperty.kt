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
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.associations.GenOneToOneAssociation

/**
 * 一对一映射属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_one_to_one_mapped_property")
interface GenOneToOneMappedProperty : TypeMustEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 一对一关联
     * 
     * @see top.potmot.entity.model.associations.GenOneToOneAssociation.mappedProperty
     */
    @OneToOne(mappedBy = "mappedProperty")
    @get:Valid
    val oneToOneAssociation: GenOneToOneAssociation?

    /**
     * 一对一关联 ID View
     */
    @IdView("oneToOneAssociation")
    val oneToOneAssociationId: Int?

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
     * 对应源属性
     */
    @Key(group = "mapped_by")
    @OneToOne
    @JoinColumn(
        name = "mapped_by_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val mappedBy: GenOneToOneSourceProperty

    /**
     * 对应源属性 ID View
     */
    @IdView("mappedBy")
    val mappedById: Int

    /**
     * ID视图名
     */
    @Column(name = "id_view_name")
    @get:Length(max = 255)
    val idViewName: String
}
