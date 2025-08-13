package top.potmot.entity.database

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
 * 外键列引用
 * 
 * @author potmot
 */
@Entity
interface DbForeignKeyColumnReference {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 关联
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "association_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val association: DbForeignKey

    /**
     * 关联 ID View
     */
    @IdView("association")
    val associationId: Int

    /**
     * 主列
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "source_column_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val sourceColumn: DbTableColumn

    /**
     * 主列 ID View
     */
    @IdView("sourceColumn")
    val sourceColumnId: Int

    /**
     * 从列
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "target_column_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val targetColumn: DbTableColumn

    /**
     * 从列 ID View
     */
    @IdView("targetColumn")
    val targetColumnId: Int

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String
}
