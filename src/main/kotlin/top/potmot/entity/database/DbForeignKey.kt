package top.potmot.entity.database

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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length

/**
 * 外键
 * 
 * @author potmot
 */
@Entity
interface DbForeignKey {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 关联名称
     */
    @get:Length(max = 500)
    val name: String

    /**
     * 主表
     */
    @ManyToOne
    @JoinColumn(
        name = "source_table_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val sourceTable: DbTable

    /**
     * 主表 ID View
     */
    @IdView("sourceTable")
    val sourceTableId: Int

    /**
     * 从表
     */
    @ManyToOne
    @JoinColumn(
        name = "target_table_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val targetTable: DbTable

    /**
     * 从表 ID View
     */
    @IdView("targetTable")
    val targetTableId: Int

    /**
     * 关联类型
     */
    @get:Length(max = 500)
    val type: String

    /**
     * 更新行为
     */
    @get:Length(max = 500)
    val updateAction: String

    /**
     * 删除行为
     */
    @get:Length(max = 500)
    val deleteAction: String

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 外键列引用
     * 
     * @see top.potmot.entity.database.DbForeignKeyColumnReference.association
     */
    @OneToMany(mappedBy = "association", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbForeignKeyColumnReferences: List<DbForeignKeyColumnReference>

    /**
     * 外键列引用 ID View
     */
    @IdView("dbForeignKeyColumnReferences")
    val dbForeignKeyColumnReferenceIds: List<Int>
}
