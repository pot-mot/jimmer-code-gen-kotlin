package top.potmot.model

import org.babyfish.jimmer.sql.*
import kotlin.Long
import kotlin.collections.List
import top.potmot.model.base.BaseEntity
import kotlin.String

/**
 * 生成枚举
 *
 * @author
 * @since 2023-09-30T19:31:11.933
 */
@Entity
interface GenEnum : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 生成枚举元素
     */
    @OneToMany(mappedBy = "enum")
    val enumItems: List<GenEnumItem>

    /**
     * 生成枚举元素 ID 视图
     */
    @IdView("enumItems")
    val enumItemIds: List<Long>

    /**
     * 生成属性
     */
    @OneToMany(mappedBy = "enum")
    val properties: List<GenProperty>

    /**
     * 生成属性 ID 视图
     */
    @IdView("properties")
    val propertyIds: List<Long>

    /**
     * 生成包
     */
    @ManyToOne
    @JoinColumn(name = "package_id")
    @OnDissociate(DissociateAction.SET_NULL)
    val genPackage: GenPackage?

    /**
     * 生成包 ID 视图
     */
    @IdView("genPackage")
    val packageId: Long?

    /**
     * 枚举名
     */
    val name: String?

    /**
     * 枚举注释
     */
    val comment: String?

    /**
     * 自定排序
     */
    val orderKey: Long

}
