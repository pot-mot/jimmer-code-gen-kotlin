package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length

/**
 * Join Table 逻辑删除过滤器
 * 
 * @author potmot
 */
@Entity
interface GenJoinTableLogicalDeleteFilter {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 列名称
     */
    @get:Length(max = 255)
    val columnName: String

    /**
     * 类型
     */
    @get:Length(max = 255)
    val type: String

    /**
     * 值
     */
    @get:Length(max = 255)
    val values: String

    /**
     * 逻辑删除注解
     */
    @get:Length(max = 500)
    val logicalDeletedAnnotation: String

    /**
     * 可空
     */
    val nullable: Boolean

    /**
     * Join Table
     * 
     * @see top.potmot.entity.model.associations.GenJoinTable.logicalDeleteFilter
     */
    @OneToMany(mappedBy = "logicalDeleteFilter", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genJoinTables: List<GenJoinTable>

    /**
     * Join Table ID View
     */
    @IdView("genJoinTables")
    val genJoinTableIds: List<Int>
}
