package top.potmot.utils

import org.babyfish.jimmer.kt.merge
import top.potmot.entity.dto.GenConfigProperties

fun List<GenConfigProperties>.multiple(other: List<GenConfigProperties>): List<GenConfigProperties> {
    val result = mutableListOf<GenConfigProperties>()

    this.forEach { p1 ->
        other.forEach { p2 ->
            result += GenConfigProperties(merge(p1.toEntity(), p2.toEntity()))
        }
    }

    return result
}
