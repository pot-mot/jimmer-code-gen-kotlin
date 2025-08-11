package top.potmot.entity.model.embeddables

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
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 嵌入类型深层属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_embeddable_deep_property")
interface GenEmbeddableDeepProperty {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    val embeddable: GenEmbeddable

    /**
     * 嵌入类型 ID View
     */
    @IdView("embeddable")
    val embeddableId: Int

    /**
     * 名称
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 注释
     */
    @Column(name = "comment")
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
    val typeEmbeddable: GenEmbeddable

    /**
     * 类型对应嵌入类型 ID View
     */
    @IdView("typeEmbeddable")
    val typeEmbeddableId: Int

    /**
     * 其他注解
     */
    @Column(name = "extra_annotations")
    @get:Length(max = 500)
    val extraAnnotations: String?

    /**
     * 其他导入
     */
    @Column(name = "extra_imports")
    @get:Length(max = 255)
    val extraImports: String?

    /**
     * 其他验证器
     */
    @Column(name = "extra_validations")
    @get:Length(max = 255)
    val extraValidations: String?

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String
}
