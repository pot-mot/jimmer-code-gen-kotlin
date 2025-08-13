package top.potmot.entity.model.entities.properties

import jakarta.validation.Valid
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
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.entities.GenEntity
import top.potmot.entity.model.entities.GenEntityIndex

/**
 * 属性
 * 
 * @author potmot
 */
@Entity
interface GenProperty : ScalarPropertyTypeInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 额外属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenExtraProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genExtraProperty: GenExtraProperty?

    /**
     * 额外属性 ID View
     */
    @IdView("genExtraProperty")
    val genExtraPropertyId: Int?

    /**
     * ID属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenIdProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genIdProperty: GenIdProperty?

    /**
     * ID属性 ID View
     */
    @IdView("genIdProperty")
    val genIdPropertyId: Int?

    /**
     * 逻辑删除属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenLogicalDeleteProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genLogicalDeleteProperty: GenLogicalDeleteProperty?

    /**
     * 逻辑删除属性 ID View
     */
    @IdView("genLogicalDeleteProperty")
    val genLogicalDeletePropertyId: Int?

    /**
     * 多对多映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManyMappedProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genManyToManyMappedProperty: GenManyToManyMappedProperty?

    /**
     * 多对多映射属性 ID View
     */
    @IdView("genManyToManyMappedProperty")
    val genManyToManyMappedPropertyId: Int?

    /**
     * 多对多源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToManySourceProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genManyToManySourceProperty: GenManyToManySourceProperty?

    /**
     * 多对多源属性 ID View
     */
    @IdView("genManyToManySourceProperty")
    val genManyToManySourcePropertyId: Int?

    /**
     * 多对一映射属性（对多）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneMappedProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genManyToOneMappedProperty: GenManyToOneMappedProperty?

    /**
     * 多对一映射属性（对多） ID View
     */
    @IdView("genManyToOneMappedProperty")
    val genManyToOneMappedPropertyId: Int?

    /**
     * 多对一源属性（对单）
     * 
     * @see top.potmot.entity.model.entities.properties.GenManyToOneSourceProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genManyToOneSourceProperty: GenManyToOneSourceProperty?

    /**
     * 多对一源属性（对单） ID View
     */
    @IdView("genManyToOneSourceProperty")
    val genManyToOneSourcePropertyId: Int?

    /**
     * 一对一映射属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneMappedProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genOneToOneMappedProperty: GenOneToOneMappedProperty?

    /**
     * 一对一映射属性 ID View
     */
    @IdView("genOneToOneMappedProperty")
    val genOneToOneMappedPropertyId: Int?

    /**
     * 一对一源属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenOneToOneSourceProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genOneToOneSourceProperty: GenOneToOneSourceProperty?

    /**
     * 一对一源属性 ID View
     */
    @IdView("genOneToOneSourceProperty")
    val genOneToOneSourcePropertyId: Int?

    /**
     * 排序属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenSortProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genSortProperty: GenSortProperty?

    /**
     * 排序属性 ID View
     */
    @IdView("genSortProperty")
    val genSortPropertyId: Int?

    /**
     * 乐观锁属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenVersionProperty.property
     */
    @OneToOne(mappedBy = "property")
    @get:Valid
    val genVersionProperty: GenVersionProperty?

    /**
     * 乐观锁属性 ID View
     */
    @IdView("genVersionProperty")
    val genVersionPropertyId: Int?

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
    @get:Length(max = 500)
    val name: String

    /**
     * 注释
     */
    @get:Length(max = 500)
    val comment: String

    /**
     * 其他注解
     */
    @get:Length(max = 500)
    val extraAnnotations: String?

    /**
     * 其他导入
     */
    @get:Length(max = 255)
    val extraImports: String?

    /**
     * 其他验证器
     */
    @get:Length(max = 255)
    val extraValidations: String?

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 排序键
     */
    val orderKey: Int

    /**
     * 实体索引
     * 
     * @see top.potmot.entity.model.entities.GenEntityIndex.genProperties
     */
    @ManyToMany(mappedBy = "genProperties", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEntityIndexes: List<GenEntityIndex>

    /**
     * 实体索引 ID View
     */
    @IdView("genEntityIndexes")
    val genEntityIndexIds: List<Int>
}
