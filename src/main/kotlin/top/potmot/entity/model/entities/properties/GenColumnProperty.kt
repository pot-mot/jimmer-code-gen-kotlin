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
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.GenColumnTypeInfo
import top.potmot.entity.model.entities.GenEntity

/**
 * 列属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_column_property")
interface GenColumnProperty : GenColumnTypeInfo {
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
     * 实体
     */
    @ManyToOne
    @JoinColumn(
        name = "entity_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val entity: GenEntity

    /**
     * 实体 ID View
     */
    @IdView("entity")
    val entityId: Int

    /**
     * 类型
     */
    @Column(name = "type")
    @get:Length(max = 255)
    val type: String

    /**
     * 列名
     */
    @Column(name = "column_name")
    @get:Length(max = 255)
    val columnName: String
}
