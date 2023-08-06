package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id

/**
 * 列到字段类型映射实体类
 *
 * @author potmot
 * @since 2023-08-06 17:23:23
 */
@Entity
interface GenTypeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 列类型(表达式)
     */
    val columnType: String

    /**
     * 是否正则（1是）
     */
    val isRegex: Boolean

    /**
     * 字段类型
     */
    val propertyType: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

