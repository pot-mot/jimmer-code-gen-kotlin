package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import java.time.LocalDateTime

/**
 * 字典类型实体类
 *
 * @author potmot
 * @since 2023-08-06 17:21:18
 */
@Entity
interface GenDict {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 字典名称
     */
    val dictName: String

    /**
     * 字典类型
     */
    val dictType: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

