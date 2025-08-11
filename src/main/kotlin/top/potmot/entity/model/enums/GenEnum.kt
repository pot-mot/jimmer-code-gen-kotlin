package top.potmot.entity.model.enums

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
import top.potmot.entity.model.GenColumnTypeInfo
import top.potmot.entity.model.GenModelGroup
import top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty
import top.potmot.entity.model.entities.properties.GenEnumColumnProperty
import top.potmot.entity.model.entities.properties.GenExtraProperty
import top.potmot.enums.model.enums.EnumType

/**
 * 枚举
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_enum")
interface GenEnum : GenColumnTypeInfo {
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
     * 枚举名
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 枚举注释
     */
    @Column(name = "comment")
    @get:Length(max = 500)
    val comment: String

    /**
     * 枚举类型
     */
    @Column(name = "enum_type")
    val enumType: EnumType

    /**
     * 子包路径
     */
    @Column(name = "sub_package_path")
    @get:Length(max = 500)
    val subPackagePath: String

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String

    /**
     * 枚举元素
     * 
     * @see top.potmot.entity.model.enums.GenEnumItem.enum
     */
    @OneToMany(mappedBy = "enum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val enumItems: List<GenEnumItem>

    /**
     * 枚举元素 ID View
     */
    @IdView("enumItems")
    val enumItemIds: List<Int>

    /**
     * 枚举列属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenEnumColumnProperty.typeEnum
     */
    @OneToMany(mappedBy = "typeEnum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val enumColumnProperties: List<GenEnumColumnProperty>

    /**
     * 枚举列属性 ID View
     */
    @IdView("enumColumnProperties")
    val enumColumnPropertyIds: List<Int>

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.typeEnum
     */
    @OneToMany(mappedBy = "typeEnum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val extraProperties: List<GenExtraProperty>

    /**
     * 额外属性 ID View
     */
    @IdView("extraProperties")
    val extraPropertyIds: List<Int>

    /**
     * 复合类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty.typeEnum
     */
    @OneToMany(mappedBy = "typeEnum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableTypeProperties: List<GenEmbeddableTypeProperty>

    /**
     * 复合类型属性 ID View
     */
    @IdView("embeddableTypeProperties")
    val embeddableTypePropertyIds: List<Int>
}
