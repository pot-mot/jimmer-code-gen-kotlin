package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table
import top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty
import top.potmot.entity.model.entities.properties.GenManyToManySourceProperty

/**
 * 多对多关联
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_many_to_many_association")
interface GenManyToManyAssociation {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 源属性
     */
    @ManyToOne
    @JoinColumn(
        name = "source_property_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val sourceProperty: GenManyToManySourceProperty

    /**
     * 源属性 ID View
     */
    @IdView("sourceProperty")
    val sourcePropertyId: Int

    /**
     * 目标属性
     */
    @ManyToOne
    @JoinColumn(
        name = "mapped_property_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val mappedProperty: GenManyToManyMappedProperty

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
    @get:Valid
    val joinTable: GenJoinTable

    /**
     * Join Table ID View
     */
    @IdView("joinTable")
    val joinTableId: Int
}
