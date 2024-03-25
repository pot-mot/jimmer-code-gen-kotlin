package top.potmot.core.entity.convert

import top.potmot.context.getContextOrGlobal
import top.potmot.utils.string.removePrefixes
import top.potmot.utils.string.removeSuffixes
import top.potmot.utils.string.splitTrim
import top.potmot.utils.string.trimToLetterOrDigit

const val SEPARATOR = "_"

/**
 * 转换下划线命名为大驼峰名名
 * 根据分割符将一个字符串转成首字母大写其余小写的形式
 * 将根据 GlobalGenConfig 判断移除表前缀或后缀
 * eq:
 *      HELLO_WORLD -> HelloWorld
 */
fun snakeToCamel(name: String): String =
    buildString {
        // 将 newName 按照 SEPARATOR 进行分割成一个字符串数组并且去掉数组中的空字符串
        name
            .trimToLetterOrDigit()
            .clearTableName()
            .split(SEPARATOR)
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

fun tableNameToClassName(name: String): String =
    snakeToCamel(name)

fun columnNameToPropertyName(name: String): String =
    snakeToCamel(name).replaceFirstChar { it.lowercase() }

private fun String.removeLastId(): String =
    if (lowercase().endsWith("id"))
        slice(0 until length - 2)
    else
        this

fun propertyNameToAssociationPropertyName(propertyName: String, tableName: String): String {
    val associationPropertyName = propertyName.removeLastId()
    return associationPropertyName.ifBlank { tableNameToClassName(tableName).replaceFirstChar { it.lowercase() } }
}

/**
 * 根据配置清理表名的前缀和后缀
 */
fun String.clearTableName(): String =
    this.removePrefixes(getContextOrGlobal().tableNamePrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().tableNameSuffixes.splitTrim())

fun String.clearTableComment(): String =
    this.removePrefixes(getContextOrGlobal().tableCommentPrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().tableCommentSuffixes.splitTrim())

/**
 * 根据配置清理列名的前缀和后缀
 */
fun String.clearColumnName(): String =
    this.removePrefixes(getContextOrGlobal().columnNamePrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().columnNameSuffixes.splitTrim())

fun String.clearColumnComment(): String =
    this.removePrefixes(getContextOrGlobal().columnCommentPrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().columnCommentSuffixes.splitTrim())
