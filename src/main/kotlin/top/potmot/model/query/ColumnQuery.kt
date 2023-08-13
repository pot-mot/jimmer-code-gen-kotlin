package top.potmot.model.query

import top.potmot.model.base.BaseQuery
import top.potmot.model.base.TimeRangeQueryParam

class ColumnQuery(
    override val ids: List<Long>? = null,
    override val createdTime: TimeRangeQueryParam? = null,
    override val modifiedTime: TimeRangeQueryParam? = null,
    keywords: List<String>? = null,
    tableIds: List<Long>? = null,
) : BaseQuery
