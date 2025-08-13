package top.potmot.entity.model.entities

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
import top.potmot.entity.model.entities.properties.GenExtraProperty
import top.potmot.entity.model.entities.properties.GenIdProperty
import top.potmot.entity.model.entities.properties.GenLogicalDeleteProperty
import top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty
import top.potmot.entity.model.entities.properties.GenManyToManySourceProperty
import top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty
import top.potmot.entity.model.entities.properties.GenManyToOneSourceProperty
import top.potmot.entity.model.entities.properties.GenOneToOneMappedProperty
import top.potmot.entity.model.entities.properties.GenOneToOneSourceProperty
import top.potmot.entity.model.entities.properties.GenProperty
import top.potmot.entity.model.entities.properties.GenSortProperty
import top.potmot.entity.model.entities.properties.GenVersionProperty

/**
 * 实体
 * 
 * @author potmot
 */
@Entity
interface GenEntity {
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
     * 接口名称
     */
    @Key
    @get:Length(max = 500)
    val interfaceName: String

    /**
     * 抽象超类型
     */
    val mappedSuper: Boolean

    /**
     * 表名称
     */
    @get:Length(max = 500)
    val tableName: String

    /**
     * 复数名称
     */
    @get:Length(max = 500)
    val pluralName: String

    /**
     * 作者
     */
    @get:Length(max = 500)
    val author: String

    /**
     * 注释
     */
    @get:Length(max = 500)
    val comment: String

    /**
     * 子包路径
     */
    @get:Length(max = 500)
    val subPackagePath: String

    /**
     * 其他注解
     */
    @get:Length(max = 500)
    val otherAnnotations: String

    /**
     * 其他导入
     */
    @get:Length(max = 255)
    val otherImports: String

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * ID属性
     */
    @ManyToOne
    @JoinColumn(
        name = "id_property_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val idProperty: GenIdProperty?

    /**
     * ID属性 ID View
     */
    @IdView("idProperty")
    val idPropertyId: Int?

    /**
     * 逻辑删除属性
     */
    @ManyToOne
    @JoinColumn(
        name = "logical_delete_property_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val logicalDeleteProperty: GenLogicalDeleteProperty?

    /**
     * 逻辑删除属性 ID View
     */
    @IdView("logicalDeleteProperty")
    val logicalDeletePropertyId: Int?

    /**
     * 乐观锁属性
     */
    @ManyToOne
    @JoinColumn(
        name = "version_property_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val versionProperty: GenVersionProperty?

    /**
     * 乐观锁属性 ID View
     */
    @IdView("versionProperty")
    val versionPropertyId: Int?

    /**
     * X坐标
     */
    val x: Double

    /**
     * Y坐标
     */
    val y: Double

    /**
     * 实体索引
     * 
     * @see top.potmot.entity.model.entities.GenEntityIndex.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEntityIndexes: List<GenEntityIndex>

    /**
     * 实体索引 ID View
     */
    @IdView("genEntityIndexes")
    val genEntityIndexIds: List<Int>

    /**
     * 实体继承
     * 
     * @see top.potmot.entity.model.entities.GenEntityInherit.child
     */
    @OneToMany(mappedBy = "child", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEntityInheritsForChild: List<GenEntityInherit>

    /**
     * 实体继承 ID View
     */
    @IdView("genEntityInheritsForChild")
    val genEntityInheritIdsForChild: List<Int>

    /**
     * 实体继承
     * 
     * @see top.potmot.entity.model.entities.GenEntityInherit.parent
     */
    @OneToMany(mappedBy = "parent", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEntityInheritsForParent: List<GenEntityInherit>

    /**
     * 实体继承 ID View
     */
    @IdView("genEntityInheritsForParent")
    val genEntityInheritIdsForParent: List<Int>

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genExtraProperties: List<GenExtraProperty>

    /**
     * 额外属性 ID View
     */
    @IdView("genExtraProperties")
    val genExtraPropertyIds: List<Int>

    /**
     * 属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genProperties: List<GenProperty>

    /**
     * 属性 ID View
     */
    @IdView("genProperties")
    val genPropertyIds: List<Int>

    /**
     * 排序属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenSortProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genSortProperties: List<GenSortProperty>

    /**
     * 排序属性 ID View
     */
    @IdView("genSortProperties")
    val genSortPropertyIds: List<Int>

    /**
     * 多对多映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToManyMappedProperties: List<GenManyToManyMappedProperty>

    /**
     * 多对多映射属性 ID View
     */
    @IdView("genManyToManyMappedProperties")
    val genManyToManyMappedPropertyIds: List<Int>

    /**
     * 多对多源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManySourceProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToManySourceProperties: List<GenManyToManySourceProperty>

    /**
     * 多对多源属性 ID View
     */
    @IdView("genManyToManySourceProperties")
    val genManyToManySourcePropertyIds: List<Int>

    /**
     * 多对一映射属性（对多）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToOneMappedProperties: List<GenManyToOneMappedProperty>

    /**
     * 多对一映射属性（对多） ID View
     */
    @IdView("genManyToOneMappedProperties")
    val genManyToOneMappedPropertyIds: List<Int>

    /**
     * 多对一源属性（对单）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneSourceProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToOneSourceProperties: List<GenManyToOneSourceProperty>

    /**
     * 多对一源属性（对单） ID View
     */
    @IdView("genManyToOneSourceProperties")
    val genManyToOneSourcePropertyIds: List<Int>

    /**
     * 一对一映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneMappedProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genOneToOneMappedProperties: List<GenOneToOneMappedProperty>

    /**
     * 一对一映射属性 ID View
     */
    @IdView("genOneToOneMappedProperties")
    val genOneToOneMappedPropertyIds: List<Int>

    /**
     * 一对一源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneSourceProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genOneToOneSourceProperties: List<GenOneToOneSourceProperty>

    /**
     * 一对一源属性 ID View
     */
    @IdView("genOneToOneSourceProperties")
    val genOneToOneSourcePropertyIds: List<Int>
}
