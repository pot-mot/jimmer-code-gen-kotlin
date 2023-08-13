package top.potmot.model.query

import top.potmot.model.base.BaseQuery
import top.potmot.model.base.TimeRangeQueryParam

class TableQuery(
    override val ids: List<Long>? = null,
    override val createdTime: TimeRangeQueryParam? = null,
    override val modifiedTime: TimeRangeQueryParam? = null,
    keywords: List<String>? = null,
    columnNames: List<String>? = null,
    groupIds: List<Long>? = null
) : BaseQuery
