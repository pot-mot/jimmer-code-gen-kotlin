package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.entity.model.embeddables.GenEmbeddable

/**
 * 类型可能是嵌入
 * 
 * @author potmot
 */
@MappedSuperclass
interface TypeMayEmbeddable {
    /**
     * 类型对应嵌入
     */
    @ManyToOne
    @JoinColumn(
        name = "type_embeddable_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.SET_NULL)
    @get:Valid
    val typeEmbeddable: GenEmbeddable?

    /**
     * 类型对应嵌入 ID View
     */
    @IdView("typeEmbeddable")
    val typeEmbeddableId: Int?
}
