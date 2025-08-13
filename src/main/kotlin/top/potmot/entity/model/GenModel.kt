package top.potmot.entity.model

import jakarta.validation.Valid
import java.time.LocalDateTime
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length
import top.potmot.enums.database.DatabaseNamingStrategyType
import top.potmot.enums.database.DatabaseType
import top.potmot.enums.model.DevLanguage

/**
 * 模型
 * 
 * @author potmot
 */
@Entity
interface GenModel {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 模型配置
     * 
     * @see top.potmot.entity.model.GenModelConfig.model
     */
    @OneToOne(mappedBy = "model")
    @get:Valid
    val genModelConfig: GenModelConfig?

    /**
     * 模型配置 ID View
     */
    @IdView("genModelConfig")
    val genModelConfigId: Int?

    /**
     * 名称
     */
    @get:Length(max = 500)
    val name: String

    /**
     * 作者
     */
    @get:Length(max = 500)
    val author: String

    /**
     * 语言
     */
    val language: DevLanguage

    /**
     * 数据库类型
     */
    val databaseType: DatabaseType

    /**
     * 数据库命名策略
     */
    val databaseNamingStrategy: DatabaseNamingStrategyType

    /**
     * 图偏移
     */
    @get:Length(max = 255)
    val transition: String

    /**
     * 创建时间
     */
    val createdTime: LocalDateTime

    /**
     * 修改时间
     */
    val modifiedTime: LocalDateTime

    /**
     * 模型分组
     * 
     * @see top.potmot.entity.model.GenModelGroup.model
     */
    @OneToMany(mappedBy = "model", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genModelGroups: List<GenModelGroup>

    /**
     * 模型分组 ID View
     */
    @IdView("genModelGroups")
    val genModelGroupIds: List<Int>
}
