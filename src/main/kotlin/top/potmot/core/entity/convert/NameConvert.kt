package top.potmot.core.entity.convert

import top.potmot.context.getContextGenConfig
import top.potmot.utils.string.removePrefixes
import top.potmot.utils.string.removeSuffixes
import top.potmot.utils.string.splitTrim
import top.potmot.utils.string.trimToLetterOrDigit

/**
 * 转换表名为类名
 * 根据分割符将一个字符串转成首字母大写其余小写的形式
 * 将根据 GlobalConfig 判断移除表前缀或后缀
 * eq:
 *      HELLO_WORLD -> HelloWorld
 */
fun tableNameToClassName(name: String): String =
    buildString {
        // 将 newName 按照 SEPARATOR 进行分割成一个字符串数组并且去掉数组中的空字符串
        name
            .trimToLetterOrDigit()
            .clearTableName()
            .split("_")
            .dropLastWhile { it.isEmpty() }
            .forEach {
                if (it.isNotEmpty()) {
                    // 将字符串的第一个字符转换为大写字符
                    append(it[0].uppercaseChar())

                    // 将字符串的第二个字符到最后一个字符转换为小写字符
                    if (it.length > 1) {
                        append(it.substring(1).lowercase())
                    }
                }
            }
    }

/**
 * 转换表名为属性名
 * 在转换表名为类名基础上将首字母转小写
 * eq:
 *      HELLO_WORLD -> helloWorld
 */
fun tableNameToPropertyName(name: String): String =
    tableNameToClassName(name).replaceFirstChar { it.lowercase() }

fun columnNameToPropertyName(name: String): String =
    tableNameToClassName(name).replaceFirstChar { it.lowercase() }

/**
 * 根据配置清理表名的前缀和后缀
 */
fun String.clearTableName(): String =
    this.removePrefixes(getContextGenConfig().tableNamePrefixes.splitTrim())
        .removeSuffixes(getContextGenConfig().tableNameSuffixes.splitTrim())

fun String.clearTableComment(): String =
    this.removePrefixes(getContextGenConfig().tableCommentPrefixes.splitTrim())
        .removeSuffixes(getContextGenConfig().tableCommentSuffixes.splitTrim())

/**
 * 根据配置清理列名的前缀和后缀
 */
fun String.clearColumnName(): String =
    this.removePrefixes(getContextGenConfig().columnNamePrefixes.splitTrim())
        .removeSuffixes(getContextGenConfig().columnNameSuffixes.splitTrim())

fun String.clearColumnComment(): String =
    this.removePrefixes(getContextGenConfig().columnCommentPrefixes.splitTrim())
        .removeSuffixes(getContextGenConfig().columnCommentSuffixes.splitTrim())
