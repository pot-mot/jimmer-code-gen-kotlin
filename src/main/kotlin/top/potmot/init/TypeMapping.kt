package top.potmot.init

import java.util.UUID
import top.potmot.entity.database.DatabaseTypeOrAny
import top.potmot.entity.model.JvmLanguageOrAny
import top.potmot.entity.typeMapping.dto.CrossTypeInput
import top.potmot.entity.typeMapping.dto.JvmTypeInput
import top.potmot.entity.typeMapping.dto.SqlTypeInput
import top.potmot.entity.typeMapping.dto.TsTypeInput
import top.potmot.entity.typeMapping.dto.crossTypeWithOrderKey
import top.potmot.entity.typeMapping.dto.jvmTypeWithOrderKey
import top.potmot.entity.typeMapping.dto.sqlTypeWithOrderKey
import top.potmot.entity.typeMapping.dto.tsTypeWithOrderKey

val initJvmTypes = listOf(
    JvmTypeInput(
        id = UUID.fromString("00039100-0000-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "String",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0000-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(n)?(var)?char(acter)?(2)?( varying)?(\\(\\d+\\))?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0000-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(n)?(tiny|small|medium|long)?text$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0000-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(n)?(tiny|small|medium|long)?clob$/i",
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0000-4000-a000-000000000004"),
                matchRegExp = "/^[Ss]tring$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0001-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "int",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0001-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(medium)?int(eger)?(\\(\\d+\\))?$/i",
                nullableLimit = false,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0001-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^number(\\(\\d+\\))?$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0001-4000-a000-000000000003"),
                matchRegExp = "/^[Nn]umber$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0002-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Integer",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0002-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(medium)?int(eger)?(\\(\\d+\\))?$/i",
                nullableLimit = true,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0002-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^number(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0002-4000-a000-000000000003"),
                matchRegExp = "/^[Nn]umber$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0003-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Int",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0003-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(medium)?int(eger)?(\\(\\d+\\))?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0003-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^number(\\(\\d+\\))?$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0003-4000-a000-000000000003"),
                matchRegExp = "/^[Nn]umber$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0004-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "long",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0004-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bigint(\\(\\d+\\))?$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0005-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Long",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0005-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bigint(\\(\\d+\\))?$/i",
                nullableLimit = true,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0006-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Long",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0006-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bigint(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0007-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "short",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0007-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^smallint(\\(\\d+\\))?$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0008-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Short",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0008-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^smallint(\\(\\d+\\))?$/i",
                nullableLimit = true,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0009-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Short",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0009-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^smallint(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-000a-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "float",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000a-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^real$/i",
                nullableLimit = false,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000a-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^float$/i",
                nullableLimit = false,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000a-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)float$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-000b-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Float",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000b-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^real$/i",
                nullableLimit = true,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000b-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^float$/i",
                nullableLimit = true,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000b-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)float$/i",
                nullableLimit = true,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-000c-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Float",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000c-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^real$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000c-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^float$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000c-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)float$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-000d-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "double",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000d-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^double( precision)?$/i",
                nullableLimit = false,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000d-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)double$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-000e-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Double",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000e-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^double( precision)?$/i",
                nullableLimit = true,
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000e-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)double$/i",
                nullableLimit = true,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-000f-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Double",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000f-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^double( precision)?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-000f-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^(binary_)double$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0010-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "boolean",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0010-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0010-4000-a000-000000000002"),
                matchRegExp = "/^[Bb]oolean$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0011-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Boolean",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0011-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i",
                nullableLimit = true,
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0011-4000-a000-000000000002"),
                matchRegExp = "/^[Bb]oolean$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0012-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Boolean",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0012-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i",
            )
        ),
        tsMatchRules = listOf(
            JvmTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039100-0012-4000-a000-000000000002"),
                matchRegExp = "/^[Bb]oolean$/",
            )
        ),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0013-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "byte",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0013-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^tinyint(\\(\\d+\\))?$/i",
                nullableLimit = false,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0014-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.JAVA,
        typeExpression = "Byte",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0014-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^tinyint(\\(\\d+\\))?$/i",
                nullableLimit = true,
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0015-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.KOTLIN,
        typeExpression = "Byte",
        serialized = false,
        extraImports = emptyList(),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0015-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^tinyint(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0016-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "BigDecimal",
        serialized = false,
        extraImports = listOf(
            "java.math.BigDecimal"
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0016-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^decimal(\\(\\d+(,\\d+)?\\))?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0016-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^numeric(\\(\\d+(,\\d+)?\\))?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0016-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^number(\\(\\d+(,\\d+)?\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0017-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "LocalDateTime",
        serialized = false,
        extraImports = listOf(
            "java.time.LocalDateTime"
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0017-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^timestamp(\\(\\d+\\))?( without time zone)?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0017-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^datetime(\\(\\d+\\))?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0017-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ORACLE,
                matchRegExp = "/^datetime2(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0018-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "LocalDate",
        serialized = false,
        extraImports = listOf(
            "java.time.LocalDate"
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0018-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^date( without time zone)?(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-0019-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "LocalTime",
        serialized = false,
        extraImports = listOf(
            "java.time.LocalTime"
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-0019-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^time( without time zone)?(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    JvmTypeInput(
        id = UUID.fromString("00039100-001a-4000-a000-00000000"),
        jvmSource = JvmLanguageOrAny.ANY,
        typeExpression = "ZonedDateTime",
        serialized = false,
        extraImports = listOf(
            "java.time.ZonedDateTime"
        ),
        extraAnnotations = emptyList(),
        sqlMatchRules = listOf(
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-001a-4000-a000-000000000001"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^timestamptz(\\(\\d+\\))?$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-001a-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^timestamp(\\(\\d+\\))? with time zone$/i",
            ),
            JvmTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039100-001a-4000-a000-000000000003"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^datetimeoffset(\\(\\d+\\))?$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
).jvmTypeWithOrderKey()

val initSqlTypes = listOf(
    SqlTypeInput(
        id = UUID.fromString("00039101-0000-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "text",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0000-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/",
            )
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039101-0000-4000-a000-000000000002"),
                matchRegExp = "/^[Ss]tring$/",
            )
        ),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0001-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "varchar(255)",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0001-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/",
            )
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039101-0001-4000-a000-000000000002"),
                matchRegExp = "/^[Ss]tring$/",
            )
        ),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0002-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "char(255)",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0002-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/",
            )
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039101-0002-4000-a000-000000000002"),
                matchRegExp = "/^[Ss]tring$/",
            )
        ),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0003-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "integer",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0003-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.JAVA,
                matchRegExp = "/^(int|Integer)$/",
            ),
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0003-4000-a000-000000000002"),
                jvmSource = JvmLanguageOrAny.KOTLIN,
                matchRegExp = "/^Int$/",
            )
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039101-0003-4000-a000-000000000003"),
                matchRegExp = "/^[Nn]umber$/",
            )
        ),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0004-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "bigint",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0004-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Ll]ong$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0005-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "smallint",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0005-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Ss]hort$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0007-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "real",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0007-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Ff]loat$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0008-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "double precision",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0008-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Dd]ouble$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0009-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "boolean",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0009-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Bb]oolean$/",
            )
        ),
        tsMatchRules = listOf(
            SqlTypeInput.TargetOf_tsMatchRules(
                id = UUID.fromString("00039101-0009-4000-a000-000000000002"),
                matchRegExp = "/^[Bb]oolean$/",
            )
        ),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-0006-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "tinyint",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-0006-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Bb]yte$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-000a-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "decimal(11, 2)",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-000a-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^BigDecimal$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-000b-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "date",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-000b-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^LocalDate$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-000c-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "time",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-000c-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^LocalTime$/i",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-000d-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.ANY,
        type = "timestamp",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-000d-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^LocalDateTime$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
    SqlTypeInput(
        id = UUID.fromString("00039101-000e-4000-a000-00000000"),
        databaseSource = DatabaseTypeOrAny.POSTGRESQL,
        type = "timestamptz",
        jvmMatchRules = listOf(
            SqlTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039101-000e-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^ZonedDateTime$/",
            )
        ),
        tsMatchRules = emptyList(),
    ),
).sqlTypeWithOrderKey()

val initTsTypes = listOf(
    TsTypeInput(
        id = UUID.fromString("00039102-0000-4000-a000-00000000"),
        typeExpression = "string",
        extraImports = emptyList(),
        jvmMatchRules = listOf(
                        TsTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039102-0000-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^String$/"
            )
        ),
        sqlMatchRules = listOf(
                        TsTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039102-0000-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(text|(n)?(var)?char\\(\\d+\\)|(tiny|small|medium|long)?text|character( varying)?\\(\\d+\\))$/i"
            )
        ),
    ),
    TsTypeInput(
        id = UUID.fromString("00039102-0001-4000-a000-00000000"),
        typeExpression = "number",
        extraImports = emptyList(),
        jvmMatchRules = listOf(
                        TsTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039102-0001-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^(int|Integer|Int|long|Long|short|Short|float|Float|double|Double)$/i"
            )
        ),
        sqlMatchRules = listOf(
                        TsTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039102-0001-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^(int(eger)?|tinyint|bigint|smallint|real|float|double( precision)?)\\(\\d+\\)\\)?$/i"
            )
        ),
    ),
    TsTypeInput(
        id = UUID.fromString("00039102-0002-4000-a000-00000000"),
        typeExpression = "boolean",
        extraImports = emptyList(),
        jvmMatchRules = listOf(
                        TsTypeInput.TargetOf_jvmMatchRules(
                id = UUID.fromString("00039102-0002-4000-a000-000000000001"),
                jvmSource = JvmLanguageOrAny.ANY,
                matchRegExp = "/^[Bb]oolean$/i"
            )
        ),
        sqlMatchRules = listOf(
                        TsTypeInput.TargetOf_sqlMatchRules(
                id = UUID.fromString("00039102-0002-4000-a000-000000000002"),
                databaseSource = DatabaseTypeOrAny.ANY,
                matchRegExp = "/^bool(ean)?$/i"
            )
        ),
    ),
).tsTypeWithOrderKey()

val initCrossTypes = listOf(
    CrossTypeInput(
        id = UUID.fromString("00039103-0000-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0000-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0000-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0001-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0000-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0001-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0002-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0000-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0002-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0003-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0001-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0003-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0004-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0002-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0003-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0005-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0003-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0003-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0007-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0004-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0004-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0008-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0005-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0004-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0006-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0006-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0004-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-000a-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0007-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0005-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-000b-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0008-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0005-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0009-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0009-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0005-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-000d-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-000a-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0007-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-000e-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-000b-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0007-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-000c-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-000c-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0007-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0010-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-000d-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0008-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0011-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-000e-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0008-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-000f-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-000f-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0008-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0013-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0010-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0009-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0002-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0014-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0011-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0009-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0002-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0012-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0012-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0009-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0002-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0016-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0013-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0006-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = false,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0017-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0014-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0006-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
        nullable = true,
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0015-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0015-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-0006-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0018-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0016-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-000a-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0001-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-0019-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0017-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-000d-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-001a-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0018-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-000b-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-001b-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-0019-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-000c-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
    CrossTypeInput(
        id = UUID.fromString("00039103-001c-4000-a000-00000000"),
        jvmTypeId = UUID.fromString("00039100-001a-4000-a000-00000000"),
        sqlTypeId = UUID.fromString("00039101-000e-4000-a000-00000000"),
        tsTypeId = UUID.fromString("00039102-0000-4000-a000-00000000"),
    ),
).crossTypeWithOrderKey()
