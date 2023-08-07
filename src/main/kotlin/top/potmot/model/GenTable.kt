package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import top.potmot.model.base.BaseEntity
import top.potmot.model.base.Identifiable

/**
 * 生成表实体类
 *
 * @author potmot
 * @since 2023-08-06 17:22:35
 */
@Entity
interface GenTable : BaseEntity, Identifiable<Long> {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

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
     * 表名称
     */
    @Key
    val tableName: String

    /**
     * 表描述
     */
    val tableComment: String

    /**
     * 表种类
     */
    val tableType: String

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 列
     */
    @OneToMany(mappedBy = "table")
    val columns: List<GenColumn>
}

