package top.potmot.util

import java.math.BigDecimal
import java.sql.Types
import kotlin.reflect.KClass

fun jdbcTypeToJavaType(jdbcType: Int, isNotNull: Boolean): Class<*> {
    if (isNotNull) {
        return when (jdbcType) {
            Types.BIT, Types.BOOLEAN -> Boolean::class.java
            Types.TINYINT -> Byte::class.java
            Types.SMALLINT -> Short::class.java
            Types.INTEGER -> Int::class.java
            Types.BIGINT -> Long::class.java
            Types.REAL -> Float::class.java
            Types.FLOAT, Types.DOUBLE -> Double::class.java
            Types.DECIMAL, Types.NUMERIC -> BigDecimal::class.java
            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR -> String::class.java
            Types.DATE -> java.sql.Date::class.java
            Types.TIME -> java.sql.Time::class.java
            Types.TIMESTAMP -> java.sql.Timestamp::class.java
            Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class.java
            else -> Any::class.java
        }
    } else {
        return when (jdbcType) {
            Types.BIT, Types.BOOLEAN -> Boolean::class.javaObjectType
            Types.TINYINT -> Byte::class.javaObjectType
            Types.SMALLINT -> Short::class.javaObjectType
            Types.INTEGER -> Int::class.javaObjectType
            Types.BIGINT -> Long::class.javaObjectType
            Types.REAL -> Float::class.javaObjectType
            Types.FLOAT, Types.DOUBLE -> Double::class.javaObjectType
            Types.DECIMAL, Types.NUMERIC -> BigDecimal::class.java
            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR -> String::class.java
            Types.DATE -> java.sql.Date::class.java
            Types.TIME -> java.sql.Time::class.java
            Types.TIMESTAMP -> java.sql.Timestamp::class.java
            Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class.java
            else -> Any::class.java
        }
    }
}


fun jdbcTypeToKotlinType(jdbcType: Int): KClass<out Any> {
    return when (jdbcType) {
        Types.BIT, Types.BOOLEAN -> Boolean::class
        Types.TINYINT -> Byte::class
        Types.SMALLINT -> Short::class
        Types.INTEGER -> Int::class
        Types.BIGINT -> Long::class
        Types.REAL -> Float::class
        Types.FLOAT, Types.DOUBLE -> Double::class
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR -> String::class
        Types.DATE -> java.sql.Date::class
        Types.TIME -> java.sql.Time::class
        Types.TIMESTAMP -> java.sql.Timestamp::class
        Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class
        else -> Any::class
    }
}
