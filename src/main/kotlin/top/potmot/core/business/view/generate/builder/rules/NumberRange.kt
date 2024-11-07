package top.potmot.core.business.view.generate.builder.rules

import top.potmot.entity.dto.GenEntityBusinessView

const val MAX_NUMBER_SIZE = 15L

val GenEntityBusinessView.TargetOf_properties.numberPrecision: Int?
    get() = column?.numericPrecision?.toInt()

val GenEntityBusinessView.TargetOf_properties.numberMin: Double?
    get() = if (column == null)
        null
    else
        0.0

val GenEntityBusinessView.TargetOf_properties.numberMax: Double?
    get() = if (column == null)
        null
    else {
        var integerPart = 0.0
        var decimalPart = 0.0
        for (i in 1..column.numericPrecision) {
            decimalPart += 9
            decimalPart /= 10
        }
        for (i in 1..kotlin.math.min(column.dataSize - column.numericPrecision, MAX_NUMBER_SIZE)) {
            integerPart *= 10
            integerPart += 9
        }
        integerPart + decimalPart
    }