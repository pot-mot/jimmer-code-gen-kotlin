package top.potmot.entity.database

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 表索引
 * 
 * @author potmot
 */
@Entity
@Table(name = "db_table_index")
interface DbTableIndex {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 归属表
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
     * 归属表 ID View
     */
    @IdView("table")
    val tableId: Int

    /**
     * 名称
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 是否是唯一索引
     */
    @Column(name = "unique_index")
    val uniqueIndex: Boolean

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String

    /**
     * 列
     */
    @ManyToMany(orderedProps = [OrderedProp("id")])
    @JoinTable(
        name = "db_table_index_db_table_column_mapping",
        joinColumnName = "db_table_index_id",
        inverseJoinColumnName = "db_table_column_id"
    )
    @get:Valid
    val dbTableColumns: List<DbTableColumn>

    /**
     * 列 ID View
     */
    @IdView("dbTableColumns")
    val dbTableColumnIds: List<Int>
}
