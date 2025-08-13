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
import org.babyfish.jimmer.sql.OneToOne
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.associations.GenManyToOneAssociation

/**
 * 多对一源属性（对单）
 * 
 * @author potmot
 */
@Entity
interface GenManyToOneSourceProperty : AssociationPropertyTypeInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 多对一关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToOneAssociation.sourceProperty
     */
    @OneToOne(mappedBy = "sourceProperty")
    @get:Valid
    val genManyToOneAssociation: GenManyToOneAssociation?

    /**
     * 多对一关联 ID View
     */
    @IdView("genManyToOneAssociation")
    val genManyToOneAssociationId: Int?

    /**
     * 多对一映射属性（对多）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty.mappedBy
     */
    @OneToOne(mappedBy = "mappedBy")
    @get:Valid
    val genManyToOneMappedProperty: GenManyToOneMappedProperty?

    /**
     * 多对一映射属性（对多） ID View
     */
    @IdView("genManyToOneMappedProperty")
    val genManyToOneMappedPropertyId: Int?

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
     * 类型是否非空
     */
    val typeIsNotNull: Boolean
}
