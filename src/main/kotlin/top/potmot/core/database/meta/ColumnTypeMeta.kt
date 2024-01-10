package top.potmot.core.database.meta

import top.potmot.model.GenColumn
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableModelInput

data class ColumnTypeMeta (
    var typeCode: Int,
    var overwriteByType: Boolean,
    var type: String,
    var displaySize: Long,
    var numericPrecision: Long,
    var typeNotNull: Boolean,
    var autoIncrement: Boolean
)

fun GenColumn.getTypeMeta() =
    ColumnTypeMeta(typeCode, overwriteByType, type, displaySize, numericPrecision, typeNotNull, autoIncrement)

fun GenTableModelInput.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(typeCode, overwriteByType, type, displaySize, numericPrecision, typeNotNull, autoIncrement)

fun GenTableAssociationsView.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(typeCode, overwriteByType, type, displaySize, numericPrecision, typeNotNull, autoIncrement)
