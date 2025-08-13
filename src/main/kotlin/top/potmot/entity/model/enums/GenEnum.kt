package top.potmot.entity.model.enums

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
import top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty
import top.potmot.entity.model.entities.properties.GenExtraProperty
import top.potmot.entity.model.entities.properties.GenProperty
import top.potmot.enums.model.enums.EnumType

/**
 * 枚举
 * 
 * @author potmot
 */
@Entity
interface GenEnum {
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
     * 枚举名
     */
    @Key
    @get:Length(max = 500)
    val name: String

    /**
     * 枚举注释
     */
    @get:Length(max = 500)
    val comment: String

    /**
     * 枚举类型
     */
    val enumType: EnumType

    /**
     * 子包路径
     */
    @get:Length(max = 500)
    val subPackagePath: String

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 枚举元素
     * 
     * @see top.potmot.entity.model.enums.GenEnumItem.enum
     */
    @OneToMany(mappedBy = "enum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEnumItems: List<GenEnumItem>

    /**
     * 枚举元素 ID View
     */
    @IdView("genEnumItems")
    val genEnumItemIds: List<Int>

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.typeEnum
     */
    @OneToMany(mappedBy = "typeEnum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genExtraProperties: List<GenExtraProperty>

    /**
     * 额外属性 ID View
     */
    @IdView("genExtraProperties")
    val genExtraPropertyIds: List<Int>

    /**
     * 复合类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty.typeEnum
     */
    @OneToMany(mappedBy = "typeEnum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypeProperties: List<GenEmbeddableTypeProperty>

    /**
     * 复合类型属性 ID View
     */
    @IdView("genEmbeddableTypeProperties")
    val genEmbeddableTypePropertyIds: List<Int>

    /**
     * 属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenProperty.typeEnum
     */
    @OneToMany(mappedBy = "typeEnum", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genProperties: List<GenProperty>

    /**
     * 属性 ID View
     */
    @IdView("genProperties")
    val genPropertyIds: List<Int>
}
