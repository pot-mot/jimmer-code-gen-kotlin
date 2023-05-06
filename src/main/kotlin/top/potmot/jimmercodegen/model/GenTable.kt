package top.potmot.jimmercodegen.model;
import java.time.LocalDateTime;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.OneToMany

/**
 * 代码生成业务表实体类
 *
 * @author potmot
 * @since 2023-05-06 18:45:50
 */
@Entity
interface GenTable {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val genTableId: Long

    /**
     * 表名称
     */
    val tableName: String

    /**
     * 表描述
     */
    val tableComment: String

    /**
     * 实体类名称
     */
    val className: String

    /**
     * 使用的模板（crud单表操作 tree树表操作）
     */
    val tplCategory: String

    /**
     * 生成包路径
     */
    val packageName: String

    /**
     * 生成模块名
     */
    val moduleName: String

    /**
     * 生成业务名
     */
    val businessName: String

    /**
     * 生成功能名
     */
    val functionName: String

    /**
     * 生成功能作者
     */
    val functionAuthor: String

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    val genType: String

    /**
     * 生成路径（不填默认项目路径）
     */
    val genPath: String

    /**
     * 其它生成选项
     */
    val options: String

    /**
     * 列
     */
    @OneToMany(mappedBy = "table")
    val columns: List<GenTableColumn>

    /**
     * 作为从表的关联
     */
    @OneToMany(mappedBy = "slaveTable")
    val slaveAssociation: List<GenTableAssociation>

    /**
     * 作为主表的关联
     */
    @OneToMany(mappedBy = "masterTable")
    val masterAssociation: List<GenTableAssociation>
}
