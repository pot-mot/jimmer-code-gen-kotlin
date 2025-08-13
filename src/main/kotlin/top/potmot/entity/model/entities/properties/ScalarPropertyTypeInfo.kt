package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.GenColumnInfo
import top.potmot.entity.model.embeddables.GenEmbeddableType
import top.potmot.entity.model.enums.GenEnum

/**
 * 标量属性类型信息
 * 
 * @author potmot
 */
@MappedSuperclass
interface ScalarPropertyTypeInfo {
    /**
     * 字面类型
     */
    @get:Length(max = 500)
    val rawType: String?

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
    val typeEmbeddable: GenEmbeddableType?

    /**
     * 类型对应嵌入 ID View
     */
    @IdView("typeEmbeddable")
    val typeEmbeddableId: Int?

    /**
     * 列信息
     */
    @ManyToOne
    @JoinColumn(
        name = "column_info_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.SET_NULL)
    @get:Valid
    val columnInfo: GenColumnInfo?

    /**
     * 列信息 ID View
     */
    @IdView("columnInfo")
    val columnInfoId: Int?
}
