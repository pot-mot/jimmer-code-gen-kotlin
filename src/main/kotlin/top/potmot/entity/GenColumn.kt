package top.potmot.entity

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table
import top.potmot.entity.base.BaseEntity

/**
 * 生成列
 *
 * @author potmot
 * @since 2023-08-12 10:49:27
 */
@Entity
@Table(name = "jimmer_code_gen.gen_column")
interface GenColumn : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 归属表
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val table: GenTable

    /**
     * 归属表 ID 视图
     */
    @IdView
    val tableId: Long

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * JdbcType 码值
     */
    val typeCode: Int

    /**
     * 覆盖为字面类型
     */
    val overwriteByRaw: Boolean

    /**
     * 字面类型
     */
    val rawType: String

    /**
     * 是否非空
     */
    val typeNotNull: Boolean

    /**
     * 长度
     */
    val dataSize: Long

    /**
     * 精度
     */
    val numericPrecision: Long

    /**
     * 默认值
     */
    val defaultValue: String?

    /**
     * 注释
     */
    val comment: String

    /**
     * 是否为主键的部分
     */
    val partOfPk: Boolean

    /**
     * 是否自增
     */
    val autoIncrement: Boolean

    /**
     * 是否为业务键
     */
    val businessKey: Boolean

    /**
     * 是否为逻辑删除
     */
    val logicalDelete: Boolean

    /**
     * 排序键
     */
    val orderKey: Long

    /**
     * 备注
     */
    val remark: String

    /**
     * 生成枚举
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val enum: GenEnum?

    /**
     * 生成枚举 ID 视图
     */
    @IdView("enum")
    val enumId: Long?

    /**
     * 唯一索引
     */
    @ManyToMany(mappedBy = "columns")
    val indexes: List<GenTableIndex>

    /**
     * 入关联
     */
    @OneToMany(mappedBy = "targetColumn")
    val inColumnReferences: List<GenColumnReference>

    /**
     * 出关联
     */
    @OneToMany(mappedBy = "sourceColumn")
    val outColumnReferences: List<GenColumnReference>

    /**
     * 对应属性
     */
    @OneToMany(mappedBy = "column")
    val properties: List<GenProperty>

    /**
     * 对应属性 ID 视图
     */
    @IdView("properties")
    val propertyIds: List<Long>
}

