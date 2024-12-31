package top.potmot.core.entity.convert.type

import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.columnType.getColumnTypeDefiner
import top.potmot.entity.dto.GenTypeMappingView
import top.potmot.entity.dto.share.ColumnTypeMeta
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.error.ColumnTypeException

/**
 * java.sql.Types 映射为 java 类型
 */
private fun jdbcTypeToJavaType(jdbcType: Int, typeNotNull: Boolean = true): Class<*>? {
    return when (jdbcType) {
        Types.NULL -> null
        Types.JAVA_OBJECT -> JvmType.Object::class.java
        Types.BOOLEAN -> if (typeNotNull) Boolean::class.java else Boolean::class.javaObjectType
        Types.BIT -> if (typeNotNull) Byte::class.java else Byte::class.javaObjectType
        Types.TINYINT,
        Types.SMALLINT,
        -> if (typeNotNull) Short::class.java else Short::class.javaObjectType

        Types.INTEGER -> if (typeNotNull) Int::class.java else Int::class.javaObjectType
        Types.BIGINT -> if (typeNotNull) Long::class.java else Long::class.javaObjectType
        Types.REAL -> if (typeNotNull) Float::class.java else Float::class.javaObjectType
        Types.FLOAT, Types.DOUBLE -> if (typeNotNull) Double::class.java else Double::class.javaObjectType
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class.java
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> String::class.java
        Types.DATE -> LocalDate::class.java
        Types.TIME, Types.TIME_WITH_TIMEZONE -> LocalTime::class.java
        Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> LocalDateTime::class.java
        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class.java
        else -> null
    }
}

/**
 * java.sql.Types 映射为 kotlin 类型
 */
private fun jdbcTypeToKotlinType(jdbcType: Int): KClass<out Any>? {
    return when (jdbcType) {
        Types.NULL -> null
        Types.JAVA_OBJECT -> JvmType.Object::class
        Types.BOOLEAN -> Boolean::class
        Types.BIT -> Byte::class
        Types.TINYINT, Types.SMALLINT -> Short::class
        Types.INTEGER -> Int::class
        Types.BIGINT -> Long::class
        Types.REAL -> Float::class
        Types.FLOAT, Types.DOUBLE -> Double::class
        Types.DECIMAL, Types.NUMERIC -> BigDecimal::class
        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> String::class
        Types.DATE -> LocalDate::class
        Types.TIME, Types.TIME_WITH_TIMEZONE -> LocalTime::class
        Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> LocalDateTime::class
        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> ByteArray::class
        else -> null
    }
}

/**
 * 传入数据源的 typeDefine，根据 typeMapping 进行匹配
 */
private fun mappingPropertyType(
    typeDefine: String,
    typeMappings: List<GenTypeMappingView>
): String? {
    for (typeMapping in typeMappings) {
        if (typeMapping.typeExpression.isBlank()) {
            continue
        }
        if (typeMapping.propertyType.isBlank()) {
            continue
        }
        if (typeDefine.isBlank()) {
            continue
        }

        var matchResult: Boolean

        try {
            val regex = Regex(typeMapping.typeExpression)
            matchResult = regex.matches(typeDefine)
        } catch (e: java.util.regex.PatternSyntaxException) {
            return null
        }

        if (matchResult) {
            return typeMapping.propertyType
        }
    }

    return null
}


/**
 * 基于 ColumnTypeMeta 映射 property type
 * 通过 dataSourceType 和 language 参数获取映射方式
 */
@Throws(ColumnTypeException::class)
fun getPropertyType(
    typeMeta: ColumnTypeMeta,
    typeMappings: List<GenTypeMappingView> = emptyList(),
    dataSourceType: DataSourceType = getContextOrGlobal().dataSourceType,
    language: GenLanguage = getContextOrGlobal().language,
): String =
    mappingPropertyType(
        dataSourceType.getColumnTypeDefiner().getTypeDefine(typeMeta),
        typeMappings.filter { it.language == language && it.dataSourceType == dataSourceType },
    )
        ?: when (language) {
            GenLanguage.JAVA -> jdbcTypeToJavaType(typeMeta.typeCode, typeMeta.typeNotNull)?.name
            GenLanguage.KOTLIN -> jdbcTypeToKotlinType(typeMeta.typeCode)?.qualifiedName
        }
        ?: typeMeta.rawType
