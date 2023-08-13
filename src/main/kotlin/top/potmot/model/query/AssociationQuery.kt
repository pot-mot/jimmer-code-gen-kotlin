package top.potmot.model.query

import top.potmot.model.base.BaseQuery
import top.potmot.model.base.TimeRangeQueryParam

class AssociationQuery(
    override val ids: List<Long>? = null,
    override val createdTime: TimeRangeQueryParam? = null,
    override val modifiedTime: TimeRangeQueryParam? = null,
    keywords: List<String>? = null,
    sourceTableId: Long? = null,
    targetTableId: Long? = null,
    sourceColumnId: Long? = null,
    targetColumnId: Long? = null,
): BaseQuery
