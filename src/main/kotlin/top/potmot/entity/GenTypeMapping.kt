package top.potmot.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.base.BaseEntity

/**
 * 列到属性类型映射
 *
 * @author potmot
 * @since 2023-08-12 10:51:39
 */
@Entity
@Table(name = "gen_type_mapping")
interface GenTypeMapping : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 数据源类型
     */
    @Key
    val dataSourceType: DataSourceType

    /**
     * 语言
     */
    @Key
    val language: GenLanguage

    /**
     * 数据库类型表达式
     */
    @Key
    val typeExpression: String

    /**
     * 属性类型
     */
    @Key
    val propertyType: String

    /**
     * 排序键
     */
    val orderKey: Long

    /**
     * 备注
     */
    val remark: String
}
