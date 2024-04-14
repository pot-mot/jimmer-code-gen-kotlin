package top.potmot.model.query.param

/**
 * 范围查询参数
 */
interface RangeQueryParam<T> {
    val start: T

    val end: T
}
