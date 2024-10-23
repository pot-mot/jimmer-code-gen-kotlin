package top.potmot.core.business.utils

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

fun typeStrToJavaType(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName, String::class.java.name -> "String"
        Int::class.qualifiedName, Int::class.java.name -> if (typeNotNull) "int" else "Integer"
        Long::class.qualifiedName, Long::class.java.name -> if (typeNotNull) "long" else "Long"
        Float::class.qualifiedName, Float::class.java.name -> if (typeNotNull) "float" else "Float"
        Double::class.qualifiedName, Double::class.java.name -> if (typeNotNull) "double" else "Double"
        Boolean::class.qualifiedName, Boolean::class.java.name -> if (typeNotNull) "boolean" else "Boolean"
        else -> typeStr.substringAfterLast(".")
    }

fun typeStrToKotlinType(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName -> if (typeNotNull) "String" else "String?"
        String::class.java.name -> "String?"
        Int::class.qualifiedName -> if (typeNotNull) "Int" else "Int?"
        "int" -> "Int"
        Int::class.java.name -> "Int?"
        Long::class.qualifiedName -> if (typeNotNull) "Long" else "Long?"
        "long" -> "Long"
        Long::class.java.name -> "Long?"
        Float::class.qualifiedName -> if (typeNotNull) "Float" else "Float?"
        "float" -> "Float"
        Float::class.java.name -> "Float?"
        Double::class.qualifiedName -> if (typeNotNull) "Double" else "Double?"
        "double" -> "Double"
        Double::class.java.name -> "Double?"
        Boolean::class.qualifiedName -> if (typeNotNull) "Boolean" else "Boolean?"
        "boolean" -> "Boolean"
        Boolean::class.java.name -> "Boolean?"
        else -> typeStr.substringAfterLast(".")
    }

fun typeStrToTypeScriptType(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName,
        Date::class.qualifiedName,
        Instant::class.qualifiedName,
        LocalDate::class.qualifiedName,
        LocalTime::class.qualifiedName,
        LocalDateTime::class.qualifiedName -> if (typeNotNull) "string" else "string | undefined"
        String::class.java.name -> "string | undefined"

        Int::class.qualifiedName, "int",
        Long::class.qualifiedName, "long",
        Float::class.qualifiedName, "float",
        Double::class.qualifiedName, "double",
        BigDecimal::class.java.name -> if (typeNotNull) "number" else "number | undefined"

        Int::class.java.name,
        Long::class.java.name,
        Float::class.java.name,
        Double::class.java.name -> "number | undefined"

        Boolean::class.qualifiedName, "boolean" -> if (typeNotNull) "boolean" else "boolean | undefined"
        Boolean::class.java.name -> "boolean | undefined"

        else -> typeStr.substringAfterLast(".")
    }

fun typeStrToTypeScriptDefault(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName,
        Date::class.qualifiedName,
        Instant::class.qualifiedName,
        LocalDate::class.qualifiedName,
        LocalTime::class.qualifiedName,
        LocalDateTime::class.qualifiedName,
        String::class.java.name -> if (typeNotNull) "\"\"" else "undefined"

        Int::class.qualifiedName, "int",
        Long::class.qualifiedName, "long",
        Float::class.qualifiedName, "float",
        Double::class.qualifiedName, "double",
        BigDecimal::class.java.name,
        Int::class.java.name,
        Long::class.java.name,
        Float::class.java.name,
        Double::class.java.name -> if (typeNotNull) "0" else "undefined"

        Boolean::class.qualifiedName,
        "boolean",
        Boolean::class.java.name -> if (typeNotNull) "false" else "undefined"

        else -> "undefined"
    }