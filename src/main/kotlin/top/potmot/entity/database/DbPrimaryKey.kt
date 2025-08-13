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
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length

/**
 * 主键
 * 
 * @author potmot
 */
@Entity
interface DbPrimaryKey {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 表
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "table_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val table: DbTable

    /**
     * 表 ID View
     */
    @IdView("table")
    val tableId: Int

    /**
     * 名称
     */
    @get:Length(max = 500)
    val name: String

    /**
     * 对应序列
     */
    @get:Length(max = 255)
    val serialName: String?

    /**
     * 是否自增
     */
    val autoIncrement: Boolean

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 列
     * 
     * @see top.potmot.entity.database.DbTableColumn.dbPrimaryKeys
     */
    @ManyToMany(mappedBy = "dbPrimaryKeys", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbTableColumns: List<DbTableColumn>

    /**
     * 列 ID View
     */
    @IdView("dbTableColumns")
    val dbTableColumnIds: List<Int>
}
