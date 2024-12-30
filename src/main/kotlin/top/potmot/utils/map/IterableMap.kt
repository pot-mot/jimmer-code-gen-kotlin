package top.potmot.utils.map

fun <K, ITEM> MutableMap<K, Iterable<ITEM>>.merge(
    vararg otherMaps: Map<K, Iterable<ITEM>>
): MutableMap<K, Iterable<ITEM>> {
    for (otherMap in otherMaps) {
        for ((key, value) in otherMap) {
            this[key] = this[key]?.plus(value) ?: value
        }
    }
    return this
}

fun <K, ITEM> iterableMapOf(
    vararg maps: Map<K, Iterable<ITEM>>
): Map<K, Iterable<ITEM>> =
    mutableMapOf<K, Iterable<ITEM>>()
        .merge(*maps)
