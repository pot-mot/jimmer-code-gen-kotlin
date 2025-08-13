package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
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
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.associations.GenManyToManyAssociation

/**
 * 多对多源属性
 * 
 * @author potmot
 */
@Entity
interface GenManyToManySourceProperty : AssociationPropertyTypeInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 多对多映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty.mappedBy
     */
    @OneToOne(mappedBy = "mappedBy")
    @get:Valid
    val genManyToManyMappedProperty: GenManyToManyMappedProperty?

    /**
     * 多对多映射属性 ID View
     */
    @IdView("genManyToManyMappedProperty")
    val genManyToManyMappedPropertyId: Int?

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
    @get:Length(max = 255)
    val idViewName: String

    /**
     * 多对多关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToManyAssociation.sourceProperty
     */
    @OneToMany(mappedBy = "sourceProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToManyAssociations: List<GenManyToManyAssociation>

    /**
     * 多对多关联 ID View
     */
    @IdView("genManyToManyAssociations")
    val genManyToManyAssociationIds: List<Int>
}
