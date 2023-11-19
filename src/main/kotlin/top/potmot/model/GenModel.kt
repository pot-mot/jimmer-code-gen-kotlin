package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
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
     * 模型JSON数据
     */
    val value: String

    /**
     * 生成
     */
    @OneToMany(mappedBy = "model")
    val tables: List<GenTable>

    /**
     * 生成 ID 视图
     */
    @IdView("tables")
    val tableIds: List<Long>

    /**
     * 生成关联
     */
    @OneToMany(mappedBy = "model")
    val associations: List<GenAssociation>

    /**
     * 生成关联 ID 视图
     */
    @IdView("associations")
    val associationIds: List<Long>
}