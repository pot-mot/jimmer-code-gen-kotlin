package top.potmot.model.query

import top.potmot.model.base.TimeRangeQueryParam

class EntityQuery(
    val ids: List<Long>? = null,
    val createdTime: TimeRangeQueryParam? = null,
    val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val names: List<String>? = null,
    val packageIds: List<Long>? = null,
    val nonPackage: Boolean? = null,
)
