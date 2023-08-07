package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToManyView
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import top.potmot.constant.QueryType
import top.potmot.constant.SortDirection

/**
 * 生成字段实体类
 *
 * @author potmot
 * @since 2023-08-06 17:22:11
 */
@Entity
interface GenProperty {
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
    val columnId: Long

    /**
     * 对应列
     */
    @OneToOne
    @OnDissociate(DissociateAction.DELETE)
    val column: GenColumn

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
    val propertyName: String

    /**
     * 属性类型
     */
    val propertyType: String

    /**
     * 属性描述
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
     * 在修改表单中顺序
     */
    val editSort: Long

    /**
     * 是否为修改时必填（1是）
     */
    val isEditRequired: Boolean

    /**
     * 是否为只读（1是）
     */
    val readOnly: Boolean

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
     * 字典类型
     */
    val dictType: String

    /**
     * 是否为排序属性（1是）
     */
    val isSort: Boolean

    /**
     * 排序方向（0 ASC 1 DESC）
     */
    val sortDirection: SortDirection

    /**
     * 是否为逻辑删除属性（1是）
     */
    val isLogicalDelete: Boolean

    /**
     * 出关联
     * 本列作为主列的关联，指向另一张表的字段
     * example:
     * book.authorId -> author.id
     */
    @OneToMany(mappedBy = "sourceProperty")
    val outAssociations: List<GenAssociation>

    /**
     * 入关联
     * 本列作为从列的关联，被另一张表的字段所指
     * example:
     * author.id <- book.authorId
     */
    @OneToMany(mappedBy = "targetProperty")
    val inAssociations: List<GenAssociation>

    /**
     * 本列指向的从列
     * example:
     * book -> author 中的 author.id
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetProperty"
    )
    val targetProperties: List<GenProperty>

    /**
     * 指向本列的主列
     * example:
     * author <- book 中的 book.authorId
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceProperty"
    )
    val sourceProperties: List<GenProperty>

    /**
     * 本列指向的从表
     * example:
     * book -> author 中的 author.id
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetEntity"
    )
    val targetEntities: List<GenEntity>

    /**
     * 指向本列的主表
     * example:
     * author <- book 中的 book.authorId
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceEntity"
    )
    val sourceEntities: List<GenEntity>
}

