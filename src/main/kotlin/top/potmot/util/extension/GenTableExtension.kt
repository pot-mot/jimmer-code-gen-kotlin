package top.potmot.util.extension

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.model.GenColumn
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.model.dto.GenTableColumnsView
import java.util.*

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

fun GenTableColumnsView.toColumnMatchViews(): List<GenColumnMatchView> {
    return this.columns.map {
        val column = it.toEntity()
        val table = this.toEntity()

        GenColumnMatchView(
            new(GenColumn::class).by(column) {
                this.table = table
            }
        )
    }
}
