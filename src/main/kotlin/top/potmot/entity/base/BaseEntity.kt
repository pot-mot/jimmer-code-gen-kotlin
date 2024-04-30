package top.potmot.entity.base

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.LocalDateTime

/**
 * 基础实体
 */
@MappedSuperclass
interface BaseEntity {
    /**
     * 创建事件
     */
    val createdTime: LocalDateTime

    /**
     * 修改时间
     */
    val modifiedTime: LocalDateTime
}
