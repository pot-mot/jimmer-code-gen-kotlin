package top.potmot.entity.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.MappedSuperclass
import org.hibernate.validator.constraints.Length

/**
 * 列类型信息
 * 
 * @author potmot
 */
@MappedSuperclass
interface GenColumnTypeInfo {
    /**
     * JdbcType 码值
     */
    @Column(name = "jdbc_type_code")
    val jdbcTypeCode: Int

    /**
     * 字面类型
     */
    @Column(name = "raw_type")
    @get:Length(max = 500)
    val rawType: String

    /**
     * 类型是否非空
     */
    @Column(name = "type_is_not_null")
    val typeIsNotNull: Boolean

    /**
     * 长度
     */
    @Column(name = "data_size")
    val dataSize: Int?

    /**
     * 精度
     */
    @Column(name = "numeric_precision")
    val numericPrecision: Int?

    /**
     * 列默认表达式
     */
    @Column(name = "column_default_exp")
    @get:Length(max = 500)
    val columnDefaultExp: String?
}
