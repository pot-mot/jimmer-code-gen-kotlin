package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToManyView
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.model.base.BaseEntity

/**
 * 代码生成业务表实体类
 *
 * @author potmot
 * @since 2023-08-05 09:51:52
 */
@Entity
interface GenTable : BaseEntity {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

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
    @Key
    val tableType: String

    /**
     * 类名称
     */
    val className: String

    /**
     * 类描述
     */
    val classComment: String

    /**
     * 包名
     */
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
    @OneToMany(mappedBy = "table")
    val columns: List<GenTableColumn>

    /**
     * 出关联
     * 本表作为主表的关联，指向另一张表
     * example:
     * book -> author
     */
    @OneToMany(mappedBy = "sourceTable")
    val outAssociations: List<GenTableAssociation>

    /**
     * 入关联
     * 本表作为从表的关联，被另一张表所指
     * example:
     * author <- book
     */
    @OneToMany(mappedBy = "targetTable")
    val inAssociations: List<GenTableAssociation>

    /**
     * 本表指向的从表
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetTable"
    )
    val targetTables: List<GenTable>

    /**
     * 指向本表的主表
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceTable"
    )
    val sourceTables: List<GenTable>

    /**
     * 本表指向的从表列
     * example:
     * book -> author 中的 author.id
     */
    @ManyToManyView(
        prop = "outAssociations",
        deeperProp = "targetColumn"
    )
    val targetColumns: List<GenTableColumn>

    /**
     * 指向本表的主表列
     * example:
     * author <- book 中的 book.authorId
     */
    @ManyToManyView(
        prop = "inAssociations",
        deeperProp = "sourceColumn"
    )
    val sourceColumns: List<GenTableColumn>
}

