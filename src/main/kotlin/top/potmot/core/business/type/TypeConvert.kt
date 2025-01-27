package top.potmot.core.business.type

import top.potmot.core.booleanType
import top.potmot.core.dateTimeType
import top.potmot.core.dateType
import top.potmot.core.intType
import top.potmot.core.numericType
import top.potmot.core.stringType
import top.potmot.core.timeType

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
        in stringType,
        in timeType,
        in dateType,
        in dateTimeType,
        -> if (typeNotNull) "string" else "string | undefined"

        in intType,
        in numericType,
        -> if (typeNotNull) "number" else "number | undefined"

        in booleanType,
        -> if (typeNotNull) "boolean" else "boolean | undefined"

        else -> typeStr.substringAfterLast(".").let {
            if (typeNotNull) it else "$it | undefined"
        }
    }

fun typeStrToTypeScriptDefault(typeStr: String, typeNotNull: Boolean) =
    when (typeStr) {
        in stringType,
        in timeType,
        in dateType,
        in dateTimeType,
        -> if (typeNotNull) "\"\"" else "undefined"

        in intType,
        in numericType,
        -> if (typeNotNull) "0" else "undefined"

        in booleanType,
        -> if (typeNotNull) "false" else "undefined"

        else -> "undefined"
    }