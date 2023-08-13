package top.potmot.model.base

interface BaseQuery {
    val ids: List<Long>?

    val createdTime: TimeRangeQueryParam?

    val modifiedTime: TimeRangeQueryParam?
}
