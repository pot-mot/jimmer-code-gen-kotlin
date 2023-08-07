package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToManyView
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import top.potmot.model.base.BaseEntity
import top.potmot.model.base.Identifiable

/**
 * 生成实体实体类
 *
 * @author potmot
 * @since 2023-08-06 17:21:36
 */
@Entity
interface GenEntity : BaseEntity, Identifiable<Long> {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 对应表 ID
     */
    @IdView
    val tableId: Long

    /**
     * 对应表
     */
    @OneToOne
    @OnDissociate(DissociateAction.DELETE)
    val table: GenTable

    /**
     * 类名称
     */
    @Key
    val className: String

    /**
     * 类描述
     */
    val classComment: String

    /**
     * 包名
     */
    @Key
    val packageName: String

    /**
     * 模块名
     */
    val moduleName: String

    /**
     * 功能名
     */
    val functionName: String

    /**
     * 作者
     */
    val author: String

    /**
     * 生成路径（不填默认项目路径）
     */
    val genPath: String

    /**
     * 是否生成添加功能（1是）
     */
    val isAdd: Boolean

    /**
     * 是否生成编辑功能（1是）
     */
    val isEdit: Boolean

    /**
     * 是否生成列表功能（1是）
     */
    val isList: Boolean

    /**
     * 是否生成查询功能（1是）
     */
    val isQuery: Boolean

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 列
     */
    @OneToMany(mappedBy = "entity")
    val properties: List<GenProperty>

    /**
     * 出关联
     * 本表作为主表的关联，指向另一张表
     * example:
     * book -> author
     */
    @OneToMany(mappedBy = "sourceEntity")
    val outAssociations: List<GenAssociation>

    /**
     * 入关联
     * 本表作为从表的关联，被另一张表所指
     * example:
     * author <- book
     */
    @OneToMany(mappedBy = "targetEntity")
    val inAssociations: List<GenAssociation>

    /**
     * 本表指向的从表
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetEntity"
    )
    val targetEntities: List<GenEntity>

    /**
     * 指向本表的主表
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceEntity"
    )
    val sourceEntities: List<GenEntity>

    /**
     * 本表指向的从表列
     * example:
     * book -> author 中的 author.id
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetProperty"
    )
    val targetProperties: List<GenProperty>

    /**
     * 指向本表的主表列
     * example:
     * author <- book 中的 book.authorId
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceProperty"
    )
    val sourceProperties: List<GenProperty>
}

