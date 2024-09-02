package top.potmot.entity

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import top.potmot.entity.base.BaseEntity

/**
 * 生成列
 *
 * @author potmot
 * @since 2023-08-12 10:49:27
 */
@Entity
@Table(name = "gen_table_index")
interface GenTableIndex : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 归属表
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val table: GenTable

    /**
     * 归属表 ID 视图
     */
    @IdView
    val tableId: Long

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * 索引
     */
    val uniqueIndex: Boolean

    /**
     * 备注
     */
    val remark: String

    /**
     * 列
     */
    @ManyToMany
    @JoinTable(
        name = "gen_index_column_mapping",
        joinColumnName = "index_id",
        inverseJoinColumnName = "column_id"
    )
    val columns: List<GenColumn>

    /**
     * 列 ID 视图
     */
    @IdView("columns")
    val columnIds: List<Long>
}
