package top.potmot.core.common

import top.potmot.utils.number.format
import java.math.BigDecimal
import java.math.RoundingMode
import java.sql.Types

private val numberTypeSet = setOf(
    Types.TINYINT,
    Types.SMALLINT,
    Types.INTEGER,
    Types.BIGINT,

    Types.REAL,
    Types.FLOAT, Types.DOUBLE,
    Types.DECIMAL, Types.NUMERIC
)

fun numberMin(typeCode: Int, dataSize: Int, numericPrecision: Int) =
    if (typeCode !in numberTypeSet)
        null
    else if (dataSize <= 0)
        null
    else
        0.0.format(numericPrecision)

fun numberMax(typeCode: Int, dataSize: Int, numericPrecision: Int): String? {
    if (typeCode !in numberTypeSet)
        return null

    val integerPartStr: String

    if (dataSize > 0) {
        integerPartStr = "9".repeat(dataSize - numericPrecision)

        when (typeCode) {
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
        return when (typeCode) {
            Types.BIGINT -> Long.MAX_VALUE.toString()
            Types.INTEGER -> Int.MAX_VALUE.toString()
            Types.SMALLINT -> Short.MAX_VALUE.toString()
            Types.TINYINT -> Byte.MAX_VALUE.toString()
            else -> null
        }
    }

    if (numericPrecision > 0) {
        val decimalPartStr: String = "0." + "9".repeat(numericPrecision)

        val integerPart = BigDecimal(integerPartStr)
        val decimalPart = BigDecimal(decimalPartStr)

        return (integerPart + decimalPart).setScale(numericPrecision, RoundingMode.HALF_UP).toString()
    } else {
        return integerPartStr
    }
}
