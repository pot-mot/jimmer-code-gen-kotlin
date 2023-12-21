package top.potmot.model.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenColumn
import top.potmot.model.GenTable
import top.potmot.model.dto.GenTableAssociationsView

fun GenTable.getColumn(id: Long): GenColumn? {
    if (!ImmutableObjects.isLoaded(this, "columns")) {
        return null
    }
    return this.columns.find { it.id == id }
}

fun GenTable.getColumn(name: String): GenColumn? {
    if (!ImmutableObjects.isLoaded(this, "columns")) {
        return null
    }
    return this.columns.find { it.name == name }
}

fun GenTableAssociationsView.pkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfPk }
