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
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.GenColumnTypeInfo

/**
 * Join Table Join Column
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_join_table_column")
interface GenJoinTableColumn : GenColumnTypeInfo {
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
    @Column(name = "column_name")
    @get:Length(max = 255)
    val columnName: String

    /**
     * 引用列名称
     */
    @Column(name = "reference_column_name")
    @get:Length(max = 255)
    val referenceColumnName: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 255)
    val comment: String

    /**
     * 是否伪外键
     */
    @Column(name = "fake")
    val fake: Boolean
}
