package top.potmot.util.convert

import org.apache.commons.lang3.StringUtils
import top.potmot.config.GenConfig

/**
 * 转换表名为类名，即根据一个分割符将一个字符串转成首字母大写其余小写的形式
 * 例如：HELLO_WORLD -> HelloWorld
 */
fun tableNameToClassName(name: String): String {
    val newName = name.removePrefixes().removePrefixes()
    val result = StringBuilder()

    // 将 newName 按照 SEPARATOR 进行分割成一个字符串数组并且去掉数组中的空字符串
    for (camel in newName.split(GenConfig.separator)
        .dropLastWhile { it.isEmpty() }
        .toTypedArray()) {
        if (camel.isNotEmpty()) {
            // 将字符串的第一个字符转换为大写字符
            result.append(camel[0].uppercaseChar())

            // 将字符串的第二个字符到最后一个字符转换为小写字符
            if (camel.length > 1) {
                result.append(camel.substring(1).lowercase())
            }
        }
    }
    return result.toString()
}

/**
 * 转换列名为属性名，即根据一个分割符将一个字符串转成自第二部分开始首字母大写其余小写的形式
 * 例如：user_name -> userName
 */
fun columnNameToPropertyName(name: String): String {
    val sb = StringBuilder(name.length)
    // 标记下一个字符是否需要转换为大写
    var upperCase = false

    // 遍历原始属性名中的每个字符
    for (c in name.lowercase().split("").dropLastWhile { it.isEmpty() }) {
        // 如果当前字符是分隔符，则标记下一个字符需要转换为大写
        if (c == GenConfig.separator) {
            upperCase = true
        }
        // 如果标记为需要转换为大写，则将当前字符转换为大写字符，并且添加到结果字符串中
        else if (upperCase) {
            sb.append(c.uppercase())
            upperCase = false
        } else {
            sb.append(c)
        }
    }
    return sb.toString()
}

/**
 * 转换包名为模块名
 *
 * @param packageName 包名
 * @return 模块名
 */
fun packageNameToModuleName(packageName: String): String {
    val lastIndex = packageName.lastIndexOf(".")
    val nameLength = packageName.length
    return StringUtils.substring(packageName, lastIndex + 1, nameLength)
}

/**
 * 转换表注释为功能名：中文
 * 例如：测试表 -> 测试
 */
fun commentToFunctionName(comment: String): String {
    return if (comment.isNotEmpty() && comment.last() == '表') {
        comment.dropLast(1) // 如果最后一个字符是 "表"，则将其删除
    } else {
        comment // 如果最后一个字符不是 "表"，则直接返回原字符串
    }
}

/**
 * 从给定的字符串中移除前缀字符串
 *
 * @param target 要处理的字符串
 * @param prefixes 要移除的前缀列表
 * @param separator 分隔符
 * @return 移除前缀后的字符串
 */
fun String.removePrefixes(
    prefixes: List<String> = GenConfig.tablePrefix,
    separator: String = GenConfig.separator
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
 *
 * @param target 要处理的字符串
 * @param suffixes 要移除的后缀列表
 * @param separator 分隔符
 * @return 移除后缀后的字符串
 */
fun String.removeSuffixes(
    suffixes: List<String> = GenConfig.tableSuffix,
    separator: String = GenConfig.separator
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
