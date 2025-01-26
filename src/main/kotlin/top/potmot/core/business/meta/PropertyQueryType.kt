package top.potmot.core.business.meta

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

private val intRangeType = setOf(
    Short::class.qualifiedName,
    "short",
    "java.lang.Short",
    Int::class.qualifiedName,
    "int",
    "java.lang.Integer",
    Long::class.qualifiedName,
    "long",
    "java.lang.Long",
)

private val floatRangeType = setOf(
    BigDecimal::class.java.name,

    Float::class.qualifiedName,
    "float",
    "java.lang.Float",
    Double::class.qualifiedName,
    "double",
    "java.lang.Double",
)

private val likeType = setOf(
    String::class.qualifiedName,
    String::class.java.name,
)

private val timeRangeType = setOf(
    Instant::class.java.name,
    LocalTime::class.java.name
)

private val dateRangeType = setOf(
    LocalDate::class.java.name
)

private val dateTimeRangeType = setOf(
    LocalDateTime::class.java.name
)

val GenEntityBusinessView.TargetOf_properties.queryType: PropertyQueryType
    get() =
        if (idProperty) {
            PropertyQueryType.EQ
        } else {
            when (type) {
                in likeType -> PropertyQueryType.LIKE
                in intRangeType -> PropertyQueryType.INT_RANGE
                in floatRangeType -> PropertyQueryType.FLOAT_RANGE
                in timeRangeType -> PropertyQueryType.TIME_RANGE
                in dateRangeType -> PropertyQueryType.DATE_RANGE
                in dateTimeRangeType -> PropertyQueryType.DATETIME_RANGE
                else -> PropertyQueryType.EQ
            }
        }
