package top.potmot.core.common

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime
import java.util.Date

val intType = setOf(
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

val numericType = setOf(
    BigDecimal::class.java.name,

    Float::class.qualifiedName,
    "float",
    "java.lang.Float",
    Double::class.qualifiedName,
    "double",
    "java.lang.Double",
)

val booleanType = setOf(
    Boolean::class.qualifiedName,
    "boolean",
    "java.lang.Boolean",
)

val stringType = setOf(
    String::class.qualifiedName,
    String::class.java.name,
)

val timeType = setOf(
    Instant::class.java.name,
    OffsetTime::class.java.name,
    LocalTime::class.java.name
)

val dateType = setOf(
    LocalDate::class.java.name
)

val dateTimeType = setOf(
    LocalDateTime::class.java.name,
    OffsetDateTime::class.java.name,
    ZonedDateTime::class.java.name,
    Date::class.java.name,
)