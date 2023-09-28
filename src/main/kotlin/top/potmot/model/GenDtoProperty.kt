package top.potmot.model

import org.babyfish.jimmer.sql.*
import top.potmot.enum.QueryType
import top.potmot.enum.SortDirection
import top.potmot.model.base.BaseEntity

/**
 * 生成 DTO 属性实体类
 *
 * @author potmot
 * @since 2023-08-12 10:50:21
 */
@Entity
@Table(name = "jimmer_code_gen.gen_dto_property")
interface GenDtoProperty : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 对应 DTO
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val dto: GenDto

    /**
     * 对应 DTO ID
     */
    @IdView
    val dtoId: Long

    /**
     * 对应属性
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val property: GenProperty

    /**
     * 对应属性 ID
     */
    @IdView
    val propertyId: Long

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
     * 是否为排序属性（1是）
     */
    val isSort: Boolean

    /**
     * 排序方向（0 ASC 1 DESC）
     */
    val sortDirection: SortDirection

}

