package top.potmot.entity.model

import jakarta.validation.Valid
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
interface GenModelGroup {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @get:Length(max = 500)
    val name: String

    /**
     * 注释
     */
    @get:Length(max = 500)
    val comment: String

    /**
     * 包路径
     */
    @get:Length(max = 500)
    val packagePath: String

    /**
     * 表路径
     */
    @get:Length(max = 500)
    val tablePath: String

    /**
     * 颜色
     */
    @get:Length(max = 1000000000)
    val color: String

    /**
     * 排序键
     */
    val orderKey: Int

    /**
     * 复合类型
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableType.group
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypes: List<GenEmbeddableType>

    /**
     * 复合类型 ID View
     */
    @IdView("genEmbeddableTypes")
    val genEmbeddableTypeIds: List<Int>

    /**
     * 实体
     * 
     * @see top.potmot.entity.model.entities.GenEntity.group
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEntities: List<GenEntity>

    /**
     * 实体 ID View
     */
    @IdView("genEntities")
    val genEntityIds: List<Int>

    /**
     * 枚举
     * 
     * @see top.potmot.entity.model.enums.GenEnum.group
     */
    @OneToMany(mappedBy = "group", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEnums: List<GenEnum>

    /**
     * 枚举 ID View
     */
    @IdView("genEnums")
    val genEnumIds: List<Int>
}
