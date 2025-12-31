package top.potmot.init

import top.potmot.entity.database.DatabaseTypeOrAny
import top.potmot.entity.model.JvmLanguageOrAny
import top.potmot.entity.typeMapping.dto.CrossTypeInput
import top.potmot.entity.typeMapping.dto.JvmTypeInput
import top.potmot.entity.typeMapping.dto.JvmTypeInput.TargetOf_sqlMatchRules
import top.potmot.entity.typeMapping.dto.JvmTypeInput.TargetOf_tsMatchRules
import top.potmot.entity.typeMapping.dto.SqlTypeInput
import top.potmot.entity.typeMapping.dto.TsTypeInput
import top.potmot.entity.typeMapping.dto.crossTypeWithOrderKey
import top.potmot.entity.typeMapping.dto.jvmTypeWithOrderKey
import top.potmot.entity.typeMapping.dto.sqlTypeWithOrderKey
import top.potmot.entity.typeMapping.dto.tsTypeWithOrderKey
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.UUID

private enum class InitTypeIds(val value: UUID = UUID.randomUUID()) {
    JVM_STRING_ID,
    JAVA_INT_PRIMITIVE_ID,
    JAVA_INTEGER_OBJECT_ID,
    KT_INT_ID,
    JAVA_LONG_PRIMITIVE_ID,
    JAVA_LONG_OBJECT_ID,
    KT_LONG_ID,
    JAVA_SHORT_PRIMITIVE_ID,
    JAVA_SHORT_OBJECT_ID,
    KT_SHORT_ID,
    JAVA_FLOAT_PRIMITIVE_ID,
    JAVA_FLOAT_OBJECT_ID,
    KT_FLOAT_ID,
    JAVA_DOUBLE_PRIMITIVE_ID,
    JAVA_DOUBLE_OBJECT_ID,
    KT_DOUBLE_ID,
    JAVA_BOOLEAN_PRIMITIVE_ID,
    JAVA_BOOLEAN_OBJECT_ID,
    KT_BOOLEAN_ID,
    JAVA_BYTE_PRIMITIVE_ID,
    JAVA_BYTE_OBJECT_ID,
    KT_BYTE_ID,
    JVM_BIG_DECIMAL_ID,
    JVM_LOCAL_DATE_TIME_ID,
    JVM_LOCAL_DATE_ID,
    JVM_LOCAL_TIME_ID,
    JVM_ZONED_DATE_TIME_ID,

    SQL_TEXT_ID,
    SQL_VARCHAR255_ID,
    SQL_CHAR255_ID,
    SQL_INTEGER_ID,
    SQL_BIGINT_ID,
    SQL_SMALLINT_ID,
    SQL_TINYINT_ID,
    SQL_REAL_ID,
    SQL_DOUBLE_PRECISION_ID,
    SQL_BOOLEAN_ID,
    SQL_DECIMAL_11_2_ID,
    SQL_DATE_ID,
    SQL_TIME_ID,
    SQL_TIMESTAMP_ID,
    SQL_TIMESTAMPTZ_ID,

    TS_STRING_ID,
    TS_NUMBER_ID,
    TS_BOOLEAN_ID,
}

val initJvmTypes = listOf(
    JvmTypeInput(
        id = InitTypeIds.JVM_STRING_ID.value,
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "String",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(n)?(var)?char(acter)?(2)?( varying)?(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(n)?(tiny|small|medium|long)?text$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(n)?(tiny|small|medium|long)?clob$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Ss]tring$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_INT_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "int",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(medium)?int(eger)?(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^number(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Nn]umber$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_INTEGER_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Integer",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(medium)?int(eger)?(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^number(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Nn]umber$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_INT_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Int",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(medium)?int(eger)?(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^number(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Nn]umber$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_LONG_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "long",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bigint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_LONG_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Long",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bigint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_LONG_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Long",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bigint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_SHORT_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "short",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^smallint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_SHORT_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Short",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^smallint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_SHORT_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Short",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^smallint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_FLOAT_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "float",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^real$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^float$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)float$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_FLOAT_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Float",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^real$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^float$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)float$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_FLOAT_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Float",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^real$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^float$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)float$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_DOUBLE_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "double",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^double( precision)?$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)double$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_DOUBLE_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Double",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^double( precision)?$/i"
            ),
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)double$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_DOUBLE_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Double",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^double( precision)?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)double$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_BOOLEAN_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "boolean",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Bb]oolean$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_BOOLEAN_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Boolean",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Bb]oolean$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_BOOLEAN_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Boolean",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i"
            ),
        ),
        tsMatchRules = listOf(
            TargetOf_tsMatchRules(
                matchRegExp = "/^[Bb]oolean$/"
            ),
        )
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_BYTE_PRIMITIVE_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "byte",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = false,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^tinyint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JAVA_BYTE_OBJECT_ID.value,
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Byte",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                nullableLimit = true,
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^tinyint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.KT_BYTE_ID.value,
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Byte",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^tinyint(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JVM_BIG_DECIMAL_ID.value,
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "BigDecimal",
        serialized = false,
        extraImports = listOf(
            BigDecimal::class.java.name
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^decimal(\\(\\d+(\\,\\d+)?\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^numeric(\\(\\d+\\(\\,\\d+)?\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^number(\\(\\d+\\(\\,\\d+)?\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JVM_LOCAL_DATE_TIME_ID.value,
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "LocalDateTime",
        serialized = false,
        extraImports = listOf(
            LocalDateTime::class.java.name
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^timestamp( without time zone)?(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^datetime(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^datetime2(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JVM_LOCAL_DATE_ID.value,
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "LocalDate",
        serialized = false,
        extraImports = listOf(
            LocalDate::class.java.name
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^date( without time zone)?(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JVM_LOCAL_TIME_ID.value,
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "LocalTime",
        serialized = false,
        extraImports = listOf(
            LocalTime::class.java.name
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^time( without time zone)?(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    ),
    JvmTypeInput(
        id = InitTypeIds.JVM_ZONED_DATE_TIME_ID.value,
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "ZonedDateTime",
        serialized = false,
        extraImports = listOf(
            ZonedDateTime::class.java.name
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^timestamp(tz| with time zone)(\\(\\d+\\))?$/i"
            ),
            TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^datetimeoffset(\\(\\d+\\))?$/i"
            ),
        ),
        tsMatchRules = emptyList()
    )
).jvmTypeWithOrderKey()

val initSqlTypes = listOf(
    SqlTypeInput(
        id = InitTypeIds.SQL_TEXT_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "text",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/"
            ),
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                matchRegExp = "/^[Ss]tring$/"
            ),
        ),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_VARCHAR255_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "varchar(255)",
        dataSize = 255,
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/"
            ),
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                matchRegExp = "/^[Ss]tring$/"
            ),
        ),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_CHAR255_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "char(255)",
        dataSize = 255,
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/"
            ),
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                matchRegExp = "/^[Ss]tring$/"
            ),
        ),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_INTEGER_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "integer",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.JAVA,
                matchRegExp = "/^(int|Integer)$/"
            ),
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.KOTLIN,
                matchRegExp = "/^Int$/"
            ),
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                matchRegExp = "/^[Nn]umber$/"
            ),
        ),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_BIGINT_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "bigint",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Ll]ong$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_SMALLINT_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "smallint",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Ss]hort$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_REAL_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "real",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Ff]loat$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_DOUBLE_PRECISION_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "double precision",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Dd]ouble$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_BOOLEAN_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "boolean",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Bb]oolean$/"
            ),
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                matchRegExp = "/^[Bb]oolean$/"
            ),
        ),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_TINYINT_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "tinyint",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Bb]yte$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_DECIMAL_11_2_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "decimal(11, 2)",
        dataSize = 11,
        numericPrecision = 2,
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^BigDecimal$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_DATE_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "date",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^LocalDate$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_TIME_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "time",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^LocalTime$/i"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_TIMESTAMP_ID.value,
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "timestamp",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^LocalDateTime$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = InitTypeIds.SQL_TIMESTAMPTZ_ID.value,
        databaseSource = DatabaseTypeOrAny.POSTGRESQL,
        type = "timestamptz",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^ZonedDateTime$/"
            ),
        ),
        tsMatchRules = emptyList(),
    ),
).sqlTypeWithOrderKey()


val initTsTypes = listOf(
    TsTypeInput(
        id = InitTypeIds.TS_STRING_ID.value,
        typeExpression = "string",
        extraImports = emptyList(),
        jvmMatchRules = listOf(
            TsTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/"
            ),
        ),
        sqlMatchRules = listOf(
            TsTypeInput.TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(text|(n)?(var)?char\\(\\d+\\)|(tiny|small|medium|long)?text|character( varying)?\\(\\d+\\))?$/i"
            ),
        ),
    ),
    TsTypeInput(
        id = InitTypeIds.TS_NUMBER_ID.value,
        typeExpression = "number",
        extraImports = emptyList(),
        jvmMatchRules = listOf(
            TsTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^(int|Integer|Int|long|Long|short|Short|float|Float|double|Double)$/i"
            ),
        ),
        sqlMatchRules = listOf(
            TsTypeInput.TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(int(eger)?|tinyint|bigint|smallint|real|float|double( precision)?)\\(\\d+\\)\\)?$/i"
            ),
        ),
    ),
    TsTypeInput(
        id = InitTypeIds.TS_BOOLEAN_ID.value,
        typeExpression = "boolean",
        extraImports = emptyList(),
        jvmMatchRules = listOf(
            TsTypeInput.TargetOf_jvmMatchRules(
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Bb]oolean$/i"
            ),
        ),
        sqlMatchRules = listOf(
            TsTypeInput.TargetOf_sqlMatchRules(
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i"
            ),
        ),
    )
).tsTypeWithOrderKey()

val initCrossTypes = listOf(
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_STRING_ID.value,
        sqlTypeId = InitTypeIds.SQL_TEXT_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_STRING_ID.value,
        sqlTypeId = InitTypeIds.SQL_VARCHAR255_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_STRING_ID.value,
        sqlTypeId = InitTypeIds.SQL_CHAR255_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_INT_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_INTEGER_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_INTEGER_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_INTEGER_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_INT_ID.value,
        sqlTypeId = InitTypeIds.SQL_INTEGER_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_LONG_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_BIGINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_LONG_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_BIGINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_LONG_ID.value,
        sqlTypeId = InitTypeIds.SQL_BIGINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_SHORT_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_SMALLINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_SHORT_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_SMALLINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_SHORT_ID.value,
        sqlTypeId = InitTypeIds.SQL_SMALLINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_FLOAT_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_REAL_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_FLOAT_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_REAL_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_FLOAT_ID.value,
        sqlTypeId = InitTypeIds.SQL_REAL_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_DOUBLE_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_DOUBLE_PRECISION_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_DOUBLE_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_DOUBLE_PRECISION_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_DOUBLE_ID.value,
        sqlTypeId = InitTypeIds.SQL_DOUBLE_PRECISION_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_BOOLEAN_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_BOOLEAN_ID.value,
        tsTypeId = InitTypeIds.TS_BOOLEAN_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_BOOLEAN_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_BOOLEAN_ID.value,
        tsTypeId = InitTypeIds.TS_BOOLEAN_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_BOOLEAN_ID.value,
        sqlTypeId = InitTypeIds.SQL_BOOLEAN_ID.value,
        tsTypeId = InitTypeIds.TS_BOOLEAN_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_BYTE_PRIMITIVE_ID.value,
        sqlTypeId = InitTypeIds.SQL_TINYINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = false,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JAVA_BYTE_OBJECT_ID.value,
        sqlTypeId = InitTypeIds.SQL_TINYINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
        nullable = true,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.KT_BYTE_ID.value,
        sqlTypeId = InitTypeIds.SQL_TINYINT_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_BIG_DECIMAL_ID.value,
        sqlTypeId = InitTypeIds.SQL_DECIMAL_11_2_ID.value,
        tsTypeId = InitTypeIds.TS_NUMBER_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_LOCAL_DATE_TIME_ID.value,
        sqlTypeId = InitTypeIds.SQL_TIMESTAMP_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_LOCAL_DATE_ID.value,
        sqlTypeId = InitTypeIds.SQL_DATE_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_LOCAL_TIME_ID.value,
        sqlTypeId = InitTypeIds.SQL_TIME_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    ),
    CrossTypeInput(
        jvmTypeId = InitTypeIds.JVM_ZONED_DATE_TIME_ID.value,
        sqlTypeId = InitTypeIds.SQL_TIMESTAMPTZ_ID.value,
        tsTypeId = InitTypeIds.TS_STRING_ID.value,
    )
).crossTypeWithOrderKey()
