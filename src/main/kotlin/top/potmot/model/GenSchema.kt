package top.potmot.model

import org.babyfish.jimmer.sql.*
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

