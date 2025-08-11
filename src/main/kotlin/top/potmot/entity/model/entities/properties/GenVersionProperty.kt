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
import top.potmot.entity.model.entities.GenEntity

/**
 * 乐观锁属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_version_property")
interface GenVersionProperty {
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
     * 列名称
     */
    @Column(name = "column_name")
    @get:Length(max = 255)
    val columnName: String

    /**
     * 实体
     * 
     * @see top.potmot.entity.model.entities.GenEntity.versionProperty
     */
    @OneToMany(mappedBy = "versionProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entities: List<GenEntity>

    /**
     * 实体 ID View
     */
    @IdView("entities")
    val entityIds: List<Int>
}
