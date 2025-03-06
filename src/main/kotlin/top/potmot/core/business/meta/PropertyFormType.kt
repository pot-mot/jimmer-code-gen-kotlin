package top.potmot.core.business.meta

import top.potmot.core.common.booleanType
import top.potmot.core.common.dateTimeType
import top.potmot.core.common.dateType
import top.potmot.core.common.intType
import top.potmot.core.common.numericType
import top.potmot.core.common.timeType
import top.potmot.entity.dto.GenEntityBusinessView

enum class PropertyFormType {
    INPUT,
    BOOLEAN,
    INT,
    FLOAT,
    TIME,
    DATE,
    DATETIME,

    FILE,
    FILE_LIST,
    IMAGE,
    IMAGE_LIST,
}

/**
 * 特殊表单类型
 */
enum class PropertySpecialFormType(
    val formType: PropertyFormType
) {
    FILE(PropertyFormType.FILE),
    FILE_LIST(PropertyFormType.FILE_LIST),
    IMAGE(PropertyFormType.IMAGE),
    IMAGE_LIST(PropertyFormType.IMAGE_LIST),
}

val GenEntityBusinessView.TargetOf_properties.formType: PropertyFormType
    get() = specialFormType?.formType ?: when (type) {
        in intType -> PropertyFormType.INT
        in numericType -> PropertyFormType.FLOAT
        in booleanType -> PropertyFormType.BOOLEAN
        in dateType -> PropertyFormType.DATE
        in timeType -> PropertyFormType.TIME
        in dateTimeType -> PropertyFormType.DATETIME
        else -> PropertyFormType.INPUT
    }