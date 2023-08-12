package top.potmot.model.base

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.LocalDateTime

/**
 * 基础实体
 */
@MappedSuperclass
interface BaseEntity: Identifiable<Long> {
    /**
     * 创建事件
     */
    val createdTime: LocalDateTime

    /**
     * 修改时间
     */
    val modifiedTime: LocalDateTime

    /**
     * 备注
     */
    val remark: String
}
