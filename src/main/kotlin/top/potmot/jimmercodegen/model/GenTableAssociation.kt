package top.potmot.jimmercodegen.model;

import org.babyfish.jimmer.sql.*
import top.potmot.jimmercodegen.model.common.BaseEntity

/**
 * 代码生成业务表关联实体类
 *
 * @author potmot
 * @since 2023-05-07 09:36:23
 */
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
    val masterTable: GenTable

    @IdView
    val masterTableId: Long

    /**
     * 主表字段id
     */
    @ManyToOne
    val masterColumn: GenTableColumn

    @IdView
    val masterColumnId: Long

    /**
     * 从表编号
     */
    @ManyToOne
    val slaveTable: GenTable

    @IdView
    val slaveTableId: Long

    /**
     * 从表字段id
     */
    @ManyToOne
    val slaveColumn: GenTableColumn

    @IdView
    val slaveColumnId: Long

    /**
     * 关联类别
     */
    val associationCategory: String

}
