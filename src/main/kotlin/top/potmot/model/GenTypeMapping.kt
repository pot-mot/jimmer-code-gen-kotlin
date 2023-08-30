package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import top.potmot.model.base.BaseEntity

/**
 * 列到字段类型映射实体类
 *
 * @author potmot
 * @since 2023-08-12 10:51:39
 */
@Entity
interface GenTypeMapping: BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 列类型表达式
     */
    val typeExpression: String

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

