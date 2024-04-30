package top.potmot.core.database.meta

import top.potmot.entity.dto.ColumnTypeMeta
import top.potmot.entity.dto.GenTableAssociationsView

fun GenTableAssociationsView.TargetOf_columns.getTypeMeta() =
    ColumnTypeMeta(this.toEntity())
