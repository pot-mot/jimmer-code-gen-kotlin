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
import top.potmot.entity.model.entities.properties.GenExtraProperty
import top.potmot.entity.model.entities.properties.GenScalarProperty

/**
 * 嵌入类型
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_embeddable")
interface GenEmbeddable {
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
     * 嵌入类型深层属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableDeepProperty.embeddable
     */
    @OneToMany(mappedBy = "embeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableDeepPropertiesForEmbeddable: List<GenEmbeddableDeepProperty>

    /**
     * 嵌入类型深层属性 ID View
     */
    @IdView("embeddableDeepPropertiesForEmbeddable")
    val embeddableDeepPropertyIdsForEmbeddable: List<Int>

    /**
     * 嵌入类型深层属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableDeepProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableDeepPropertiesForTypeEmbeddable: List<GenEmbeddableDeepProperty>

    /**
     * 嵌入类型深层属性 ID View
     */
    @IdView("embeddableDeepPropertiesForTypeEmbeddable")
    val embeddableDeepPropertyIdsForTypeEmbeddable: List<Int>

    /**
     * 嵌入类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableProperty.embeddable
     */
    @OneToMany(mappedBy = "embeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableProperties: List<GenEmbeddableProperty>

    /**
     * 嵌入类型属性 ID View
     */
    @IdView("embeddableProperties")
    val embeddablePropertyIds: List<Int>

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val extraProperties: List<GenExtraProperty>

    /**
     * 额外属性 ID View
     */
    @IdView("extraProperties")
    val extraPropertyIds: List<Int>

    /**
     * 标量属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenScalarProperty.typeEmbeddable
     */
    @OneToMany(mappedBy = "typeEmbeddable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val scalarProperties: List<GenScalarProperty>

    /**
     * 标量属性 ID View
     */
    @IdView("scalarProperties")
    val scalarPropertyIds: List<Int>
}
