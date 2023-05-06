package top.potmot.jimmercodegen.model;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToOne

/**
 * 代码生成业务表字段实体类
 *
 * @author potmot
 * @since 2023-05-06 23:20:00
 */
@Entity
interface GenTableColumn {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val genTableColumnId: Long

    /**
     * 归属表编号
     */
    @ManyToOne
    val table: GenTable
    
    @IdView
    val tableId: Long

    /**
     * 列名称
     */
    val columnName: String

    /**
     * 列描述
     */
    val columnComment: String

    /**
     * 列类型
     */
    val columnType: String

    /**
     * 实体类型
     */
    val fieldType: String

    /**
     * 字段名
     */
    val fieldName: String

    /**
     * 是否主键（1是）
     */
    val pk: String

    /**
     * 是否自增（1是）
     */
    val increment: String

    /**
     * 是否必填（1是）
     */
    val required: String

    /**
     * 是否为增加字段（1是）
     */
    val inInsert: String

    /**
     * 是否编辑字段（1是）
     */
    val inEdit: String

    /**
     * 是否列表字段（1是）
     */
    val inList: String

    /**
     * 是否查询字段（1是）
     */
    val inQuery: String

    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    val queryType: String

    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    val htmlType: String

    /**
     * 字典类型
     */
    val dictType: String

    /**
     * 排序
     */
    val sort: Int

}
