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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.associations.GenManyToManyAssociation

/**
 * 多对多源属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_many_to_many_source_property")
interface GenManyToManySourceProperty : TypeMustEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 多对多映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty.mappedBy
     */
    @OneToOne(mappedBy = "mappedBy")
    @get:Valid
    val manyToManyMappedProperty: GenManyToManyMappedProperty?

    /**
     * 多对多映射属性 ID View
     */
    @IdView("manyToManyMappedProperty")
    val manyToManyMappedPropertyId: Int?

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
     * ID视图名
     */
    @Column(name = "id_view_name")
    @get:Length(max = 255)
    val idViewName: String

    /**
     * 多对多关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToManyAssociation.sourceProperty
     */
    @OneToMany(mappedBy = "sourceProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyTomenyAssociation: List<GenManyToManyAssociation>

    /**
     * 多对多关联 ID View
     */
    @IdView("manyTomenyAssociation")
    val manyTomenyAssociationId: List<Int>
}
