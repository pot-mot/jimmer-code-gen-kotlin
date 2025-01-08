package top.potmot.utils.string

import top.potmot.constant.SEPARATOR

fun String.replaceFirstOrAppend(
    oldValue: String,
    newValue: String,
    ignoreCase: Boolean
): String =
    if (indexOf(oldValue, ignoreCase = ignoreCase) == -1)
        this + SEPARATOR + newValue
    else
        replaceFirst(oldValue, newValue, ignoreCase = ignoreCase)

/**
 * 从给定的字符串中移除前缀字符串，每个前缀仅移除一次
 * @param prefixes 要移除的前缀列表
 * @param separator 分隔符
 * @param ignoreCase 是否忽略大小写
 * @return 移除前缀后的字符串
 */
fun String.removePrefixes(
    prefixes: List<String>,
    separator: String = SEPARATOR,
    ignoreCase: Boolean = true,
): String {
    var result = this

    for (prefix in prefixes) {
        if (result.startsWith(prefix, ignoreCase)) {
            result = result.substring(prefix.length)
            if (separator.isNotEmpty() && result.startsWith(separator, ignoreCase)) {
                result = result.substring(separator.length)
            }
            break
        }
    }

    return result
}

/**
 * 从给定的字符串中移除后缀字符串，每个后缀仅移除一次
 * @param suffixes 要移除的后缀列表
 * @param separator 分隔符
 * @param ignoreCase 是否忽略大小写
 * @return 移除后缀后的字符串
 */
fun String.removeSuffixes(
    suffixes: List<String>,
    separator: String = SEPARATOR,
    ignoreCase: Boolean = true,
): String {
    var result = this

    for (suffix in suffixes) {
        if (result.endsWith(suffix, ignoreCase)) {
            result = result.substring(0, result.length - suffix.length)
            if (separator.isNotEmpty() && result.endsWith(separator, ignoreCase)) {
                result = result.substring(0, result.length - separator.length)
            }
            break
        }
    }
    return result
}