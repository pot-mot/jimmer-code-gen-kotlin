package top.potmot.core.database.meta

import top.potmot.model.dto.ColumnTypeMeta
import top.potmot.model.dto.GenTableAssociationsView

fun GenTableAssociationsView.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(this.toEntity())
