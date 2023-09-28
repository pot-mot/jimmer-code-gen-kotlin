package top.potmot.model

import org.babyfish.jimmer.sql.*
import top.potmot.model.base.BaseEntity

/**
 * 生成实体实体类
 *
 * @author potmot
 * @since 2023-08-12 10:48:54
 */
@Entity
@Table(name = "jimmer-code-gen.gen_entity")
interface GenEntity : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 所属包 ID
     */
    @IdView("genPackage")
    val packageId: Long?

    /**
     * 所属包
     */
    @ManyToOne
    @JoinColumn(name = "package_id")
    @OnDissociate(DissociateAction.SET_NULL)
    val genPackage: GenPackage?

    /**
     * 对应表 ID
     */
    @IdView
    val tableId: Long?

    /**
     * 对应表
     */
    @OneToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val table: GenTable?

    /**
     * 类名称
     */
    @Key
    val name: String

    /**
     * 类注释
     */
    val comment: String

    /**
     * 作者
     */
    val author: String

    /**
     * 生成路径（不填默认项目路径）
     */
    val genPath: String

    /**
     * 是否生成添加功能（1是）
     */
    val isAdd: Boolean

    /**
     * 是否生成编辑功能（1是）
     */
    val isEdit: Boolean

    /**
     * 是否生成列表功能（1是）
     */
    val isList: Boolean

    /**
     * 是否生成查询功能（1是）
     */
    val isQuery: Boolean


    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 属性 ID
     */
    @IdView("properties")
    val propertyIds: List<Long>

    /**
     * 属性
     */
    @OneToMany(mappedBy = "entity")
    val properties: List<GenProperty>
}

