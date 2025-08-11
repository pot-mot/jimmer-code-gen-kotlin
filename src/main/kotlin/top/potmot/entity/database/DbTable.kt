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
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.enums.database.TableType

/**
 * @author potmot
 */
@Entity
@Table(name = "db_table")
interface DbTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 数据架构
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "schema_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val schema: DbSchema

    /**
     * 数据架构 ID View
     */
    @IdView("schema")
    val schemaId: Int

    /**
     * 名称
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 500)
    val comment: String

    /**
     * 类型
     */
    @Column(name = "type")
    val type: TableType

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String

    /**
     * 外键
     * 
     * @see top.potmot.entity.database.DbForeignKey.sourceTable
     */
    @OneToMany(mappedBy = "sourceTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbForeignKeysForSourceTable: List<DbForeignKey>

    /**
     * 外键 ID View
     */
    @IdView("dbForeignKeysForSourceTable")
    val dbForeignKeyIdsForSourceTable: List<Int>

    /**
     * 外键
     * 
     * @see top.potmot.entity.database.DbForeignKey.targetTable
     */
    @OneToMany(mappedBy = "targetTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbForeignKeysForTargetTable: List<DbForeignKey>

    /**
     * 外键 ID View
     */
    @IdView("dbForeignKeysForTargetTable")
    val dbForeignKeyIdsForTargetTable: List<Int>

    /**
     * 主键
     * 
     * @see top.potmot.entity.database.DbPrimaryKey.table
     */
    @OneToMany(mappedBy = "table", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbPrimaryKeys: List<DbPrimaryKey>

    /**
     * 主键 ID View
     */
    @IdView("dbPrimaryKeys")
    val dbPrimaryKeyIds: List<Int>

    /**
     * 列
     * 
     * @see top.potmot.entity.database.DbTableColumn.table
     */
    @OneToMany(mappedBy = "table", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbTableColumns: List<DbTableColumn>

    /**
     * 列 ID View
     */
    @IdView("dbTableColumns")
    val dbTableColumnIds: List<Int>

    /**
     * 表索引
     * 
     * @see top.potmot.entity.database.DbTableIndex.table
     */
    @OneToMany(mappedBy = "table", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbTableIndexes: List<DbTableIndex>

    /**
     * 表索引 ID View
     */
    @IdView("dbTableIndexes")
    val dbTableIndexIds: List<Int>
}
