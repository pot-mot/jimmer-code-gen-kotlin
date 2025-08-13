package top.potmot.entity.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.hibernate.validator.constraints.Length
import top.potmot.enums.database.DatabaseType
import top.potmot.enums.model.DevLanguage

/**
 * 类型对
 * 
 * @author potmot
 */
@Entity
interface GenTypePair {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 数据源类型
     */
    val databaseType: DatabaseType?

    /**
     * JdbcType 码值
     */
    val jdbcTypeCode: Int

    /**
     * 字面类型
     */
    @get:Length(max = 500)
    val rawType: String

    /**
     * 默认长度
     */
    val defaultDataSize: Int?

    /**
     * 默认精度
     */
    val defaultNumericPrecision: Int?

    /**
     * 语言
     */
    val language: DevLanguage?

    /**
     * 属性类型
     */
    @get:Length(max = 500)
    val propertyType: String

    /**
     * 排序键
     */
    val orderKey: Int

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String
}
