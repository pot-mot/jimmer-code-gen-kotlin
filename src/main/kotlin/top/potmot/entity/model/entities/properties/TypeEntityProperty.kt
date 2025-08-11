package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.entity.model.entities.GenEntity

/**
 * 类型是实体的属性
 * 
 * @author potmot
 */
@MappedSuperclass
interface TypeEntityProperty {
    /**
     * 类型对应实体
     */
    @ManyToOne
    @JoinColumn(
        name = "type_entity_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val typeEntity: GenEntity

    /**
     * 类型对应实体 ID View
     */
    @IdView("typeEntity")
    val typeEntityId: Int
}
