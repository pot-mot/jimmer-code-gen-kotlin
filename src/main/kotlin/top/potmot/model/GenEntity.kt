package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.model.base.BaseEntity

/**
 * 生成实体实体类
 *
 * @author potmot
 * @since 2023-08-12 10:48:54
 */
@Entity
interface GenEntity : BaseEntity {
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
    val tableId: Long?

    /**
     * 对应表
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val table: GenTable?

    /**
     * 类名称
     */
    @Key
    val className: String

    /**
     * 类注释
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
     * 属性 ID
     */
    @IdView("properties")
    val propertyIds: List<Long>

    /**
     * 属性
     */
    @OneToMany(mappedBy = "entity")
    val properties: List<GenProperty>
}

