package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
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
@Table(name = "gen_data_source_column_type")
interface GenDataSourceColumnType : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 数据源类型
     */
    val dataSourceType: DataSourceType

    /**
     * JDBCType 码值
     */
    val typeCode: Int

    /**
     * 数据库类型表达式
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
     * 默认值
     */
    val defaultValue: String?

    /**
     * 自定排序
     */
    val orderKey: Long
}
