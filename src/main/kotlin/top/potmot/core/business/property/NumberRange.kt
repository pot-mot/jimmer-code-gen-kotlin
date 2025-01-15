package top.potmot.core.business.property

import java.math.BigDecimal
import java.math.RoundingMode
import java.sql.Types
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.utils.number.format

private val numberTypeSet = setOf(
    Types.TINYINT,
    Types.SMALLINT,
    Types.INTEGER,
    Types.BIGINT,

    Types.REAL,
    Types.FLOAT, Types.DOUBLE,
    Types.DECIMAL, Types.NUMERIC
)

val GenEntityBusinessView.TargetOf_properties.numberPrecision: Int?
    get() = column?.numericPrecision

val GenEntityBusinessView.TargetOf_properties.numberMin: String?
    get() = if (column == null)
        null
    else
        if (column.typeCode !in numberTypeSet)
            null
        else if (column.dataSize <= 0)
            null
        else
            0.0.format(column.numericPrecision)

val GenEntityBusinessView.TargetOf_properties.numberMax: String?
    get() {
        if (column == null)
            return null
        else if (column.typeCode !in numberTypeSet)
            return null
        else {

            val integerPartStr: String

            if (column.dataSize > 0) {
                integerPartStr = "9".repeat(column.dataSize - column.numericPrecision)

                when (column.typeCode) {
                    Types.BIGINT -> if (integerPartStr.toBigInteger() > Long.MAX_VALUE.toBigInteger()) return Long.MAX_VALUE.toString()
                    Types.INTEGER -> if (integerPartStr.toBigInteger() > Int.MAX_VALUE.toBigInteger()) return Int.MAX_VALUE.toString()
                    Types.SMALLINT -> if (integerPartStr.toBigInteger() > Short.MAX_VALUE.toLong()
                            .toBigInteger()
                    ) return Short.MAX_VALUE.toString()

                    Types.TINYINT -> if (integerPartStr.toBigInteger() > Byte.MAX_VALUE.toLong()
                            .toBigInteger()
                    ) return Byte.MAX_VALUE.toString()
                }
            } else {
                // 如果数据尺寸小于等于 0，则参照 typeCode 给出尺寸限制
                return when (column.typeCode) {
                    Types.BIGINT -> Long.MAX_VALUE.toString()
                    Types.INTEGER -> Int.MAX_VALUE.toString()
                    Types.SMALLINT -> Short.MAX_VALUE.toString()
                    Types.TINYINT -> Byte.MAX_VALUE.toString()
                    else -> null
                }
            }

            if (column.numericPrecision > 0) {
                val decimalPartStr: String = "0." + "9".repeat(column.numericPrecision)

                val integerPart = BigDecimal(integerPartStr)
                val decimalPart = BigDecimal(decimalPartStr)

                return (integerPart + decimalPart).setScale(column.numericPrecision, RoundingMode.HALF_UP).toString()
            } else {
                return integerPartStr
            }
        }
    }
