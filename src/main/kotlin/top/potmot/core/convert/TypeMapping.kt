package top.potmot.core.convert

import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

fun jdbcTypeToJavaType(jdbcType: Int, isNotNull: Boolean = true): Class<*> {
    return when (jdbcType) {
        Types.NULL -> Any::class.java
        Types.JAVA_OBJECT -> JvmType.Object::class.java
        Types.BIT, Types.BOOLEAN -> if (isNotNull) Boolean::class.java else Boolean::class.javaObjectType
        Types.TINYINT -> if (isNotNull) Byte::class.java else Byte::class.javaObjectType
        Types.SMALLINT -> if (isNotNull) Short::class.java else Short::class.javaObjectType
        Types.INTEGER -> if (isNotNull) Int::class.java else Int::class.javaObjectType
        Types.BIGINT -> if (isNotNull) Long::class.java else Long::class.javaObjectType
        Types.REAL -> if (isNotNull) Float::class.java else Long::class.javaObjectType
        Types.FLOAT, Types.DOUBLE -> if (isNotNull) Double::class.java else Double::class.javaObjectType
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class.java
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> String::class.java
        Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> LocalDateTime::class.java
        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class.java
        else -> Any::class.java
    }
}


fun jdbcTypeToKotlinType(jdbcType: Int): KClass<out Any> {
    return when (jdbcType) {
        Types.NULL -> Any::class
        Types.JAVA_OBJECT -> JvmType.Object::class
        Types.BIT, Types.BOOLEAN -> Boolean::class
        Types.TINYINT -> Byte::class
        Types.SMALLINT -> Short::class
        Types.INTEGER -> Int::class
        Types.BIGINT -> Long::class
        Types.REAL -> Float::class
        Types.FLOAT, Types.DOUBLE -> Double::class
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> String::class
        Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> LocalDateTime::class
        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class
        else -> Any::class
    }
}
