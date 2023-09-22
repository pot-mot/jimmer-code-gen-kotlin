package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.enum.QueryType
import top.potmot.enum.SortDirection
import top.potmot.model.base.BaseEntity

/**
 * 生成属性实体类
 *
 * @author potmot
 * @since 2023-08-12 10:50:21
 */
@Entity
interface GenProperty: BaseEntity {
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
     * 是否Id（1是）
     */
    val isId: Boolean

    /**
     * Id 生成类型
     */
    val idGenerationType: String

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
    val propertyAssociationType: String

    /**
     * 属性类型
     */
    val propertyType: String

    /**
     * 属性注解表达式
     */
    val propertyAnnotationExpression: String

    /**
     * 属性注释
     */
    val propertyComment: String

    /**
     * 是否在列表中（1是）
     */
    val isList: Boolean

    /**
     * 在列表中顺序
     */
    val listSort: Long

    /**
     * 是否在新增表单中（1是）
     */
    val isAdd: Boolean

    /**
     * 在新增表单中顺序
     */
    val addSort: Long

    /**
     * 是否为新增时必填（1是）
     */
    val isAddRequired: Boolean

    /**
     * 是否在编辑表单中（1是）
     */
    val isEdit: Boolean

    /**
     * 在编辑表单中顺序
     */
    val editSort: Long

    /**
     * 是否为编辑时必填（1是）
     */
    val isEditRequired: Boolean

    /**
     * 是否为编辑时只读（1是）
     */
    val isEditReadOnly: Boolean

    /**
     * 是否为查询属性（1是）
     */
    val isQuery: Boolean

    /**
     * 在查询属性中顺序
     */
    val querySort: Long

    /**
     * 查询类型
     */
    val queryType: QueryType

    /**
     * 对应枚举 ID
     */
    val enumId: Long

    /**
     * 是否为排序属性（1是）
     */
    val isSort: Boolean

    /**
     * 排序方向（0 ASC 1 DESC）
     */
    val sortDirection: SortDirection

}

