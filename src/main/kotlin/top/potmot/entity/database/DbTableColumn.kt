package top.potmot.entity.database

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 列
 * 
 * @author potmot
 */
@Entity
@Table(name = "db_table_column")
interface DbTableColumn {
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
     * 排序键
     */
    @Column(name = "order_key")
    @get:Max(value = 2147483647, message = "排序键不可大于2147483647")
    val orderKey: Int

    /**
     * JdbcType 码值
     */
    @Column(name = "jdbc_type_code")
    @get:Max(value = 2147483647, message = "JdbcType 码值不可大于2147483647")
    @get:Min(value = 0, message = "JdbcType 码值不可小于0")
    val jdbcTypeCode: Int

    /**
     * 字面类型
     */
    @Column(name = "raw_type")
    @get:Length(max = 500)
    val rawType: String

    /**
     * 类型是否非空
     */
    @Column(name = "type_is_not_null")
    val typeIsNotNull: Boolean

    /**
     * 长度
     */
    @Column(name = "data_size")
    @get:Max(value = 9223372036854775807, message = "长度不可大于9223372036854775807")
    @get:Min(value = 0, message = "长度不可小于0")
    val dataSize: Long?

    /**
     * 精度
     */
    @Column(name = "numeric_precision")
    @get:Max(value = 9223372036854775807, message = "精度不可大于9223372036854775807")
    @get:Min(value = 0, message = "精度不可小于0")
    val numericPrecision: Long?

    /**
     * 列默认值
     */
    @Column(name = "default_value")
    @get:Length(max = 500)
    val defaultValue: String?

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 500)
    val comment: String

    /**
     * 备注
     */
    @Column(name = "remark")
    @get:Length(max = 500)
    val remark: String

    /**
     * 主键
     */
    @ManyToMany(orderedProps = [OrderedProp("id")])
    @JoinTable(
        name = "db_table_column_db_primary_key_mapping",
        joinColumnName = "db_table_column_id",
        inverseJoinColumnName = "db_primary_key_id"
    )
    @get:Valid
    val dbPrimaryKeys: List<DbPrimaryKey>

    /**
     * 主键 ID View
     */
    @IdView("dbPrimaryKeys")
    val dbPrimaryKeyIds: List<Int>

    /**
     * 表索引
     * 
     * @see top.potmot.entity.database.DbTableIndex.dbTableColumns
     */
    @ManyToMany(mappedBy = "dbTableColumns", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbTableIndexes: List<DbTableIndex>

    /**
     * 表索引 ID View
     */
    @IdView("dbTableIndexes")
    val dbTableIndexIds: List<Int>

    /**
     * 外键列引用
     * 
     * @see top.potmot.entity.database.DbForeignKeyColumnReference.sourceColumn
     */
    @OneToMany(mappedBy = "sourceColumn", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbForeignKeyColumnReferencesForSourceColumn: List<DbForeignKeyColumnReference>

    /**
     * 外键列引用 ID View
     */
    @IdView("dbForeignKeyColumnReferencesForSourceColumn")
    val dbForeignKeyColumnReferenceIdsForSourceColumn: List<Int>

    /**
     * 外键列引用
     * 
     * @see top.potmot.entity.database.DbForeignKeyColumnReference.targetColumn
     */
    @OneToMany(mappedBy = "targetColumn", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbForeignKeyColumnReferencesForTargetColumn: List<DbForeignKeyColumnReference>

    /**
     * 外键列引用 ID View
     */
    @IdView("dbForeignKeyColumnReferencesForTargetColumn")
    val dbForeignKeyColumnReferenceIdsForTargetColumn: List<Int>
}
