package top.potmot.entity.model.associations

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length

/**
 * Join Table
 * 
 * @author potmot
 */
@Entity
interface GenJoinTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 名称
     */
    @get:Length(max = 255)
    val name: String

    /**
     * 注释
     */
    @get:Length(max = 255)
    val comment: String

    /**
     * 只读
     */
    val readonly: Boolean

    /**
     * 阻止从源删除
     */
    val preventDeletionBySource: Boolean

    /**
     * 阻止从目标删除
     */
    val preventDeletionByTarget: Boolean

    /**
     * 在终端逻辑删除时物理删除
     */
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
    val genJoinTableColumns: List<GenJoinTableColumn>

    /**
     * Join Table Join Column ID View
     */
    @IdView("genJoinTableColumns")
    val genJoinTableColumnIds: List<Int>

    /**
     * Join Table 过滤器
     * 
     * @see top.potmot.entity.model.associations.GenJoinTableFilter.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genJoinTableFilters: List<GenJoinTableFilter>

    /**
     * Join Table 过滤器 ID View
     */
    @IdView("genJoinTableFilters")
    val genJoinTableFilterIds: List<Int>

    /**
     * 多对多关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToManyAssociation.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToManyAssociations: List<GenManyToManyAssociation>

    /**
     * 多对多关联 ID View
     */
    @IdView("genManyToManyAssociations")
    val genManyToManyAssociationIds: List<Int>

    /**
     * 多对一关联
     * 
     * @see top.potmot.entity.model.associations.GenManyToOneAssociation.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genManyToOneAssociations: List<GenManyToOneAssociation>

    /**
     * 多对一关联 ID View
     */
    @IdView("genManyToOneAssociations")
    val genManyToOneAssociationIds: List<Int>

    /**
     * 一对一关联
     * 
     * @see top.potmot.entity.model.associations.GenOneToOneAssociation.joinTable
     */
    @OneToMany(mappedBy = "joinTable", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genOneToOneAssociations: List<GenOneToOneAssociation>

    /**
     * 一对一关联 ID View
     */
    @IdView("genOneToOneAssociations")
    val genOneToOneAssociationIds: List<Int>
}
