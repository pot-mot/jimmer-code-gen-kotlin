package top.potmot.utils.list

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