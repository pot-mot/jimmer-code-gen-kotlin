package top.potmot.core.business.utils

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date
import top.potmot.entity.dto.GenEntityBusinessView

enum class PropertyFormType {
    INPUT,
    SWITCH,
    NUMBER,
    TIME,
    DATE,
    DATETIME,
    ENUM,
    ASSOCIATION,
}

private val numberType = setOf(
    BigDecimal::class.java.name,

    Int::class.qualifiedName,
    "int",
    Int::class.java.name,
    Long::class.qualifiedName,
    "long",
    Long::class.java.name,
    Float::class.qualifiedName,
    "float",
    Float::class.java.name,
    Double::class.qualifiedName,
    "double",
    Double::class.java.name,
)

private val switchType = setOf(
    Boolean::class.qualifiedName,
    "boolean",
    Boolean::class.java.name,
)

private val timeType = setOf(
    Instant::class.java.name,
    LocalTime::class.java.name
)

private val dateType = setOf(
    LocalDate::class.java.name
)

private val dateTimeType = setOf(
    LocalDateTime::class.java.name,
    Date::class.java.name
)

val GenEntityBusinessView.TargetOf_properties.formType: PropertyFormType
    get() =
        if (associationType != null) {
            PropertyFormType.ASSOCIATION
        } else if (enum != null) {
            PropertyFormType.ENUM
        } else {
            when (type) {
                in numberType -> PropertyFormType.NUMBER
                in switchType -> PropertyFormType.SWITCH
                in dateType -> PropertyFormType.DATE
                in timeType -> PropertyFormType.TIME
                in dateTimeType -> PropertyFormType.DATETIME
                else -> PropertyFormType.INPUT
            }
        }