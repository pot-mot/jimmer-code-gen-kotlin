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
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.entities.GenEntity
import top.potmot.entity.model.enums.GenEnum

/**
 * 额外属性
 * 
 * @author potmot
 */
@Entity
interface GenExtraProperty {
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
     * 类型
     */
    @get:Length(max = 255)
    val type: String

    /**
     * 类型对应实体
     */
    @ManyToOne
    @JoinColumn(
        name = "type_entity_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.SET_NULL)
    @get:Valid
    val typeEntity: GenEntity?

    /**
     * 类型对应实体 ID View
     */
    @IdView("typeEntity")
    val typeEntityId: Int?

    /**
     * 类型对应枚举
     */
    @ManyToOne
    @JoinColumn(
        name = "type_enum_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val typeEnum: GenEnum?

    /**
     * 类型对应枚举 ID View
     */
    @IdView("typeEnum")
    val typeEnumId: Int?

    /**
     * 属性方法体
     */
    @get:Length(max = 500)
    val body: String?
}
