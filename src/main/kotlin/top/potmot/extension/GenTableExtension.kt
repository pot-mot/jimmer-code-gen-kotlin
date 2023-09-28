package top.potmot.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenColumn
import top.potmot.model.GenProperty
import top.potmot.model.GenTable
import top.potmot.model.copy
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.model.dto.GenTableColumnView

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

fun GenTableColumnView.toColumnMatchViews(): List<GenColumnMatchView> {
    return this.columns.map {
        val table = this.toEntity()
        val column = it.toEntity().copy {
            this.table = table
        }
        GenColumnMatchView(
            column
        )
    }
}

fun GenTable.stringify(): String {
    TODO()
    return this.toString()
}

fun GenColumn.stringify(): String {
    TODO()
    return this.toString()
}
