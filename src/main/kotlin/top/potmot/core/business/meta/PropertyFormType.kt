package top.potmot.core.business.meta

import top.potmot.core.booleanType
import top.potmot.core.dateTimeType
import top.potmot.core.dateType
import top.potmot.core.intType
import top.potmot.core.numericType
import top.potmot.core.timeType
import top.potmot.entity.dto.GenEntityBusinessView

enum class PropertyFormType {
    INPUT,
    BOOLEAN,
    INT,
    FLOAT,
    TIME,
    DATE,
    DATETIME,
}

val GenEntityBusinessView.TargetOf_properties.formType: PropertyFormType
    get() = when (type) {
        in intType -> PropertyFormType.INT
        in numericType -> PropertyFormType.FLOAT
        in booleanType -> PropertyFormType.BOOLEAN
        in dateType -> PropertyFormType.DATE
        in timeType -> PropertyFormType.TIME
        in dateTimeType -> PropertyFormType.DATETIME
        else -> PropertyFormType.INPUT
    }