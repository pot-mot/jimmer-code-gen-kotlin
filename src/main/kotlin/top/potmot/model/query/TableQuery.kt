package top.potmot.model.query

import top.potmot.model.base.BaseQuery
import top.potmot.model.base.TimeRangeQueryParam

class TableQuery(
    override val ids: List<Long>? = null,
    override val createdTime: TimeRangeQueryParam? = null,
    override val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val name: String? = null,
    val schemaIds: List<Long>? = null,
    val groupIds: List<Long>? = null
) : BaseQuery
