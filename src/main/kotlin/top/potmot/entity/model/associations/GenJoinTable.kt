package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * Join Table
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_join_table")
interface GenJoinTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 名称
     */
    @Column(name = "name")
    @get:Length(max = 255)
    val name: String

    /**
     * 注释
     */
    @Column(name = "comment")
    @get:Length(max = 255)
    val comment: String

    /**
     * 只读
     */
    @Column(name = "readonly")
    val readonly: Boolean

    /**
     * 阻止从源删除
     */
    @Column(name = "prevent_deletion_by_source")
    val preventDeletionBySource: Boolean

    /**
     * 阻止从目标删除
     */
    @Column(name = "prevent_deletion_by_target")
    val preventDeletionByTarget: Boolean

    /**
     * 在终端逻辑删除时物理删除
     */
    @Column(name = "deleted_when_endpointIs_logically_deleted")
    val deletedWhenEndpointIsLogicallyDeleted: Boolean

    /**
     * 逻辑删除过滤器
     */
    @ManyToOne
    @JoinColumn(
        name = "logical_delete_filter_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val logicalDeleteFilter: GenJoinTableLogicalDeleteFilter?

    /**
     * 逻辑删除过滤器 ID View
     */
    @IdView("logicalDeleteFilter")
    val logicalDeleteFilterId: Int?

    /**
     * Join Table Join Column
     * 
     * @see top.potmot.entity.model.associations.GenJoinTableColumn.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val joinTableColumns: List<GenJoinTableColumn>

    /**
     * Join Table Join Column ID View
     */
    @IdView("joinTableColumns")
    val joinTableColumnIds: List<Int>

    /**
     * Join Table 过滤器
     * 
     * @see top.potmot.entity.model.associations.GenJoinTableFilter.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val joinTableFilters: List<GenJoinTableFilter>

    /**
     * Join Table 过滤器 ID View
     */
    @IdView("joinTableFilters")
    val joinTableFilterIds: List<Int>

    /**
     * 多对多关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToManyAssociation.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyToManyAssociations: List<GenManyToManyAssociation>

    /**
     * 多对多关联 ID View
     */
    @IdView("manyToManyAssociations")
    val manyToManyAssociationIds: List<Int>

    /**
     * 多对一关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToOneAssociation.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val manyToOneAssociations: List<GenManyToOneAssociation>

    /**
     * 多对一关联 ID View
     */
    @IdView("manyToOneAssociations")
    val manyToOneAssociationIds: List<Int>

    /**
     * 一对一关联
     * 
     * @see top.potmot.entity.model.associations.GenOneToOneAssociation.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val oneToOneAssociations: List<GenOneToOneAssociation>

    /**
     * 一对一关联 ID View
     */
    @IdView("oneToOneAssociations")
    val oneToOneAssociationIds: List<Int>
}
