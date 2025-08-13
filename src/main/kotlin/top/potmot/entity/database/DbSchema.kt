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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length

/**
 * 数据架构
 * 
 * @author potmot
 */
@Entity
interface DbSchema {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 数据源
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "data_source_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val dataSource: DbDataSource

    /**
     * 数据源 ID View
     */
    @IdView("dataSource")
    val dataSourceId: Int

    /**
     * 名称
     */
    @Key
    @get:Length(max = 500)
    val name: String

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * @see top.potmot.entity.database.DbTable.schema
     */
    @OneToMany(mappedBy = "schema", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbTables: List<DbTable>

    /**
     * ID
     */
    @IdView("dbTables")
    val dbTableIds: List<Int>
}
