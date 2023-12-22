package top.potmot.model.query

import top.potmot.model.base.TimeRangeQueryParam

class ColumnQuery(
    val ids: List<Long>? = null,
    val createdTime: TimeRangeQueryParam? = null,
    val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val tableIds: List<Long>? = null,
    val type: String? = null,
    val typeCode: Int? = null,
    val partOfPk: Boolean? = null,
)
