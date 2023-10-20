package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Table
import top.potmot.model.base.BaseEntity

/**
 * 列到属性类型映射实体类
 *
 * @author potmot
 * @since 2023-08-12 10:51:39
 */
@Entity
@Table(name = "jimmer_code_gen.gen_type_mapping")
interface GenTypeMapping : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 列类型表达式
     */
    val typeExpression: String

    /**
     * 是否正则
     */
    val regex: Boolean

    /**
     * 属性类型
     */
    val propertyType: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

