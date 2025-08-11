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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.entities.properties.GenEmbeddablePropertyOverride

/**
 * 深层复合属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_embeddable_type_deep_property")
interface GenEmbeddableTypeDeepProperty {
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
    val typeEmbeddable: GenEmbeddableType

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

    /**
     * 排序键
     */
    @Column(name = "order_key")
    val orderKey: Int

    /**
     * 深层复合属性列覆盖
     * 
     * @see top.potmot.entity.model.embeddables.GenDeepEmbeddablePropertyOverride.deepProperty
     */
    @OneToMany(mappedBy = "deepProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val deepEmbeddablePropertyOverrides: List<GenDeepEmbeddablePropertyOverride>

    /**
     * 深层复合属性列覆盖 ID View
     */
    @IdView("deepEmbeddablePropertyOverrides")
    val deepEmbeddablePropertyOverrideIds: List<Int>

    /**
     * 复合属性列覆盖
     * 
     * @see top.potmot.entity.model.entities.properties.GenEmbeddablePropertyOverride.deepProperty
     */
    @OneToMany(mappedBy = "deepProperty", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddablePropertyOverrides: List<GenEmbeddablePropertyOverride>

    /**
     * 复合属性列覆盖 ID View
     */
    @IdView("embeddablePropertyOverrides")
    val embeddablePropertyOverrideIds: List<Int>
}
