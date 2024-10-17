package top.potmot.utils.string

import top.potmot.constant.SEPARATOR
import top.potmot.context.getContextOrGlobal

/**
 * 转换下划线命名为大驼峰名名
 * 根据分割符将一个字符串转成首字母大写其余小写的形式
 * 将根据 GlobalGenConfig 判断移除表前缀或后缀
 * eq:
 *      HELLO_WORLD -> HelloWorld
 */
fun snakeToUpperCamel(name: String): String =
    buildString {
        // 将 newName 按照 SEPARATOR 进行分割成一个字符串数组并且去掉数组中的空字符串
        name
            .split(SEPARATOR)
            .forEach {
                if (it.isNotEmpty()) {
                    if (it.isAllUpperCase()) {
                        append(it[0])
                        append(it.substring(1).lowercase())
                    } else if (it.isAllLowerCase()) {
                        // 将字符串的第一个字符转换为大写字符
                        append(it[0].uppercaseChar())
                        append(it.substring(1))
                    } else {
                        append(it[0].uppercaseChar())
                        append(it.substring(1))
                    }
                }
            }
    }

fun snakeToLowerCamel(name: String): String =
    snakeToUpperCamel(name).replaceFirstChar { it.lowercase() }

/**
 * 将大驼峰或小驼峰命名转换为下划线命名
 * eq:
 *      HelloWorld -> HELLO_WORLD
 *      helloWorld -> HELLO_WORLD
 */
fun camelToUpperSnake(name: String): String =
    buildString {
        name
            .trimToLetterOrDigit()
            .forEach {
                if (it.isUpperCase()) {
                    if (isNotEmpty()) append('_')
                    append(it)
                } else {
                    append(it.uppercaseChar())
                }
            }
    }


fun camelToLowerSnake(name: String): String =
    camelToUpperSnake(name).lowercase()

/**
 * 根据配置清理表名的前缀和后缀
 */
fun String.clearTableName(): String =
    this.removePrefixes(getContextOrGlobal().tableNamePrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().tableNameSuffixes.splitTrim())

fun String.clearTableComment(): String =
    this.removePrefixes(getContextOrGlobal().tableCommentPrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().tableCommentSuffixes.splitTrim())

fun tableToEntityName(tableName: String): String =
    snakeToUpperCamel(tableName.trimToLetterOrDigit().clearTableName())

fun tableToPropertyName(tableName: String): String =
    snakeToLowerCamel(tableName.trimToLetterOrDigit().clearTableName())

fun entityToTableName(entityName: String): String =
    camelToUpperSnake(entityName)

/**
 * 根据配置清理列名的前缀和后缀
 */
fun String.clearColumnName(): String =
    this.removePrefixes(getContextOrGlobal().columnNamePrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().columnNameSuffixes.splitTrim())

fun String.clearColumnComment(): String =
    this.removePrefixes(getContextOrGlobal().columnCommentPrefixes.splitTrim())
        .removeSuffixes(getContextOrGlobal().columnCommentSuffixes.splitTrim())

