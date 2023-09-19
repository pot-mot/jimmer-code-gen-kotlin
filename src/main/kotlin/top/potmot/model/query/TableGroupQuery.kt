package top.potmot.model.query

import top.potmot.model.base.TimeRangeQueryParam

class TableGroupQuery(
    val ids: List<Long>? = null,
    val createdTime: TimeRangeQueryParam? = null,
    val modifiedTime: TimeRangeQueryParam? = null,
    val names: List<String>? = null,
)
