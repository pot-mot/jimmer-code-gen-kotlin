package top.potmot.context

import org.babyfish.jimmer.kt.merge
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.MutableGenConfig

fun MutableGenConfig.toProperties(): GenConfigProperties =
    GenConfigProperties(this.toEntity())

fun List<GenConfigProperties>.multiple(other: List<GenConfigProperties>): List<GenConfigProperties> {
    val result = mutableListOf<GenConfigProperties>()

    this.forEach { p1 ->
        other.forEach { p2 ->
            result += GenConfigProperties(merge(p1.toEntity(), p2.toEntity()))
        }
    }

    return result
}
