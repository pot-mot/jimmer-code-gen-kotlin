package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * Join Table 过滤器
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_join_table_filter")
interface GenJoinTableFilter {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * Join Table
     */
    @ManyToOne
    @JoinColumn(
        name = "join_table_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val joinTable: GenJoinTable

    /**
     * Join Table ID View
     */
    @IdView("joinTable")
    val joinTableId: Int

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
}
