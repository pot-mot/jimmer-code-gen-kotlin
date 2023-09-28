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
     * 属性关联类型
     */
    val associationType: AssociationType?

    /**
     * 属性注解表达式
     */
    val annotationExpression: String?

    /**
     * 对应枚举 ID
     */
    val enumId: Long?
}

