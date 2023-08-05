package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToManyView
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.constant.QueryType
import top.potmot.constant.SortDirection

/**
 * 代码生成业务表字段实体类
 *
 * @author potmot
 * @since 2023-08-05 10:59:40
 */
@Entity
interface GenTableColumn {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 归属表编号
     */
    @IdView
    val tableId: Long

    /**
     * 归属表
     */
    @ManyToOne
    val table: GenTable

    /**
     * 列在表中顺序
     */
    val columnSort: Long

    /**
     * 列名称
     */
    val columnName: String

    /**
     * 列 JDBCType 码
     */
    val columnTypeCode: Int

    /**
     * 列类型
     */
    val columnType: String

    /**
     * 列展示长度
     */
    val columnDisplaySize: Long

    /**
     * 列精度
     */
    val columnPrecision: Long

    /**
     * 列默认值
     */
    val columnDefault: String

    /**
     * 列描述
     */
    val columnComment: String

    /**
     * 是否主键（1是）
     */
    val isPk: Boolean

    /**
     * 是否自增（1是）
     */
    val isAutoIncrement: Boolean

    /**
     * 是否唯一索引（1是）
     */
    val isUnique: Boolean

    /**
     * 是否非空（1是）
     */
    val isNotNull: Boolean

    /**
     * 是否虚拟列（1是）
     */
    val isVirtualColumn: Boolean

    /**
     * 字段名
     */
    val fieldName: String

    /**
     * 字段类型
     */
    val fieldType: String

    /**
     * 字段描述
     */
    val fieldComment: String

    /**
     * 是否为列表字段（1是）
     */
    val isList: Boolean

    /**
     * 字段在列表中顺序
     */
    val listSort: Long

    /**
     * 是否为添加字段（1是）
     */
    val isAdd: Boolean

    /**
     * 字段在新增表单中顺序
     */
    val addSort: Long

    /**
     * 是否为添加必要字段（1是）
     */
    val isAddRequired: Boolean

    /**
     * 是否为编辑字段（1是）
     */
    val isEdit: Boolean

    /**
     * 字段在修改表单中顺序
     */
    val editSort: Long

    /**
     * 是否为修改必要字段（1是）
     */
    val isEditRequired: Boolean

    /**
     * 是否为只读字段（1是）
     */
    val readOnly: Boolean

    /**
     * 是否为查询字段（1是）
     */
    val isQuery: Boolean

    /**
     * 字段在查询表单中顺序
     */
    val querySort: Long

    /**
     * 查询类型（EQ、NE、GT、GTE、LT、LTE、BETWEEN、IN、LIKE、ILIKE）
     */
    val queryType: QueryType

    /**
     * 字典类型
     */
    val dictType: String

    /**
     * 是否为排序字段（1是）
     */
    val isSort: Boolean

    /**
     * 排序方向（0 ASC 1 DESC）
     */
    val sortDirection: SortDirection

    /**
     * 是否为逻辑删除字段（1是）
     */
    val isLogicalDelete: Boolean

    /**
     * 出关联
     * 本列作为主列的关联，指向另一张表的字段
     * example:
     * book.authorId -> author.id
     */
    @OneToMany(mappedBy = "sourceColumn")
    val outAssociations: List<GenTableAssociation>

    /**
     * 入关联
     * 本列作为从列的关联，被另一张表的字段所指
     * example:
     * author.id <- book.authorId
     */
    @OneToMany(mappedBy = "targetColumn")
    val inAssociations: List<GenTableAssociation>

    /**
     * 本列指向的从列
     * example:
     * book -> author 中的 author.id
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetColumn"
    )
    val targetColumns: List<GenTableColumn>

    /**
     * 指向本列的主列
     * example:
     * author <- book 中的 book.authorId
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceColumn"
    )
    val sourceColumns: List<GenTableColumn>
}

