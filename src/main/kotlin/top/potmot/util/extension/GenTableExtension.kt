package top.potmot.util.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenColumn
import top.potmot.model.GenTable
import java.util.*

fun GenTable.getColumn(id: Long): Optional<GenColumn> {
    if (!ImmutableObjects.isLoaded(this, "columns")) {
        return Optional.empty()
    }

    val result = this.columns.find { it.id == id }
    return Optional.ofNullable(result)
}

fun GenTable.getColumn(name: String): Optional<GenColumn> {
    if (!ImmutableObjects.isLoaded(this, "columns")) {
        return Optional.empty()
    }

    val result = this.columns.find { it.columnName == name }
    return Optional.ofNullable(result)
}

