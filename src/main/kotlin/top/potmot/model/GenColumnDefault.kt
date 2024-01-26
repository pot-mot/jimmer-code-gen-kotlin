package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.DataSourceType
import top.potmot.model.base.BaseEntity

/**
 * 数据源列类型
 *
 * @author
 * @since 2023-12-21 17:56:03
 */
@Entity
@Table(name = "jimmer_code_gen.gen_column_default")
interface GenColumnDefault : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 数据源类型
     */
    @Key
    val dataSourceType: DataSourceType

    /**
     * JdbcType 码值
     */
    @Key
    val typeCode: Int

    /**
     * 数据库类型表达式
     */
    val rawType: String

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
     * 排序键
     */
    val orderKey: Long

    /**
     * 备注
     */
    val remark: String
}
