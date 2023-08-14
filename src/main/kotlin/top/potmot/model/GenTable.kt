package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.constant.TableType
import top.potmot.model.base.BaseEntity

/**
 * 生成表实体类
 *
 * @author potmot
 * @since 2023-08-14 15:29:23
 */
@Entity
interface GenTable : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long


    /**
     * 数据源
     */
    @IdView
    val dataSourceId: Long

    /**
     * 数据源
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val dataSource: GenDataSource

    /**
     * 所属组 ID
     */
    @IdView
    val groupId: Long?

    /**
     * 所属组
     */
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val group: GenTableGroup?

    /**
     * 对应实体 ID
     */
    @IdView("entities")
    val entityIds: List<Long>

    /**
     * 对应实体
     */
    @OneToMany(mappedBy = "table")
    val entities: List<GenEntity>

    /**
     * 表名称
     */
    @Key
    val tableName: String

    /**
     * 表注释
     */
    val tableComment: String

    /**
     * 表种类
     */
    val tableType: TableType

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 列 ID
     */
    @IdView("columns")
    val columnIds: List<Long>

    /**
     * 列
     */
    @OneToMany(mappedBy = "table")
    val columns: List<GenColumn>
}

