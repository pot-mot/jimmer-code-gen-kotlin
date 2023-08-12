package top.potmot.util.extension

import top.potmot.model.base.Identifiable
import java.util.*

fun <ID, T : Identifiable<ID>> List<T>.toOptionalList(ids: Iterable<ID>): List<Optional<T>> {
    val result = mutableListOf<Optional<T>>()

    for (id in ids) {
        val entity = this.find { it.id == id }
        result += Optional.ofNullable(entity)
    }

    return result
}
