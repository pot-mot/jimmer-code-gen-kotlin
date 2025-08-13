package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.hibernate.validator.constraints.Length

/**
 * Join Table 过滤器
 * 
 * @author potmot
 */
@Entity
interface GenJoinTableFilter {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
