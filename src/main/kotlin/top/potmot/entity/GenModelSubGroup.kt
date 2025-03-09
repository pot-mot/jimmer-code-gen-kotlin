package top.potmot.entity

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.entity.base.BaseEntity

/**
 * 生成模型子组
 * 
 * @author potmot
 */
@Entity
interface GenModelSubGroup : BaseEntity {
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
    val model: GenModel

    /**
     * 模型 ID View
     */
    @IdView("model")
    val modelId: Long

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
     * 子包路径
     */
    val subPackagePath: String

    /**
     * 样式
     */
    val style: String

    /**
     * 生成枚举
     * 
     * @see top.potmot.entity.GenEnum.subGroup
     */
    @OneToMany(mappedBy = "subGroup")
    val enums: List<GenEnum>

    /**
     * 生成枚举 ID View
     */
    @IdView("enums")
    val enumIds: List<Long>

    /**
     * 生成
     * 
     * @see top.potmot.entity.GenTable.subGroup
     */
    @OneToMany(mappedBy = "subGroup")
    val tables: List<GenTable>

    /**
     * 生成 ID View
     */
    @IdView("tables")
    val tableIds: List<Long>
}
