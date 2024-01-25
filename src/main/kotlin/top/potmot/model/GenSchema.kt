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
import org.babyfish.jimmer.sql.Table
import top.potmot.model.base.BaseEntity

/**
 * 生成数据源实体类
 *
 * @author potmot
 * @since 2023-08-14 15:28:59
 */
@Entity
@Table(name = "jimmer_code_gen.gen_schema")
interface GenSchema : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

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
     * 备注
     */
    val remark: String

    /**
     * 表
     */
    @OneToMany(mappedBy = "schema")
    val tables: List<GenTable>
}

