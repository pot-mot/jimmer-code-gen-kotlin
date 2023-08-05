package top.potmot.model.base

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
interface BaseEntity {
    val createdTime: LocalDateTime

    val modifiedTime: LocalDateTime

    val remark: String
}
