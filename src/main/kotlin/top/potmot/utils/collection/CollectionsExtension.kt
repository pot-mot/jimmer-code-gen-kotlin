package top.potmot.utils.collection

fun <T> Iterable<T>.join(separator: T): List<T> {
    val result = ArrayList<T>()
    val iterator = iterator()

    if (iterator.hasNext()) {
        // 处理第一个元素
        result.add(iterator.next())

        // 从第二个元素开始循环
        while (iterator.hasNext()) {
            result.add(separator)
            result.add(iterator.next())
        }
    }
    return result
}

fun <T> Iterable<T>.forEachJoinDo(joinAction: (T) -> Unit, action: (T) -> Unit) {
    val iterator = iterator()

    if (iterator.hasNext()) {
        // 处理第一个元素
        action(iterator.next())

        // 从第二个元素开始循环
        while (iterator.hasNext()) {
            val it = iterator.next()
            joinAction(it)
            action(it)
        }
    }
}

fun <K, V> Map<K, V>.forEachJoinDo(joinAction: (K, V) -> Unit, action: (K, V) -> Unit) {
    val iterator = iterator()

    if (iterator.hasNext()) {
        // 处理第一个元素
        val first = iterator.next()
        action(first.key, first.value)

        // 从第二个元素开始循环
        while (iterator.hasNext()) {
            val it = iterator.next()
            joinAction(it.key, it.value)
            action(it.key, it.value)
        }
    }
}

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

fun <T> flatSetOf(vararg item: Iterable<T>): Set<T> {
    val result = mutableSetOf<T>()
    for (it in item) {
        result.addAll(it)
    }
    return result
}
