package top.potmot.model;

import org.babyfish.jimmer.sql.*
import top.potmot.model.common.BaseEntity

/**
 * 代码生成业务表关联实体类
 *
 * @author potmot
 * @since 2023-05-07 09:36:23 */
@Entity
interface GenTableAssociation : BaseEntity {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 表关联名称
     */
    val tableAssociationName: String

    /**
     * 主表编号
     */
    @ManyToOne
    val sourceTable: GenTable

    @IdView
    val sourceTableId: Long

    /**
     * 主表字段id
     */
    @ManyToOne
    val sourceColumn: GenTableColumn

    @IdView
    val sourceColumnId: Long

    /**
     * 从表编号
     */
    @ManyToOne
    val targetTable: GenTable

    @IdView
    val targetTableId: Long

    /**
     * 从表字段id
     */
    @ManyToOne
    val targetColumn: GenTableColumn

    @IdView
    val targetColumnId: Long

    /**
     * 关联类别
     */
    val associationCategory: String

}
