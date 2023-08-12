package top.potmot.model.queryParam

import org.springframework.data.domain.PageRequest

/**
 * 分页查询参数
 */
data class PageQueryParam(
    val number: Int,
    val size: Int,
) {
    fun toPageRequest() = PageRequest.of(number, size)
}
