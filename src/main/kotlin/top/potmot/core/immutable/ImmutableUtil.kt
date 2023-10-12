package top.potmot.core.immutable

import org.babyfish.jimmer.runtime.DraftSpi
import org.babyfish.jimmer.runtime.ImmutableSpi

fun <T> copyProperties(baseEntity: T, draftEntity: T) {
    val base = baseEntity as ImmutableSpi
    val draft = draftEntity as DraftSpi
    for (prop in base.__type().props.values) {
        if (base.__isLoaded(prop.id)) {
            draft.__set(prop.id, base.__get(prop.id))
        }
    }
}
