package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Key
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
    @Key
    val name: String

    /**
     * 模型JSON数据
     */
    val value: String
}
