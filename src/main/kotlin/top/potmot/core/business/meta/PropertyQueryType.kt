package top.potmot.core.business.meta

import top.potmot.core.common.dateTimeType
import top.potmot.core.common.dateType
import top.potmot.core.common.intType
import top.potmot.core.common.numericType
import top.potmot.core.common.stringType
import top.potmot.core.common.timeType
import top.potmot.entity.dto.GenEntityBusinessView

enum class PropertyQueryType {
    EQ,
    LIKE,
    INT_RANGE,
    FLOAT_RANGE,
    TIME_RANGE,
    DATE_RANGE,
    DATETIME_RANGE,
}

val GenEntityBusinessView.TargetOf_properties.queryType: PropertyQueryType
    get() =
        if (idProperty) {
            PropertyQueryType.EQ
        } else {
            when (type) {
                in stringType -> PropertyQueryType.LIKE
                in intType -> PropertyQueryType.INT_RANGE
                in numericType -> PropertyQueryType.FLOAT_RANGE
                in timeType -> PropertyQueryType.TIME_RANGE
                in dateType -> PropertyQueryType.DATE_RANGE
                in dateTimeType -> PropertyQueryType.DATETIME_RANGE
                else -> PropertyQueryType.EQ
            }
        }
