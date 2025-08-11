package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * Join Table 逻辑删除过滤器
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_join_table_logical_delete_filter")
interface GenJoinTableLogicalDeleteFilter {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 列名称
     */
    @Column(name = "column_name")
    @get:Length(max = 255)
    val columnName: String

    /**
     * 类型
     */
    @Column(name = "type")
    @get:Length(max = 255)
    val type: String

    /**
     * 值
     */
    @Column(name = "values")
    @get:Length(max = 255)
    val values: String

    /**
     * 逻辑删除注解
     */
    @Column(name = "logical_deleted_annotation")
    @get:Length(max = 500)
    val logicalDeletedAnnotation: String

    /**
     * 可空
     */
    @Column(name = "nullable")
    val nullable: Boolean

    /**
     * Join Table
     * 
     * @see top.potmot.entity.model.associations.GenJoinTable.logicalDeleteFilter
     */
    @OneToMany(mappedBy = "logicalDeleteFilter", orderedProps = [OrderedProp("id")])
    @get:Valid
    val joinTables: List<GenJoinTable>

    /**
     * Join Table ID View
     */
    @IdView("joinTables")
    val joinTableIds: List<Int>
}
