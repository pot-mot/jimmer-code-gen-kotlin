package top.potmot.model.base

import org.springframework.data.domain.PageRequest

data class PageQuery(
    val number: Int,
    val size: Int,
) {
    fun toPageRequest() = PageRequest.of(number, size)
}
