package top.potmot.jimmercodegen.model;
import java.time.LocalDateTime;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToOne

/**
 * 代码生成业务表关联实体类
 *
 * @author potmot
 * @since 2023-05-06 18:45:32
 */
@Entity
interface GenTableAssociation {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val genTableAssociationId: Long

    /**
     * 表关联名称
     */
    val tableAssociationName: String

    /**
     * 主表
     */
    @ManyToOne
    val masterTable: GenTable

    @IdView
    val masterTableId: Long

    /**
     * 主表字段
     */
    val masterTableColumn: String

    /**
     * 从表
     */
    @ManyToOne
    val slaveTable: GenTable

    @IdView
    val slaveTableId: Long

    /**
     * 从表字段
     */
    val slaveTableColumn: String

    /**
     * 关联类别
     */
    val associationCategory: String

}
