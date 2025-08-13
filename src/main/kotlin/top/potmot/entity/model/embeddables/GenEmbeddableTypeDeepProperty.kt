package top.potmot.entity.model.embeddables

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
import org.hibernate.validator.constraints.Length

/**
 * 深层复合属性
 * 
 * @author potmot
 */
@Entity
interface GenEmbeddableTypeDeepProperty {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 嵌入类型
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "embeddable_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val embeddable: GenEmbeddableType

    /**
     * 嵌入类型 ID View
     */
    @IdView("embeddable")
    val embeddableId: Int

    /**
     * 名称
     */
    @Key
    @get:Length(max = 500)
    val name: String

    /**
     * 注释
     */
    @get:Length(max = 500)
    val comment: String

    /**
     * 类型对应嵌入类型
     */
    @ManyToOne
    @JoinColumn(
        name = "type_embeddable_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val typeEmbeddable: GenEmbeddableType

    /**
     * 类型对应嵌入类型 ID View
     */
    @IdView("typeEmbeddable")
    val typeEmbeddableId: Int

    /**
     * 覆盖列名
     */
    @get:Length(max = 255)
    val overrideColumnNames: String

    /**
     * 其他注解
     */
    @get:Length(max = 500)
    val extraAnnotations: String?

    /**
     * 其他导入
     */
    @get:Length(max = 255)
    val extraImports: String?

    /**
     * 其他验证器
     */
    @get:Length(max = 255)
    val extraValidations: String?

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 排序键
     */
    val orderKey: Int
}
