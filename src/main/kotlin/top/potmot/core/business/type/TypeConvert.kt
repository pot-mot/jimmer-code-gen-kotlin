package top.potmot.core.business.type

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

fun typeStrToJavaType(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName, String::class.java.name -> "String"
        "short", Short::class.qualifiedName, "java.lang.Short" -> if (typeNotNull) "short" else "Short"
        "int", Int::class.qualifiedName, "java.lang.Integer" -> if (typeNotNull) "int" else "Integer"
        "long", Long::class.qualifiedName, "java.lang.Long" -> if (typeNotNull) "long" else "Long"
        "float", Float::class.qualifiedName, "java.lang.Float" -> if (typeNotNull) "float" else "Float"
        "double", Double::class.qualifiedName, "java.lang.Double" -> if (typeNotNull) "double" else "Double"
        "boolean", Boolean::class.qualifiedName, "java.lang.Boolean" -> if (typeNotNull) "boolean" else "Boolean"
        else -> typeStr.substringAfterLast(".")
    }

fun typeStrToKotlinType(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.qualifiedName -> if (typeNotNull) "String" else "String?"
        String::class.java.name -> "String?"
        Short::class.qualifiedName -> if (typeNotNull) "Short" else "Short?"
        "short" -> "Short"
        "java.lang.Short" -> "Short?"
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
        String::class.java.name,
        String::class.qualifiedName,
        Date::class.qualifiedName,
        Instant::class.qualifiedName,
        LocalDate::class.qualifiedName,
        LocalTime::class.qualifiedName,
        LocalDateTime::class.qualifiedName,
        -> if (typeNotNull) "string" else "string | undefined"

        Short::class.qualifiedName, "short", "java.lang.Short",
        Int::class.qualifiedName, "int", "java.lang.Integer",
        Long::class.qualifiedName, "long", "java.lang.Long",
        Float::class.qualifiedName, "float", "java.lang.Float",
        Double::class.qualifiedName, "double", "java.lang.Double",
        BigDecimal::class.java.name,
        -> if (typeNotNull) "number" else "number | undefined"

        Boolean::class.qualifiedName, "boolean", "java.lang.Boolean",
        -> if (typeNotNull) "boolean" else "boolean | undefined"

        else -> typeStr.substringAfterLast(".").let {
            if (typeNotNull) it else "$it | undefined"
        }
    }

fun typeStrToTypeScriptDefault(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        String::class.java.name,
        String::class.qualifiedName,
        Date::class.qualifiedName,
        Instant::class.qualifiedName,
        LocalDate::class.qualifiedName,
        LocalTime::class.qualifiedName,
        LocalDateTime::class.qualifiedName,
        -> if (typeNotNull) "\"\"" else "undefined"

        Short::class.qualifiedName, "short", "java.lang.Short",
        Int::class.qualifiedName, "int", "java.lang.Integer",
        Long::class.qualifiedName, "long", "java.lang.Long",
        Float::class.qualifiedName, "float", "java.lang.Float",
        Double::class.qualifiedName, "double", "java.lang.Double",
        BigDecimal::class.java.name,
        -> if (typeNotNull) "0" else "undefined"

        Boolean::class.qualifiedName, "boolean", "java.lang.Boolean",
        -> if (typeNotNull) "false" else "undefined"

        else -> "undefined"
    }