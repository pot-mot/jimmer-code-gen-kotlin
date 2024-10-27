package top.potmot.core.business.utils

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.AssociationType

enum class PropertyQueryType {
    EQ,
    LIKE,
    INT_RANGE,
    FLOAT_RANGE,
    TIME_RANGE,
    DATE_RANGE,
    DATETIME_RANGE,
    ENUM_SELECT,
    ASSOCIATION_EQ,
    ASSOCIATION_IN,
}

private val intRangeType = setOf(
    Int::class.qualifiedName,
    "int",
    Int::class.java.name,
    Long::class.qualifiedName,
    "long",
    Long::class.java.name,
)

private val floatRangeType = setOf(
    BigDecimal::class.java.name,

    Float::class.qualifiedName,
    "float",
    Float::class.java.name,
    Double::class.qualifiedName,
    "double",
    Double::class.java.name,
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
        if (associationType != null) {
            when (associationType) {
                AssociationType.MANY_TO_ONE, AssociationType.ONE_TO_ONE ->
                    PropertyQueryType.ASSOCIATION_EQ

                AssociationType.ONE_TO_MANY, AssociationType.MANY_TO_MANY ->
                    PropertyQueryType.ASSOCIATION_IN
            }
        } else if (enum != null) {
            PropertyQueryType.ENUM_SELECT
        } else {
            when(type) {
                in likeType -> PropertyQueryType.LIKE
                in intRangeType -> PropertyQueryType.INT_RANGE
                in floatRangeType -> PropertyQueryType.FLOAT_RANGE
                in timeRangeType -> PropertyQueryType.TIME_RANGE
                in dateRangeType -> PropertyQueryType.DATE_RANGE
                in dateTimeRangeType -> PropertyQueryType.DATETIME_RANGE
                else -> PropertyQueryType.EQ
            }
        }
