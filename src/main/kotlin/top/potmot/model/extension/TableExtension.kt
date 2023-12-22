package top.potmot.model.extension

import top.potmot.model.dto.GenTableAssociationsView

fun GenTableAssociationsView.pkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfPk }
