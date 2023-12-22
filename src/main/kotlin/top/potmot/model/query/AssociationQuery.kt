package top.potmot.model.query

import top.potmot.enumeration.AssociationType
import top.potmot.model.base.TimeRangeQueryParam

class AssociationQuery(
    val ids: List<Long>? = null,
    val associationType: AssociationType? = null,
    val createdTime: TimeRangeQueryParam? = null,
    val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val sourceTableId: Long? = null,
    val targetTableId: Long? = null,
    val sourceColumnId: Long? = null,
    val targetColumnId: Long? = null,
)
