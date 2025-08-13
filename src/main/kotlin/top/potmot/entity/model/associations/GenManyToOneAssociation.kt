package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty
import top.potmot.entity.model.entities.properties.GenManyToOneSourceProperty

/**
 * 多对一关联
 * 
 * @author potmot
 */
@Entity
interface GenManyToOneAssociation {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 源属性
     */
    @OneToOne
    @JoinColumn(
        name = "source_property_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val sourceProperty: GenManyToOneSourceProperty

    /**
     * 源属性 ID View
     */
    @IdView("sourceProperty")
    val sourcePropertyId: Int

    /**
     * 目标属性
     */
    @OneToOne
    @JoinColumn(
        name = "mapped_property_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val mappedProperty: GenManyToOneMappedProperty

    /**
     * 目标属性 ID View
     */
    @IdView("mappedProperty")
    val mappedPropertyId: Int

    /**
     * Join Table
     */
    @ManyToOne
    @JoinColumn(
        name = "join_table_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val joinTable: GenJoinTable?

    /**
     * Join Table ID View
     */
    @IdView("joinTable")
    val joinTableId: Int?
}
