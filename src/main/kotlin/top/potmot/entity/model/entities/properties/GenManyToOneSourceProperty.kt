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
import top.potmot.entity.model.associations.GenManyToOneAssociation

/**
 * 多对一源属性（对单）
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_many_to_one_source_property")
interface GenManyToOneSourceProperty : TypeMustEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 多对一关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToOneAssociation.sourceProperty
     */
    @OneToOne(mappedBy = "sourceProperty")
    @get:Valid
    val manyToOneAssociation: GenManyToOneAssociation?

    /**
     * 多对一关联 ID View
     */
    @IdView("manyToOneAssociation")
    val manyToOneAssociationId: Int?

    /**
     * 多对一映射属性（对多）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty.mappedBy
     */
    @OneToOne(mappedBy = "mappedBy")
    @get:Valid
    val manyToOneMappedProperty: GenManyToOneMappedProperty?

    /**
     * 多对一映射属性（对多） ID View
     */
    @IdView("manyToOneMappedProperty")
    val manyToOneMappedPropertyId: Int?

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
}
