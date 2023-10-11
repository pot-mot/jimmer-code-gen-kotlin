package top.potmot.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenColumn
import top.potmot.model.GenTable

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
