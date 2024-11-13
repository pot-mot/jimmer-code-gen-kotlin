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
        Int::class.qualifiedName, "java.lang.Integer" -> if (typeNotNull) "int" else "Integer"
        Long::class.qualifiedName, "java.lang.Long" -> if (typeNotNull) "long" else "Long"
        Float::class.qualifiedName, "java.lang.Float" -> if (typeNotNull) "float" else "Float"
        Double::class.qualifiedName, "java.lang.Double" -> if (typeNotNull) "double" else "Double"
        Boolean::class.qualifiedName, "java.lang.Boolean" -> if (typeNotNull) "boolean" else "Boolean"
        else -> typeStr.substringAfterLast(".")
    }

fun typeStrToKotlinType(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName -> if (typeNotNull) "String" else "String?"
        String::class.java.name -> "String?"
        Int::class.qualifiedName -> if (typeNotNull) "Int" else "Int?"
        "int" -> "Int"
        "java.lang.Integer" -> "Int?"
        Long::class.qualifiedName -> if (typeNotNull) "Long" else "Long?"
        "long" -> "Long"
        "java.lang.Long" -> "Long?"
        Float::class.qualifiedName -> if (typeNotNull) "Float" else "Float?"
        "float" -> "Float"
        "java.lang.Float" -> "Float?"
        Double::class.qualifiedName -> if (typeNotNull) "Double" else "Double?"
        "double" -> "Double"
        "java.lang.Double" -> "Double?"
        Boolean::class.qualifiedName -> if (typeNotNull) "Boolean" else "Boolean?"
        "boolean" -> "Boolean"
        "java.lang.Boolean" -> "Boolean?"
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

        "java.lang.Integer",
        "java.lang.Long",
        "java.lang.Float",
        "java.lang.Double" -> "number | undefined"

        Boolean::class.qualifiedName, "boolean" -> if (typeNotNull) "boolean" else "boolean | undefined"
        "java.lang.Boolean" -> "boolean | undefined"

        else -> typeStr.substringAfterLast(".").let {
            if (typeNotNull) it else "$it | undefined"
        }
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
        "java.lang.Integer",
        "java.lang.Long",
        "java.lang.Float",
        "java.lang.Double" -> if (typeNotNull) "0" else "undefined"

        Boolean::class.qualifiedName,
        "boolean",
        "java.lang.Boolean" -> if (typeNotNull) "false" else "undefined"

        else -> "undefined"
    }