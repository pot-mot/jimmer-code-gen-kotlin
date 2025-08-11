package top.potmot.entity.model

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.embeddables.GenEmbeddableType
import top.potmot.entity.model.entities.GenEntity
import top.potmot.entity.model.enums.GenEnum

/**
 * 模型分组
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_model_group")
interface GenModelGroup {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 模型
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "model_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val model: GenModel

    /**
     * 模型 ID View
     */
    @IdView("model")
    val modelId: Int

    /**
     * 名称
     */
    @Key
    @Column(name = "name")
    @get:Length(max = 500)
    val name: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 500)
    val comment: String

    /**
     * 包路径
     */
    @Column(name = "package_path")
    @get:Length(max = 500)
    val packagePath: String

    /**
     * 表路径
     */
    @Column(name = "table_path")
    @get:Length(max = 500)
    val tablePath: String

    /**
     * 样式
     */
    @Column(name = "style")
    @get:Length(max = 1000000000)
    val style: String

    /**
     * 排序键
     */
    @Column(name = "order_key")
    val orderKey: Int

    /**
     * 复合类型
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableType.group
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("id")])
    @get:Valid
    val embeddableTypes: List<GenEmbeddableType>

    /**
     * 复合类型 ID View
     */
    @IdView("embeddableTypes")
    val embeddableTypeIds: List<Int>

    /**
     * 实体
     * 
     * @see top.potmot.entity.model.entities.GenEntity.group
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entities: List<GenEntity>

    /**
     * 实体 ID View
     */
    @IdView("entities")
    val entityIds: List<Int>

    /**
     * 枚举
     * 
     * @see top.potmot.entity.model.enums.GenEnum.group
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("id")])
    @get:Valid
    val enums: List<GenEnum>

    /**
     * 枚举 ID View
     */
    @IdView("enums")
    val enumIds: List<Int>
}
