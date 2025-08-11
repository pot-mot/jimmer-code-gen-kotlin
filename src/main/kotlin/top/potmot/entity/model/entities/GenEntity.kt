package top.potmot.entity.model.entities

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
import top.potmot.entity.model.entities.properties.GenColumnProperty
import top.potmot.entity.model.entities.properties.GenEmbeddableProperty
import top.potmot.entity.model.entities.properties.GenEnumColumnProperty
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
@Table(name = "gen_entity")
interface GenEntity {
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
     * 接口名称
     */
    @Key
    @Column(name = "interface_name")
    @get:Length(max = 500)
    val interfaceName: String

    /**
     * 抽象超类型
     */
    @Column(name = "mapped_super")
    val mappedSuper: Boolean

    /**
     * 表名称
     */
    @Column(name = "table_name")
    @get:Length(max = 500)
    val tableName: String

    /**
     * 复数名称
     */
    @Column(name = "plural_name")
    @get:Length(max = 500)
    val pluralName: String

    /**
     * 作者
     */
    @Column(name = "author")
    @get:Length(max = 500)
    val author: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 500)
    val comment: String

    /**
     * 子包路径
     */
    @Column(name = "sub_package_path")
    @get:Length(max = 500)
    val subPackagePath: String

    /**
     * 其他注解
     */
    @Column(name = "other_annotations")
    @get:Length(max = 500)
    val otherAnnotations: String

    /**
     * 其他导入
     */
    @Column(name = "other_imports")
    @get:Length(max = 255)
    val otherImports: String

    /**
     * 备注
     */
    @Column(name = "remark")
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
    @Column(name = "x")
    val x: Double

    /**
     * Y坐标
     */
    @Column(name = "y")
    val y: Double

    /**
     * 列属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenColumnProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val columnProperties: List<GenColumnProperty>

    /**
     * 列属性 ID View
     */
    @IdView("columnProperties")
    val columnPropertyIds: List<Int>

    /**
     * 复合属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenEmbeddableProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableProperties: List<GenEmbeddableProperty>

    /**
     * 复合属性 ID View
     */
    @IdView("embeddableProperties")
    val embeddablePropertyIds: List<Int>

    /**
     * 实体索引
     * 
     * @see top.potmot.entity.model.entities.GenEntityIndex.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entityIndexes: List<GenEntityIndex>

    /**
     * 实体索引 ID View
     */
    @IdView("entityIndexes")
    val entityIndexIds: List<Int>

    /**
     * 实体继承
     * 
     * @see top.potmot.entity.model.entities.GenEntityInherit.child
     */
    @OneToMany(mappedBy = "child", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entityInheritsForChild: List<GenEntityInherit>

    /**
     * 实体继承 ID View
     */
    @IdView("entityInheritsForChild")
    val entityInheritIdsForChild: List<Int>

    /**
     * 实体继承
     * 
     * @see top.potmot.entity.model.entities.GenEntityInherit.parent
     */
    @OneToMany(mappedBy = "parent", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entityInheritsForParent: List<GenEntityInherit>

    /**
     * 实体继承 ID View
     */
    @IdView("entityInheritsForParent")
    val entityInheritIdsForParent: List<Int>

    /**
     * 枚举列属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenEnumColumnProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
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
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val extraPropertiesForEntity: List<GenExtraProperty>

    /**
     * 额外属性 ID View
     */
    @IdView("extraPropertiesForEntity")
    val extraPropertyIdsForEntity: List<Int>

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val extraPropertiesForTypeEntity: List<GenExtraProperty>

    /**
     * 额外属性 ID View
     */
    @IdView("extraPropertiesForTypeEntity")
    val extraPropertyIdsForTypeEntity: List<Int>

    /**
     * 属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val properties: List<GenProperty>

    /**
     * 属性 ID View
     */
    @IdView("properties")
    val propertyIds: List<Int>

    /**
     * 排序属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenSortProperty.entity
     */
    @OneToMany(mappedBy = "entity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val sortProperties: List<GenSortProperty>

    /**
     * 排序属性 ID View
     */
    @IdView("sortProperties")
    val sortPropertyIds: List<Int>

    /**
     * 多对多映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyToManyMappedProperties: List<GenManyToManyMappedProperty>

    /**
     * 多对多映射属性 ID View
     */
    @IdView("manyToManyMappedProperties")
    val manyToManyMappedPropertyIds: List<Int>

    /**
     * 多对多源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManySourceProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyToManySourceProperties: List<GenManyToManySourceProperty>

    /**
     * 多对多源属性 ID View
     */
    @IdView("manyToManySourceProperties")
    val manyToManySourcePropertyIds: List<Int>

    /**
     * 多对一映射属性（对多）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyToOneMappedProperties: List<GenManyToOneMappedProperty>

    /**
     * 多对一映射属性（对多） ID View
     */
    @IdView("manyToOneMappedProperties")
    val manyToOneMappedPropertyIds: List<Int>

    /**
     * 多对一源属性（对单）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneSourceProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyToOneSourceProperties: List<GenManyToOneSourceProperty>

    /**
     * 多对一源属性（对单） ID View
     */
    @IdView("manyToOneSourceProperties")
    val manyToOneSourcePropertyIds: List<Int>

    /**
     * 一对一映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneMappedProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val oneToOneMappedProperties: List<GenOneToOneMappedProperty>

    /**
     * 一对一映射属性 ID View
     */
    @IdView("oneToOneMappedProperties")
    val oneToOneMappedPropertyIds: List<Int>

    /**
     * 一对一源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneSourceProperty.typeEntity
     */
    @OneToMany(mappedBy = "typeEntity", orderedProps = [OrderedProp("id")])
    @get:Valid
    val oneToOneSourceProperties: List<GenOneToOneSourceProperty>

    /**
     * 一对一源属性 ID View
     */
    @IdView("oneToOneSourceProperties")
    val oneToOneSourcePropertyIds: List<Int>
}
