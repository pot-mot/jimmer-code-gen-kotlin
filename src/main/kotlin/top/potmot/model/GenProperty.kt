package top.potmot.model

import org.babyfish.jimmer.sql.*
import top.potmot.enum.AssociationType
import top.potmot.model.base.BaseEntity

/**
 * 生成属性实体类
 *
 * @author potmot
 * @since 2023-08-12 10:50:21
 */
@Entity
@Table(name = "jimmer_code_gen.gen_property")
interface GenProperty : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 对应列 ID
     */
    @IdView
    val columnId: Long?

    /**
     * 对应列
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val column: GenColumn?

    /**
     * 归属实体编号
     */
    @IdView
    val entityId: Long

    /**
     * 归属实体
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val entity: GenEntity

    /**
     * 属性名
     */
    @Key
    val name: String


    /**
     * 属性注释
     */
    val comment: String

    /**
     * 属性类型
     */
    val type: String

    /**
     * 类型对应表
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val typeTable: GenTable?

    /**
     * 类型对应表 ID 视图
     */
    @IdView
    val typeTableId: Long?

    /**
     * 是否列表
     */
    val isList: Boolean

    /**
     * 是否非空（1是）
     */
    val isNotNull: Boolean

    /**
     * 是否Id（1是）
     */
    val isId: Boolean

    /**
     * Id 生成类型
     */
    val idGenerationType: GenerationType?

    /**
     * 是否为业务键属性（1是）
     */
    val isKey: Boolean

    /**
     * 是否为逻辑删除属性（1是）
     */
    val isLogicalDelete: Boolean

    /**
     * 是否为 ID 视图属性（1是）
     */
    val isIdView: Boolean

    /**
     * ID 视图注释
     */
    val idViewAnnotation: String?

    /**
     * 关联类型
     */
    val associationType: AssociationType?

    /**
     * 关联注释
     */
    val associationAnnotation: String?

    /**
     * 脱钩注释
     */
    val dissociateAnnotation: String?

    /**
     * 其他注释
     */
    val otherAnnotation: String?

    /**
     * 对应枚举 ID
     */
    val enumId: Long?
}

