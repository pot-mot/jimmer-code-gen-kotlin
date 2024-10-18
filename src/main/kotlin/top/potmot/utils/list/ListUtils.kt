package top.potmot.utils.list

fun <T> flatListOf(vararg elements: List<T>): List<T> =
    elements.flatMap { it }