package top.potmot.core.database.meta

import top.potmot.model.GenColumn
import top.potmot.model.dto.ColumnTypeMeta
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableModelInput

fun GenColumn.getTypeMeta() =
    ColumnTypeMeta(this)

fun GenTableModelInput.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(this.toEntity())

fun GenTableAssociationsView.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(this.toEntity())
