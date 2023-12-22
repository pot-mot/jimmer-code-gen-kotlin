package top.potmot.model.query

import top.potmot.enumeration.TableType
import top.potmot.model.base.TimeRangeQueryParam

class TableQuery(
    val ids: List<Long>? = null,
    val createdTime: TimeRangeQueryParam? = null,
    val modifiedTime: TimeRangeQueryParam? = null,
    val keywords: List<String>? = null,
    val schemaIds: List<Long>? = null,
    val nonSchema: Boolean? = null,
    val modelIds: List<Long>? = null,
    val nonModel: Boolean? = null,
    val type: TableType? = null,
)
