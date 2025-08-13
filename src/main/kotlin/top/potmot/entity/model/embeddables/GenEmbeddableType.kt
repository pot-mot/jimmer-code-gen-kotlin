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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.GenModelGroup
import top.potmot.entity.model.entities.properties.GenProperty

/**
 * 复合类型
 * 
 * @author potmot
 */
@Entity
interface GenEmbeddableType {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 分组
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "group_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val group: GenModelGroup

    /**
     * 分组 ID View
     */
    @IdView("group")
    val groupId: Int

    /**
     * 名称
     */
    @Key
    @get:Length(max = 255)
    val name: String

    /**
     * 子包路径
     */
    @get:Length(max = 500)
    val subPackagePath: String

    /**
     * 注释
     */
    @get:Length(max = 255)
    val comment: String

    /**
     * 深层复合属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeDeepProperty.embeddable
     */
    @OneToMany(mappedBy = "embeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypeDeepPropertiesForEmbeddable: List<GenEmbeddableTypeDeepProperty>

    /**
     * 深层复合属性 ID View
     */
    @IdView("genEmbeddableTypeDeepPropertiesForEmbeddable")
    val genEmbeddableTypeDeepPropertyIdsForEmbeddable: List<Int>

    /**
     * 深层复合属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeDeepProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypeDeepPropertiesForTypeEmbeddable: List<GenEmbeddableTypeDeepProperty>

    /**
     * 深层复合属性 ID View
     */
    @IdView("genEmbeddableTypeDeepPropertiesForTypeEmbeddable")
    val genEmbeddableTypeDeepPropertyIdsForTypeEmbeddable: List<Int>

    /**
     * 复合类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty.embeddable
     */
    @OneToMany(mappedBy = "embeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypePropertiesForEmbeddable: List<GenEmbeddableTypeProperty>

    /**
     * 复合类型属性 ID View
     */
    @IdView("genEmbeddableTypePropertiesForEmbeddable")
    val genEmbeddableTypePropertyIdsForEmbeddable: List<Int>

    /**
     * 复合类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypePropertiesForTypeEmbeddable: List<GenEmbeddableTypeProperty>

    /**
     * 复合类型属性 ID View
     */
    @IdView("genEmbeddableTypePropertiesForTypeEmbeddable")
    val genEmbeddableTypePropertyIdsForTypeEmbeddable: List<Int>

    /**
     * 属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genProperties: List<GenProperty>

    /**
     * 属性 ID View
     */
    @IdView("genProperties")
    val genPropertyIds: List<Int>
}
