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
 * 多对多映射属性
 * 
 * @author potmot
 */
@Entity
interface GenManyToManyMappedProperty : AssociationPropertyTypeInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    val mappedBy: GenManyToManySourceProperty

    /**
     * 对应源属性 ID View
     */
    @IdView("mappedBy")
    val mappedById: Int

    /**
     * ID视图名
     */
    @get:Length(max = 255)
    val idViewName: String

    /**
     * 多对多关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToManyAssociation.mappedProperty
     */
    @OneToMany(mappedBy = "mappedProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToManyAssociations: List<GenManyToManyAssociation>

    /**
     * 多对多关联 ID View
     */
    @IdView("genManyToManyAssociations")
    val genManyToManyAssociationIds: List<Int>
}
