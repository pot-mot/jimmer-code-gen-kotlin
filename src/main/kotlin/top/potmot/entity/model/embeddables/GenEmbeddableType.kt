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
import top.potmot.entity.model.GenModelGroup
import top.potmot.entity.model.entities.properties.GenEmbeddableProperty

/**
 * 复合类型
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_embeddable_type")
interface GenEmbeddableType {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Column(name = "name")
    @get:Length(max = 255)
    val name: String

    /**
     * 子包路径
     */
    @Column(name = "sub_package_path")
    @get:Length(max = 500)
    val subPackagePath: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 255)
    val comment: String

    /**
     * 复合属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenEmbeddableProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableProperties: List<GenEmbeddableProperty>

    /**
     * 复合属性 ID View
     */
    @IdView("embeddableProperties")
    val embeddablePropertyIds: List<Int>

    /**
     * 深层复合属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeDeepProperty.embeddable
     */
    @OneToMany(mappedBy = "embeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableTypeDeepPropertiesForEmbeddable: List<GenEmbeddableTypeDeepProperty>

    /**
     * 深层复合属性 ID View
     */
    @IdView("embeddableTypeDeepPropertiesForEmbeddable")
    val embeddableTypeDeepPropertyIdsForEmbeddable: List<Int>

    /**
     * 深层复合属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeDeepProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableTypeDeepPropertiesForTypeEmbeddable: List<GenEmbeddableTypeDeepProperty>

    /**
     * 深层复合属性 ID View
     */
    @IdView("embeddableTypeDeepPropertiesForTypeEmbeddable")
    val embeddableTypeDeepPropertyIdsForTypeEmbeddable: List<Int>

    /**
     * 复合类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty.embeddable
     */
    @OneToMany(mappedBy = "embeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableTypeProperties: List<GenEmbeddableTypeProperty>

    /**
     * 复合类型属性 ID View
     */
    @IdView("embeddableTypeProperties")
    val embeddableTypePropertyIds: List<Int>
}
