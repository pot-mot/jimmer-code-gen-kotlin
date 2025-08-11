package top.potmot.utils.string

fun String.startsWithAny(prefixes: Iterable<String>): String? {
    for (prefix in prefixes) {
        if (startsWith(prefix)) {
            return prefix
        }
    }
    return null
}

fun String.splitTrim(): List<String> =
    split(",").map { it.trim() }

/**
 * 移除两侧的非字母或数字符号
 */
fun String.trimToLetterOrDigit(): String {
    var startIndex = 0
    var endIndex = length - 1

    while (startIndex <= endIndex && !this[startIndex].isLetterOrDigit()) {
        startIndex++
    }

    while (endIndex >= startIndex && !this[endIndex].isLetterOrDigit()) {
        endIndex--
    }

    if (startIndex > endIndex) {
        return ""
    }

    return substring(startIndex, endIndex + 1)
}

fun String.isAllUpperCase(): Boolean =
    all {
        if (it.isLetter()) {
            it.isUpperCase()
        } else {
            true
        }
    }

fun String.isAllLowerCase(): Boolean =
    all {
        if (it.isLetter()) {
            it.isLowerCase()
        } else {
            true
        }
    }

fun String.trimBlankLine() =
    lines()
        .dropWhile { it.isBlank() }
        .dropLastWhile { it.isBlank() }
        .joinToString("\n")


fun String.clearBlankLine() =
    lines()
        .joinToString("\n") { it.ifBlank { "" } }