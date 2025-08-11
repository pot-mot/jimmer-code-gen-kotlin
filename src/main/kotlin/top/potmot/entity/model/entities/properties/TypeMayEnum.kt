package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.entity.model.enums.GenEnum

/**
 * 类型可能是枚举
 * 
 * @author potmot
 */
@MappedSuperclass
interface TypeMayEnum {
    /**
     * 类型对应枚举
     */
    @ManyToOne
    @JoinColumn(
        name = "type_enum_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.SET_NULL)
    @get:Valid
    val typeEnum: GenEnum?

    /**
     * 类型对应枚举 ID View
     */
    @IdView("typeEnum")
    val typeEnumId: Int?
}
