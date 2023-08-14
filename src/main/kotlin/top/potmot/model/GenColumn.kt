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
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.model.base.BaseEntity

/**
 * 生成列实体类
 *
 * @author potmot
 * @since 2023-08-12 10:49:27
 */
@Entity
interface GenColumn: BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 对应属性 ID
     */
    @IdView("properties")
    val propertyIds: List<Long>

    /**
     * 对应属性
     */
    @OneToMany(mappedBy = "column")
    val properties: List<GenProperty>

    /**
     * 归属表编号
     */
    @IdView
    val tableId: Long?

    /**
     * 归属表
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val table: GenTable?

    /**
     * 列在表中顺序
     */
    val columnSort: Long

    /**
     * 列名称
     */
    @Key
    val columnName: String

    /**
     * 列对应 JDBCType 码值
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
    val columnDefault: String?

    /**
     * 列注释
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
     * 是否外键（1是）
     */
    val isFk: Boolean

    /**
     * 是否唯一索引（1是）
     */
    val isUnique: Boolean

    /**
     * 是否非空（1是）
     */
    val isNotNull: Boolean

    /**
     * 入关联
     */
    @OneToMany(mappedBy = "targetColumn")
    val inAssociations: List<GenAssociation>

    /**
     * 出关联
     */
    @OneToMany(mappedBy = "sourceColumn")
    val outAssociations: List<GenAssociation>
}

