package top.potmot.entity.model

import jakarta.validation.Valid
import java.time.LocalDateTime
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
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
@Table(name = "gen_model")
interface GenModel {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 模型配置
     * 
     * @see top.potmot.entity.model.GenModelConfig.model
     */
    @OneToOne(mappedBy = "model")
    @get:Valid
    val modelConfig: GenModelConfig?

    /**
     * 模型配置 ID View
     */
    @IdView("modelConfig")
    val modelConfigId: Int?

    /**
     * 名称
     */
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 作者
     */
    @Column(name = "author")
    @get:Length(max = 500)
    val author: String

    /**
     * 语言
     */
    @Column(name = "language")
    val language: DevLanguage

    /**
     * 数据库类型
     */
    @Column(name = "database_type")
    val databaseType: DatabaseType

    /**
     * 数据库命名策略
     */
    @Column(name = "database_naming_strategy")
    val databaseNamingStrategy: DatabaseNamingStrategyType

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    val createdTime: LocalDateTime

    /**
     * 修改时间
     */
    @Column(name = "modified_time")
    val modifiedTime: LocalDateTime

    /**
     * 模型分组
     * 
     * @see top.potmot.entity.model.GenModelGroup.model
     */
    @OneToMany(mappedBy = "model", orderedProps = [OrderedProp("id")])
    @get:Valid
    val modelGroups: List<GenModelGroup>

    /**
     * 模型分组 ID View
     */
    @IdView("modelGroups")
    val modelGroupIds: List<Int>
}
