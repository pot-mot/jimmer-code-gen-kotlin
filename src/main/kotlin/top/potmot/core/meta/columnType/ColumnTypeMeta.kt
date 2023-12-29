package top.potmot.core.meta.columnType

import top.potmot.config.GenConfig
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenColumn
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView

data class ColumnTypeMeta (
    var typeCode: Int,
    var type: String,
    var displaySize: Long,
    var numericPrecision: Long,
    var typeNotNull: Boolean,
    var autoIncrement: Boolean
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
    ColumnTypeMeta(typeCode, type, displaySize, numericPrecision, typeNotNull, autoIncrement)

fun GenTableAssociationsView.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(typeCode, type, displaySize, numericPrecision, typeNotNull, autoIncrement)
