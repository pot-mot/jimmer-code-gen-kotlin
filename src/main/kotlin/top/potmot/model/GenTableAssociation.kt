package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToOne
import top.potmot.constant.AssociationType

/**
 * 代码生成业务表关联实体类
 *
 * @author potmot
 * @since 2023-08-04 13:08:00
 */
@Entity
interface GenTableAssociation {
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
    @IdView
    val sourceTableId: Long

    /**
     * 主表
     */
    @ManyToOne
    val sourceTable: GenTable

    /**
     * 主字段编号
     */
    @IdView
    val sourceColumnId: Long

    /**
     * 主字段
     */
    @ManyToOne
    val sourceColumn: GenTableColumn

    /**
     * 从表编号
     */
    @IdView
    val targetTableId: Long

    /**
     * 从表
     */
    @ManyToOne
    val targetTable: GenTable

    /**
     * 从字段编号
     */
    @IdView
    val targetColumnId: Long

    /**
     * 从字段
     */
    @ManyToOne
    val targetColumn: GenTableColumn

    /**
     * 关联类型（OneToOne, ManyToOne, OneToMany, ManyToMany）
     */
    val associationType: AssociationType

    /**
     * 关联表达式
     */
    val associationExpress: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

