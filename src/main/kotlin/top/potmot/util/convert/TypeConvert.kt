package top.potmot.util.convert

import java.math.BigDecimal
import java.sql.Types
import kotlin.reflect.KClass

fun jdbcTypeToJavaType(jdbcType: Int, isNotNull: Boolean = true): Class<*> {
    return when (jdbcType) {
        Types.BIT, Types.BOOLEAN -> if (isNotNull) Boolean::class.java else Boolean::class.javaObjectType
        Types.TINYINT -> if (isNotNull) Byte::class.java else Byte::class.javaObjectType
        Types.SMALLINT -> if (isNotNull) Short::class.java else Short::class.javaObjectType
        Types.INTEGER -> if (isNotNull) Int::class.java else Int::class.javaObjectType
        Types.BIGINT -> if (isNotNull) Long::class.java else Long::class.javaObjectType
        Types.REAL -> if (isNotNull) Float::class.java else Long::class.javaObjectType
        Types.FLOAT, Types.DOUBLE -> if (isNotNull) Double::class.java else Double::class.javaObjectType
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class.java
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR -> String::class.java
        Types.DATE -> java.sql.Date::class.java
        Types.TIME -> java.sql.Time::class.java
        Types.TIMESTAMP -> java.sql.Timestamp::class.java
        Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class.java
        else -> Any::class.java
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
