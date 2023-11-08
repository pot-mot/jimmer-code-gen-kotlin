package top.potmot.core.convert

import top.potmot.config.GenConfig
import top.potmot.core.template.table.fullType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenTypeMapping
import top.potmot.model.dto.GenTableAssociationsView
import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * java.sql.Types 映射为 java 类型
 */
fun jdbcTypeToJavaType(jdbcType: Int, typeNotNull: Boolean = true): Class<*>? {
    return when (jdbcType) {
        Types.NULL -> null
        Types.JAVA_OBJECT -> JvmType.Object::class.java
        Types.BIT, Types.BOOLEAN -> if (typeNotNull) Boolean::class.java else Boolean::class.javaObjectType
        Types.TINYINT -> if (typeNotNull) Byte::class.java else Byte::class.javaObjectType
        Types.SMALLINT -> if (typeNotNull) Short::class.java else Short::class.javaObjectType
        Types.INTEGER -> if (typeNotNull) Int::class.java else Int::class.javaObjectType
        Types.BIGINT -> if (typeNotNull) Long::class.java else Long::class.javaObjectType
        Types.REAL -> if (typeNotNull) Float::class.java else Float::class.javaObjectType
        Types.FLOAT, Types.DOUBLE -> if (typeNotNull) Double::class.java else Double::class.javaObjectType
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class.java
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> String::class.java
        Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> LocalDateTime::class.java
        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class.java
        else -> null
    }
}

/**
 * java.sql.Types 映射为 kotlin 类型
 */
fun jdbcTypeToKotlinType(jdbcType: Int): KClass<out Any>? {
    return when (jdbcType) {
        Types.NULL -> null
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
        else -> null
    }
}

/**
 * 设置属性类型
 */
fun GenTableAssociationsView.TargetOf_columns.mappingPropertyType(
    typeMappings: List<GenTypeMapping>,
    language: GenLanguage = GenConfig.language,
): String {
    for (typeMapping in typeMappings) {
        val matchResult =
            if (typeMapping.regex) {
                Regex(typeMapping.typeExpression).matches(fullType())
            } else {
                typeMapping.typeExpression == fullType()
            }
        if (matchResult) {
            return typeMapping.propertyType
        }
    }

    return when (language) {
        GenLanguage.JAVA -> jdbcTypeToJavaType(typeCode, typeNotNull)?.name ?: type
        GenLanguage.KOTLIN -> jdbcTypeToKotlinType(typeCode)?.qualifiedName ?: type
    }
}
