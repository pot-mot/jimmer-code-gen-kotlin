package top.potmot.core.meta

import top.potmot.config.GenConfig
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenColumn
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView
import java.sql.Types

data class ColumnTypeMeta (
    var type: String,
    var typeCode: Int,
    var displaySize: Long,
    var numericPrecision: Long,
    var typeNotNull: Boolean,
)

fun ColumnTypeMeta.getPropertyType (
    dataSourceType: DataSourceType = GenConfig.dataSourceType,
    language: GenLanguage = GenConfig.language,
    typeMappings: List<GenTypeMappingView> = emptyList(),
): String =
    top.potmot.core.entity.convert.getPropertyType(
        type,
        typeCode,
        displaySize,
        numericPrecision,
        typeNotNull,
        dataSourceType,
        language,
        typeMappings,
    )

fun GenColumn.getTypeMeta() =
    ColumnTypeMeta(type, typeCode, displaySize, numericPrecision, typeNotNull)

fun GenTableAssociationsView.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(type, typeCode, displaySize, numericPrecision, typeNotNull)


fun getFullType(typeCode: Int, type: String, displaySize: Long, numericPrecision: Long): String =
    when (typeCode) {
        Types.BIT, Types.BOOLEAN,
        Types.TINYINT,
        Types.SMALLINT,
        Types.INTEGER,
        Types.BIGINT -> "$type($displaySize)"

        Types.REAL,
        Types.FLOAT, Types.DOUBLE,
        Types.DECIMAL, Types.NUMERIC -> "$type($displaySize, $numericPrecision)"

        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
        Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR,
        Types.CLOB, Types.NCLOB,
        Types.SQLXML, Types.DATALINK -> "$type($displaySize)"

        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> "$type($displaySize)"

        Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> type

        Types.NULL, Types.JAVA_OBJECT -> type

        else -> type
    }

fun ColumnTypeMeta.fullType(): String =
    getFullType(typeCode, type, displaySize, numericPrecision)
