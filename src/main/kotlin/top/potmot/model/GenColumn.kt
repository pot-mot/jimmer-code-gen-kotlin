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
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val table: GenTable?

    /**
     * 列在表中顺序
     */
    val orderKey: Long

    /**
     * 列名称
     */
    @Key
    val name: String

    /**
     * 列对应 JDBCType 码值
     */
    val typeCode: Int

    /**
     * 列类型
     */
    val type: String

    /**
     * 列展示长度
     */
    val displaySize: Long

    /**
     * 列精度
     */
    val numericPrecision: Long

    /**
     * 列默认值
     */
    val defaultValue: String?

    /**
     * 列注释
     */
    val comment: String

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
