package top.potmot.entity

import org.babyfish.jimmer.Formula
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Serialized
import org.babyfish.jimmer.sql.Table
import top.potmot.core.entity.meta.JoinColumnMeta
import top.potmot.core.entity.meta.JoinTableMeta
import top.potmot.enumeration.AssociationType
import top.potmot.entity.base.BaseEntity
import top.potmot.entity.dto.PropertyOtherAnnotation

/**
 * 生成属性
 *
 * @author potmot
 * @since 2023-08-12 10:50:21
 */
@Entity
@Table(name = "gen_property")
interface GenProperty : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

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
     * 覆盖自动生成属性名
     */
    val overwriteName: Boolean


    /**
     * 属性注释
     */
    val comment: String

    /**
     * 覆盖自动生成注释
     */
    val overwriteComment: Boolean

    /**
     * 字面类型
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
    val listType: Boolean

    /**
     * 是否非空
     */
    val typeNotNull: Boolean

    /**
     * 是否 ID 属性
     */
    val idProperty: Boolean

    /**
     * ID 生成类型
     */
    val idGenerationAnnotation: String?

    /**
     * 是否为业务键属性
     */
    val keyProperty: Boolean

    /**
     * 业务键组
     */
    val keyGroup: String?

    @Formula(dependencies = ["keyGroup"])
    val keyGroups: List<String>
        get() = keyGroup?.split(",")?.map { it.trim() }?.distinct()?.sorted() ?: emptyList()

    /**
     * 是否为逻辑删除属性
     */
    val logicalDelete: Boolean

    /**
     * 是否为 ID 视图属性
     */
    val idView: Boolean

    /**
     * ID 视图注解
     */
    val idViewTarget: String?

    /**
     * 关联类型
     */
    val associationType: AssociationType?

    /**
     * 是否为长关联
     */
    val longAssociation: Boolean

    /**
     * 映射镜像
     */
    val mappedBy: String?

    /**
     * 输入非空
     */
    val inputNotNull: Boolean?

    /**
     * 关联列注解
     */
    @Serialized
    val joinColumnMetas: List<JoinColumnMeta>?

    /**
     * 关联表注解
     */
    @Serialized
    val joinTableMeta: JoinTableMeta?

    /**
     * 脱钩注解
     */
    val dissociateAnnotation: String?

    /**
     * 其他注解
     */
    @Serialized
    val otherAnnotation: PropertyOtherAnnotation?

    /**
     * 排序键
     */
    val orderKey: Long

        /**
     * 是否在列表视图DTO中
     */
    val inListView: Boolean

    /**
     * 是否在详情视图DTO中
     */
    val inDetailView: Boolean

    /**
     * 是否在新增入参DTO中
     */
    val inInsertInput: Boolean

    /**
     * 是否在修改入参DTO中
     */
    val inUpdateInput: Boolean

    /**
     * 是否在查询规格DTO中
     */
    val inSpecification: Boolean

    /**
     * 是否在选项视图DTO中
     */
    val inOptionView: Boolean

    /**
     * 是否在短关联视图DTO中
     */
    val inShortAssociationView: Boolean

    /**
     * 是否在长关联视图DTO中
     */
    val inLongAssociationView: Boolean

    /**
     * 是否在长关联入参DTO中
     */
    val inLongAssociationInput: Boolean

    /**
     * 备注
     */
    val remark: String

    /**
     * 生成枚举 ID 视图
     */
    @IdView
    val enumId: Long?

    /**
     * 生成枚举
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val enum: GenEnum?
}

