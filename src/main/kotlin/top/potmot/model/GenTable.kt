package top.potmot.model

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
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.TableType
import top.potmot.model.base.BaseEntity

/**
 * 生成表实体类
 *
 * @author potmot
 * @since 2023-08-14 15:29:23
 */
@Entity
@Table(name = "jimmer_code_gen.gen_table")
interface GenTable : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 模型
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val model: GenModel?

    /**
     * 模型 ID 视图
     */
    @IdView("model")
    val modelId: Long?

    /**
     * 数据架构
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val schema: GenSchema?

    /**
     * 所属架构 ID视图
     */
    @IdView
    val schemaId: Long?

    /**
     * 上级表
     */
    @ManyToMany
    @JoinTable(
        name = "jimmer_code_gen.gen_super_table_mapping",
        joinColumnName = "inherit_table_id",
        inverseJoinColumnName = "super_table_id"
    )
    val superTables: List<GenTable>

    /**
     * 对应实体 ID
     */
    @IdView
    val entityId: Long?

    /**
     * 对应实体
     */
    @OneToOne(mappedBy = "table")
    val entity: GenEntity?

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * 注释
     */
    val comment: String

    /**
     * 种类
     */
    val type: TableType

    /**
     * 备注
     */
    val remark: String

    /**
     * 列
     */
    @OneToMany(mappedBy = "table", orderedProps = [OrderedProp(value = "orderKey")])
    val columns: List<GenColumn>

    /**
     * 列 ID
     */
    @IdView("columns")
    val columnIds: List<Long>

    /**
     * 唯一索引
     */
    @OneToMany(mappedBy = "table")
    val indexes: List<GenTableIndex>

    /**
     * 唯一索引 ID 视图
     */
    @IdView("indexes")
    val indexIds: List<Long>

    /**
     * 入关联
     */
    @OneToMany(mappedBy = "targetTable")
    val inAssociations: List<GenAssociation>

    /**
     * 入关联 ID 视图
     */
    @IdView("inAssociations")
    val inAssociationIds: List<Long>

    /**
     * 出关联
     */
    @OneToMany(mappedBy = "sourceTable")
    val outAssociations: List<GenAssociation>

    /**
     * 出关联 ID 视图
     */
    @IdView("outAssociations")
    val outAssociationIds: List<Long>
}

