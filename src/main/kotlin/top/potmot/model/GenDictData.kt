package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import java.time.LocalDateTime

/**
 * 字典数据实体类
 *
 * @author potmot
 * @since 2023-08-06 17:21:29
 */
@Entity
interface GenDictData {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 字典类型
     */
    val dictTypeId: Long

    /**
     * 字典内排序
     */
    val dictSort: Long

    /**
     * 字典标签
     */
    val dictLabel: String

    /**
     * 字典键值
     */
    val dictValue: String

    /**
     * 枚举名
     */
    val enumName: String

    /**
     * 枚举标签
     */
    val enumLabel: String

    /**
     * 枚举值
     */
    val enumValue: String

}

