package top.potmot.core.business.meta

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
    INT,
    FLOAT,
    TIME,
    DATE,
    DATETIME,
    ENUM,
    ASSOCIATION_ID,
    ASSOCIATION_ID_LIST,
}

private val intType = setOf(
    Int::class.qualifiedName,
    "int",
    "java.lang.Integer",
    Long::class.qualifiedName,
    "long",
    "java.lang.Long",
    Short::class.qualifiedName,
    "short",
    "java.lang.Short",
)

private val floatType = setOf(
    BigDecimal::class.java.name,

    Float::class.qualifiedName,
    "float",
    "java.lang.Float",
    Double::class.qualifiedName,
    "double",
    "java.lang.Double",
)

private val switchType = setOf(
    Boolean::class.qualifiedName,
    "boolean",
    "java.lang.Boolean",
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
            if (listType) {
                PropertyFormType.ASSOCIATION_ID_LIST
            } else {
                PropertyFormType.ASSOCIATION_ID
            }
        } else if (enum != null) {
            PropertyFormType.ENUM
        } else {
            when (type) {
                in intType -> PropertyFormType.INT
                in floatType -> PropertyFormType.FLOAT
                in switchType -> PropertyFormType.SWITCH
                in dateType -> PropertyFormType.DATE
                in timeType -> PropertyFormType.TIME
                in dateTimeType -> PropertyFormType.DATETIME
                else -> PropertyFormType.INPUT
            }
        }