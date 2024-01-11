package top.potmot.utils.string

import top.potmot.config.GlobalGenConfig

fun String.startsWithAny(prefixes: Collection<String>): String? {
    for (prefix in prefixes) {
        if (startsWith(prefix)) {
            return prefix
        }
    }
    return null
}

fun String.endsWithAny(suffixes: Collection<String>): String? {
    for (suffix in suffixes) {
        if (endsWith(suffix)) {
            return suffix
        }
    }
    return null
}

/**
 * 从给定的字符串中移除前缀字符串
 * @param prefixes 要移除的前缀列表
 * @param separator 分隔符
 * @return 移除前缀后的字符串
 */
fun String.removePrefixes(
    prefixes: List<String>,
    separator: String = "_"
): String {
    var result = this

    for (prefix in prefixes) {
        if (result.startsWith(prefix)) {
            result = result.removePrefix(prefix)
            if (separator.isNotEmpty() && result.startsWith(separator)) {
                result = result.removePrefix(separator)
            }
            break
        }
    }

    return result
}

/**
 * 从给定的字符串中移除后缀字符串
 * @param suffixes 要移除的后缀列表
 * @param separator 分隔符
 * @return 移除后缀后的字符串
 */
fun String.removeSuffixes(
    suffixes: List<String>,
    separator: String = "_"
): String {
    var result = this

    for (suffix in suffixes) {
        if (result.endsWith(suffix)) {
            result = result.removeSuffix(suffix)
            if (separator.isNotEmpty() && result.endsWith(separator)) {
                result = result.removeSuffix(separator)
            }
            break
        }
    }
    return result
}

/**
 * 将名词转换为复数形式。
 * @return 转换后的复数形式
 */
fun String.toPlural(): String =
    EnglishWordUtil.pluralize(this)

/**
 * 将名词转换为单数形式。
 * @return 转换后的单数形式
 */
fun String.toSingular(): String =
    EnglishWordUtil.singularize(this)

/**
 * 根据全局配置的大小写进行切换
 */
fun String.changeCase(): String =
    this.let { if (GlobalGenConfig.lowerCaseName) lowercase() else uppercase() }
