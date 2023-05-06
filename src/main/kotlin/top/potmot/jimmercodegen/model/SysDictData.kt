package top.potmot.jimmercodegen.model;
import java.time.LocalDateTime;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.ManyToOne

/**
 * 字典数据表实体类
 *
 * @author potmot
 * @since 2023-05-06 19:13:44
 */
@Entity
interface SysDictData {
    /**
     * 字典数据编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val sysDictDataCode: Long

    /**
     * 字典排序
     */
    val dictSort: Int

    /**
     * 字典标签
     */
    val dictLabel: String

    /**
     * 字典键值
     */
    val dictValue: String

    /**
     * 字典数据
     */
    @ManyToOne
    val dictType: SysDictType

}
