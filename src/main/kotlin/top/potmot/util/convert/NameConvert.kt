package top.potmot.util.convert

import org.apache.commons.lang3.StringUtils
import top.potmot.config.GenConfig

/**
 * 转换表名为类名，即根据一个分割符将一个字符串转成首字母大写其余小写的形式
 * 例如：HELLO_WORLD -> HelloWorld
 */
fun nameToClassName(name: String): String {
    val autoRemovePre = GenConfig.autoRemovePre
    val tablePrefix = GenConfig.tablePrefix
    var newname = name
    if (autoRemovePre) {
        newname = removePrefixes(name, tablePrefix.split(","))
    }
    val result = StringBuilder()

    // 将 newname 按照 SEPARATOR 进行分割成一个字符串数组并且去掉数组中的空字符串
    for (camel in newname.split(GenConfig.separator.toRegex())
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
 * 转换列名为字段名，即根据一个分割符将一个字符串转成自第二部分开始首字母大写其余小写的形式
 * 例如：user_name -> userName
 */
fun nameToname(name: String): String {
    val sb = StringBuilder(name.length)
    // 标记下一个字符是否需要转换为大写
    var upperCase = false

    // 遍历原始字段名中的每个字符
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
 * 将字符串中的前缀替换为空字符串
 *
 * @param replacement 待替换的字符串
 * @param prefixes 前缀数组
 * @return 返回替换后的字符串
 */
fun removePrefixes(replacement: String, prefixes: List<String>): String {
    // 遍历前缀数组，找到第一个匹配的前缀
    prefixes.forEach { prefix ->
        if (replacement.startsWith(prefix)) {
            // 如果找到了匹配的前缀，就将其从字符串中删除
            return replacement.removePrefix(prefix)
        }
    }
    // 如果没有找到匹配的前缀，则返回原始字符串
    return replacement
}
