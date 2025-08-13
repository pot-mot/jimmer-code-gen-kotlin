package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.hibernate.validator.constraints.Length

/**
 * Join Table Join Column
 * 
 * @author potmot
 */
@Entity
interface GenJoinTableColumn {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * Join Table
     */
    @Key
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
     * 本地列名称
     */
    @Key
    @get:Length(max = 255)
    val columnName: String

    /**
     * 引用列名称
     */
    @get:Length(max = 255)
    val referenceColumnName: String

    /**
     * 注释
     */
    @get:Length(max = 255)
    val comment: String

    /**
     * 是否伪外键
     */
    val fake: Boolean
}
