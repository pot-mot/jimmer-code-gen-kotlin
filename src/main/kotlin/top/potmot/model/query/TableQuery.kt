package top.potmot.model.query

import top.potmot.model.base.TimeRangeQueryParam

class TableQuery(
    val ids: List<Long>? = null,
    val createdTime: TimeRangeQueryParam? = null,
    val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val name: String? = null,
    val schemaIds: List<Long>? = null,
    val nonSchema: Boolean? = null,
)
