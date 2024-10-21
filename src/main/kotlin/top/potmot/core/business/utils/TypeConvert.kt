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
        Int::class.qualifiedName -> if (typeNotNull) "int" else "Integer"
        Int::class.java.name -> "Integer"
        Long::class.qualifiedName -> if (typeNotNull) "long" else "Long"
        Long::class.java.name -> "Long"
        Float::class.qualifiedName -> if (typeNotNull) "float" else "Float"
        Float::class.java.name -> "Float"
        Double::class.qualifiedName -> if (typeNotNull) "double" else "Double"
        Double::class.java.name -> "Double"
        Boolean::class.qualifiedName -> if (typeNotNull) "boolean" else "Boolean"
        Boolean::class.java.name -> "Boolean"
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
        LocalDateTime::class.qualifiedName -> if (typeNotNull) "\"\"" else "undefined"
        String::class.java.name -> "undefined"

        Int::class.qualifiedName, "int",
        Long::class.qualifiedName, "long",
        Float::class.qualifiedName, "float",
        Double::class.qualifiedName, "double",
        BigDecimal::class.java.name -> if (typeNotNull) "0" else "undefined"

        Int::class.java.name,
        Long::class.java.name,
        Float::class.java.name,
        Double::class.java.name -> "undefined"

        Boolean::class.qualifiedName, "boolean" -> if (typeNotNull) "false" else "undefined"
        Boolean::class.java.name -> "undefined"

        else -> "undefined"
    }