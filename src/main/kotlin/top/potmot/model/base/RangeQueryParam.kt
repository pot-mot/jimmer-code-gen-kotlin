package top.potmot.model.base

/**
 * 范围查询参数
 */
interface RangeQueryParam<T> {
    val start: T

    val end: T
}
