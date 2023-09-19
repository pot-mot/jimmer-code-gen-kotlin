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
import top.potmot.model.base.BaseEntity

/**
 * 生成数据源实体类
 *
 * @author potmot
 * @since 2023-08-14 15:28:59
 */
@Entity
interface GenSchema: BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 数据源 ID
     */
    @IdView
    val dataSourceId: Long

    /**
     * 数据源
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val dataSource: GenDataSource

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 表
     */
    @OneToMany(mappedBy = "schema")
    val tables: List<GenTable>
}

