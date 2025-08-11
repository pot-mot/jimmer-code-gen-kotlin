package top.potmot.entity.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 类型对
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_type_pair")
interface GenTypePair {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    /**
     * 数据源类型
     */
    @Column(name = "database_type")
    @get:Length(max = 500)
    val databaseType: String

    /**
     * 列类型
     */
    @Column(name = "column_type")
    @get:Length(max = 500)
    val columnType: String

    /**
     * 语言
     */
    @Column(name = "language")
    @get:Length(max = 500)
    val language: String

    /**
     * 属性类型
     */
    @Column(name = "property_type")
    @get:Length(max = 500)
    val propertyType: String

    /**
     * 排序键
     */
    @Column(name = "order_key")
    val orderKey: Int

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String
}
