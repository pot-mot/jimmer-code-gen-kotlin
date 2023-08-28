package top.potmot.model.query

import top.potmot.model.base.BaseQuery
import top.potmot.model.base.TimeRangeQueryParam

class AssociationQuery(
    override val ids: List<Long>? = null,
    override val createdTime: TimeRangeQueryParam? = null,
    override val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val sourceTableId: Long? = null,
    val targetTableId: Long? = null,
    val sourceColumnId: Long? = null,
    val targetColumnId: Long? = null,
): BaseQuery
