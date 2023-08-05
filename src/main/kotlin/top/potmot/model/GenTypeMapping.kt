package top.potmot.model

import org.babyfish.jimmer.Formula
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id

/**
 * 实体类
 *
 * @author potmot
 * @since 2023-08-04 13:31:17
 */
@Entity
interface GenTypeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 列类型表达式
     */
    val columnType: String

    /**
     * 是否正则（1是）
     */
    val isRegex: Boolean

    /**
     * 字段类型
     */
    val fieldType: String

    /**
     * 自定排序
     */
    val orderKey: Long

    @Formula(dependencies = ["columnType"])
    val columnTypeRegex: Regex
        get() = Regex(columnType)
}

