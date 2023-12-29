package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.base.BaseEntity

/**
 * 生成模型
 *
 * @author
 * @since 2023-11-02 20:13:10
 */
@Entity
@Table(name = "jimmer_code_gen.gen_model")
interface GenModel : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 名称
     */
    val name: String

    /**
     * Graph 数据
     */
    val graphData: String

    /**
     * 语言
     */
    val language: GenLanguage

    /**
     * 数据源类型
     */
    val dataSourceType: DataSourceType

    /**
     * 包路径
     */
    val packagePath: String

    /**
     * 同步转换实体
     */
    val syncConvertEntity: Boolean

    /**
     * 表
     */
    @OneToMany(mappedBy = "model")
    val tables: List<GenTable>

    /**
     * 表 ID 视图
     */
    @IdView("tables")
    val tableIds: List<Long>

    /**
     * 关联
     */
    @OneToMany(mappedBy = "model")
    val associations: List<GenAssociation>

    /**
     * 关联 ID 视图
     */
    @IdView("associations")
    val associationIds: List<Long>

    /**
     * 实体
     */
    @OneToMany(mappedBy = "model")
    val entities: List<GenEntity>

    /**
     * 实体 ID 视图
     */
    @IdView("entities")
    val entityIds: List<Long>

    /**
     * 实体
     */
    @OneToMany(mappedBy = "model")
    val enums: List<GenEnum>

    /**
     * 实体 ID 视图
     */
    @IdView("enums")
    val enumIds: List<Long>
}
