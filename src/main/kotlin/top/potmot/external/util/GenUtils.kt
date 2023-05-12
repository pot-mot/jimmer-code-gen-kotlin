package top.potmot.external.util

import org.apache.commons.lang3.StringUtils
import top.potmot.external.config.GenConfig.Companion.author
import top.potmot.external.config.GenConfig.Companion.autoRemovePre
import top.potmot.external.config.GenConfig.Companion.packageName
import top.potmot.external.config.GenConfig.Companion.tablePrefix
import top.potmot.external.constant.GenConstants
import top.potmot.external.model.GenTable
import top.potmot.external.model.input.GenTableColumnInput
import top.potmot.external.model.input.GenTableInput
import java.util.*

/**
 * 代码生成器 工具类
 *
 * @author potmot
 */
object GenUtils {
    /**
     * 初始化表信息
     */
    fun initTable(genTable: GenTableInput) {
        genTable.className = convertClassName(genTable.tableName)
        genTable.packageName = packageName
        genTable.moduleName = getModuleName(packageName)
        genTable.functionName = genTable.tableComment.replace("表", "")
        genTable.author = author
    }

    /**
     * 初始化列属性字段
     */
    fun initColumnField(column: GenTableColumnInput, table: GenTable) {
        val dataType = getDbType(column.columnType)
        val columnName = column.columnName
        column.genTableId = table.id
        // 设置java字段名
        column.fieldName = convertFieldName(columnName)
        // 设置默认类型
        column.fieldType = GenConstants.TYPE_STRING
        column.queryType = GenConstants.QUERY_EQ
        if (arraysContains(GenConstants.COLUMN_TYPE_STR, dataType) || arraysContains(
                GenConstants.COLUMN_TYPE_TEXT,
                dataType
            )
        ) {
            // 字符串长度超过500设置为文本域
            val columnLength = getColumnLength(column.columnType)
            val htmlType = if (columnLength >= 500 || arraysContains(
                    GenConstants.COLUMN_TYPE_TEXT,
                    dataType
                )
            ) GenConstants.HTML_TEXTAREA else GenConstants.HTML_INPUT
            column.htmlType = htmlType
            column.queryType = GenConstants.QUERY_LIKE
        } else if (arraysContains(GenConstants.COLUMN_TYPE_TIME, dataType)) {
            column.fieldType = GenConstants.TYPE_DATE
            column.htmlType = GenConstants.HTML_DATETIME
            column.queryType = GenConstants.QUERY_BETWEEN
        } else if (arraysContains(GenConstants.COLUMN_TYPE_NUMBER, dataType)) {
            column.htmlType = GenConstants.HTML_INPUT

            // 数字类型处理
            val str = StringUtils.split(StringUtils.substringBetween(column.columnType, "(", ")"), ",")
            if (str != null && str.size == 2 && str[1].toInt() > 0) {
                column.fieldType = GenConstants.TYPE_BIG_DECIMAL
            } else if (str != null && str.size == 1 && str[0].toInt() <= 10) {
                column.fieldType = GenConstants.TYPE_INTEGER
            } else {
                column.fieldType = GenConstants.TYPE_LONG
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.inInsert = GenConstants.REQUIRE

        // 编辑字段
        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_EDIT, columnName) && column.pk != "1") {
            column.inEdit = GenConstants.REQUIRE
        }
        // 列表字段
        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_LIST, columnName) && column.pk != "1") {
            column.inList = GenConstants.REQUIRE
        }
        // 查询字段
        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_QUERY, columnName) && column.pk != "1") {
            column.inQuery = GenConstants.REQUIRE
        }
        if (column.pk != "1" && StringUtils.endsWithIgnoreCase(columnName, "id")) {
            column.idView = "1"
        } else if (StringUtils.endsWithIgnoreCase(columnName, "status")
            || StringUtils.endsWithIgnoreCase(columnName, "type")
            || StringUtils.endsWithIgnoreCase(columnName, "category")
        ) {
            column.htmlType = GenConstants.HTML_SELECT
        } else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.htmlType = GenConstants.HTML_IMAGE_UPLOAD
        } else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.htmlType = GenConstants.HTML_FILE_UPLOAD
        } else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.htmlType = GenConstants.HTML_EDITOR
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    private fun arraysContains(arr: Array<String>, targetValue: String): Boolean {
        return listOf(*arr).contains(targetValue)
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    private fun getModuleName(packageName: String): String {
        val lastIndex = packageName.lastIndexOf(".")
        val nameLength = packageName.length
        return StringUtils.substring(packageName, lastIndex + 1, nameLength)
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    fun convertClassName(tableName: String): String {
        val autoRemovePre = autoRemovePre
        val tablePrefix = tablePrefix
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
            val searchList = StringUtils.split(tablePrefix, ",")
            replaceFirst(tableName, searchList)
        }
        val result = StringBuilder()
        for (camel in tableName.split(GenConstants.SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (camel.isNotEmpty()) {
                result.append(camel[0].uppercaseChar())
                if (camel.length > 1) {
                    result.append(camel.substring(1).lowercase(Locale.getDefault()))
                }
            }
        }
        return result.toString()
    }

    /**
     * 转换列名为字段名
     * 例如：user_name->userName
     */
    fun convertFieldName(columnName: String): String {
        val sb = StringBuilder(columnName.length)
        var upperCase = false
        for (c in columnName.lowercase(Locale.getDefault()).split("".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()) {
            if (c == GenConstants.SEPARATOR) {
                upperCase = true
            } else if (upperCase) {
                sb.append(c.uppercase(Locale.getDefault()))
                upperCase = false
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    /**
     * 批量替换前缀
     *
     * @param replacement 替换值
     * @param searchList  替换列表
     */
    private fun replaceFirst(replacement: String, searchList: Array<String>): String {
        var text = replacement
        for (searchString in searchList) {
            if (replacement.startsWith(searchString)) {
                text = replacement.replaceFirst(searchString.toRegex(), "")
                break
            }
        }
        return text
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    private fun getDbType(columnType: String): String {
        return if (StringUtils.indexOf(columnType, "(") > 0) {
            StringUtils.substringBefore(columnType, "(")
        } else {
            columnType
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    private fun getColumnLength(columnType: String): Int {
        return if (StringUtils.indexOf(columnType, "(") > 0) {
            val length = StringUtils.substringBetween(columnType, "(", ")")
            Integer.valueOf(length)
        } else {
            0
        }
    }
}
