package top.potmot.entity.model.entities.properties

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
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.entities.GenEntity
import top.potmot.entity.model.entities.GenEntityIndex

/**
 * 属性
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_property")
interface GenProperty {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 列属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenColumnProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val columnProperty: GenColumnProperty?

    /**
     * 列属性 ID View
     */
    @IdView("columnProperty")
    val columnPropertyId: Int?

    /**
     * 复合属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenEmbeddableProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val embeddableProperty: GenEmbeddableProperty?

    /**
     * 复合属性 ID View
     */
    @IdView("embeddableProperty")
    val embeddablePropertyId: Int?

    /**
     * 枚举列属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenEnumColumnProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val enumColumnProperty: GenEnumColumnProperty?

    /**
     * 枚举列属性 ID View
     */
    @IdView("enumColumnProperty")
    val enumColumnPropertyId: Int?

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val extraProperty: GenExtraProperty?

    /**
     * 额外属性 ID View
     */
    @IdView("extraProperty")
    val extraPropertyId: Int?

    /**
     * ID属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenIdProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val idProperty: GenIdProperty?

    /**
     * ID属性 ID View
     */
    @IdView("idProperty")
    val idPropertyId: Int?

    /**
     * 逻辑删除属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenLogicalDeleteProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val logicalDeleteProperty: GenLogicalDeleteProperty?

    /**
     * 逻辑删除属性 ID View
     */
    @IdView("logicalDeleteProperty")
    val logicalDeletePropertyId: Int?

    /**
     * 多对多映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val manyToManyMappedProperty: GenManyToManyMappedProperty?

    /**
     * 多对多映射属性 ID View
     */
    @IdView("manyToManyMappedProperty")
    val manyToManyMappedPropertyId: Int?

    /**
     * 多对多源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManySourceProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val manyToManySourceProperty: GenManyToManySourceProperty?

    /**
     * 多对多源属性 ID View
     */
    @IdView("manyToManySourceProperty")
    val manyToManySourcePropertyId: Int?

    /**
     * 多对一映射属性（对多）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val manyToOneMappedProperty: GenManyToOneMappedProperty?

    /**
     * 多对一映射属性（对多） ID View
     */
    @IdView("manyToOneMappedProperty")
    val manyToOneMappedPropertyId: Int?

    /**
     * 多对一源属性（对单）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneSourceProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val manyToOneSourceProperty: GenManyToOneSourceProperty?

    /**
     * 多对一源属性（对单） ID View
     */
    @IdView("manyToOneSourceProperty")
    val manyToOneSourcePropertyId: Int?

    /**
     * 一对一映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneMappedProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val oneToOneMappedProperty: GenOneToOneMappedProperty?

    /**
     * 一对一映射属性 ID View
     */
    @IdView("oneToOneMappedProperty")
    val oneToOneMappedPropertyId: Int?

    /**
     * 一对一源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneSourceProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val oneToOneSourceProperty: GenOneToOneSourceProperty?

    /**
     * 一对一源属性 ID View
     */
    @IdView("oneToOneSourceProperty")
    val oneToOneSourcePropertyId: Int?

    /**
     * 排序属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenSortProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val sortProperty: GenSortProperty?

    /**
     * 排序属性 ID View
     */
    @IdView("sortProperty")
    val sortPropertyId: Int?

    /**
     * 乐观锁属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenVersionProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val versionProperty: GenVersionProperty?

    /**
     * 乐观锁属性 ID View
     */
    @IdView("versionProperty")
    val versionPropertyId: Int?

    /**
     * 实体
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "entity_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val entity: GenEntity

    /**
     * 实体 ID View
     */
    @IdView("entity")
    val entityId: Int

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
     * 实体索引
     * 
     * @see top.potmot.entity.model.entities.GenEntityIndex.properties
     */
    @ManyToMany(mappedBy = "properties", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entityIndexes: List<GenEntityIndex>

    /**
     * 实体索引 ID View
     */
    @IdView("entityIndexes")
    val entityIndexIds: List<Int>
}
