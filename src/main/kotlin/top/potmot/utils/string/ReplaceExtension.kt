package top.potmot.utils.string

import top.potmot.constant.SEPARATOR

fun String.replaceFirstOrAppend(
    oldValue: String, newValue: String
): String =
    if (indexOf(oldValue) == -1)
        this + SEPARATOR + newValue
    else
        replaceFirst(oldValue, newValue)