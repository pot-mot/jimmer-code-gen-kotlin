//package top.potmot.util
//
//import org.apache.commons.lang3.StringUtils
//import top.potmot.config.GenConfig
//import top.potmot.constant.GenConstants
//import top.potmot.model.GenTable
//import top.potmot.model.input.GenTableColumnInput
//import top.potmot.model.input.GenTableInput
//import java.util.*
//
///**
// * 代码生成器 工具类
// *
// * @author potmot */
//object GenUtils {
//    /**
//     * 初始化表信息
//     */
//    fun initTable(genTable: GenTableInput) {
//        genTable.className = convertClassName(genTable.tableName)
//        genTable.packageName = GenConfig.packageName
//        genTable.moduleName = convertModuleName(GenConfig.packageName)
//        genTable.functionName = convertFunctionName(genTable.tableComment)
//        genTable.author = GenConfig.author
//    }
//
//    /**
//     * 初始化列属性字段
//     */
//    fun initColumnField(column: GenTableColumnInput, table: GenTable) {
//        val dataType = getDbType(column.columnType)
//        val columnName = column.columnName
//        column.genTableId = table.id
//        // 设置java字段名
//        column.fieldName = convertFieldName(columnName)
//        // 设置默认类型
//        column.fieldType = GenConstants.TYPE_STRING
//        column.queryType = GenConstants.QUERY_EQ
//        if (arraysContains(GenConstants.COLUMN_TYPE_STR, dataType) || arraysContains(
//                GenConstants.COLUMN_TYPE_TEXT,
//                dataType
//            )
//        ) {
//            // 字符串长度超过500设置为文本域
//            val columnLength = getColumnLength(column.columnType)
//            val htmlType = if (columnLength >= 500 || arraysContains(
//                    GenConstants.COLUMN_TYPE_TEXT,
//                    dataType
//                )
//            ) GenConstants.HTML_TEXTAREA else GenConstants.HTML_INPUT
//            column.htmlType = htmlType
//            column.queryType = GenConstants.QUERY_LIKE
//        } else if (arraysContains(GenConstants.COLUMN_TYPE_TIME, dataType)) {
//            column.fieldType = GenConstants.TYPE_DATE
//            column.htmlType = GenConstants.HTML_DATETIME
//            column.queryType = GenConstants.QUERY_BETWEEN
//        } else if (arraysContains(GenConstants.COLUMN_TYPE_NUMBER, dataType)) {
//            column.htmlType = GenConstants.HTML_INPUT
//
//            // 数字类型处理
//            val str = StringUtils.split(StringUtils.substringBetween(column.columnType, "(", ")"), ",")
//            if (str != null && str.size == 2 && str[1].toInt() > 0) {
//                column.fieldType = GenConstants.TYPE_BIG_DECIMAL
//            } else if (str != null && str.size == 1 && str[0].toInt() <= 10) {
//                column.fieldType = GenConstants.TYPE_INTEGER
//            } else {
//                column.fieldType = GenConstants.TYPE_LONG
//            }
//        }
//
//        // 插入字段（默认所有字段都需要插入）
//        column.inInsert = GenConstants.REQUIRE
//
//        // 编辑字段
//        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_EDIT, columnName) && column.pk != "1") {
//            column.inEdit = GenConstants.REQUIRE
//        }
//        // 列表字段
//        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_LIST, columnName) && column.pk != "1") {
//            column.inList = GenConstants.REQUIRE
//        }
//        // 查询字段
//        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_QUERY, columnName) && column.pk != "1") {
//            column.inQuery = GenConstants.REQUIRE
//        }
//        // 主键
//        if (column.pk != "1" && StringUtils.endsWithIgnoreCase(columnName, "id")) {
//            column.idView = "1"
//        } else if (StringUtils.endsWithIgnoreCase(columnName, "status")
//            || StringUtils.endsWithIgnoreCase(columnName, "type")
//            || StringUtils.endsWithIgnoreCase(columnName, "category")
//        ) {
//            column.htmlType = GenConstants.HTML_SELECT
//        } else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
//            column.htmlType = GenConstants.HTML_IMAGE_UPLOAD
//        } else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
//            column.htmlType = GenConstants.HTML_FILE_UPLOAD
//        } else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
//            column.htmlType = GenConstants.HTML_EDITOR
//        }
//    }
//
//    /**
//     * 校验数组是否包含指定值
//     *
//     * @param arr         数组
//     * @param targetValue 值
//     * @return 是否包含
//     */
//    private fun arraysContains(arr: Array<String>, targetValue: String): Boolean {
//        return listOf(*arr).contains(targetValue)
//    }
//
//    /**
//     * 获取模块名
//     *
//     * @param packageName 包名
//     * @return 模块名
//     */
//    private fun convertModuleName(packageName: String): String {
//        val lastIndex = packageName.lastIndexOf(".")
//        val nameLength = packageName.length
//        return StringUtils.substring(packageName, lastIndex + 1, nameLength)
//    }
//
//    /**
//     * 转换表名为类名
//     * 例如：HELLO_WORLD -> HelloWorld
//     */
//    fun convertClassName(tableName: String): String {
//        val autoRemovePre = GenConfig.autoRemovePre
//        val tablePrefix = GenConfig.tablePrefix
//        var newTableName = tableName
//        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
//            newTableName = replacePrefixes(tableName, StringUtils.split(tablePrefix, ","))
//        }
//        val result = StringBuilder()
//
//        // 将 newTableName 按照 SEPARATOR 进行分割成一个字符串数组并且去掉数组中的空字符串
//        for (camel in newTableName.split(GenConstants.SEPARATOR.toRegex())
//            .dropLastWhile { it.isEmpty() }
//            .toTypedArray()) {
//            if (camel.isNotEmpty()) {
//                // 将字符串的第一个字符转换为大写字符
//                result.append(camel[0].uppercaseChar())
//
//                // 将字符串的第二个字符到最后一个字符转换为小写字符
//                if (camel.length > 1) {
//                    result.append(camel.substring(1).lowercase(Locale.getDefault()))
//                }
//            }
//        }
//        return result.toString()
//    }
//
//    /**
//     * 转换列名为字段名
//     * 例如：user_name -> userName
//     */
//    fun convertFieldName(columnName: String): String {
//        val sb = StringBuilder(columnName.length)
//        // 标记下一个字符是否需要转换为大写
//        var upperCase = false
//
//        // 遍历原始字段名中的每个字符
//        for (c in columnName.lowercase(Locale.getDefault()).split("".toRegex()).dropLastWhile { it.isEmpty() }
//            .toTypedArray()) {
//            // 如果当前字符是分隔符，则标记下一个字符需要转换为大写
//            if (c == GenConstants.SEPARATOR) {
//                upperCase = true
//            }
//            // 如果标记为需要转换为大写，则将当前字符转换为大写字符，并且添加到结果字符串中
//            else if (upperCase) {
//                sb.append(c.uppercase(Locale.getDefault()))
//                upperCase = false
//            } else {
//                sb.append(c)
//            }
//        }
//        return sb.toString()
//    }
//
//    /**
//     * 转换表注释为功能名：中文
//     * 例如：测试表 -> 测试
//     */
//    private fun convertFunctionName(tableComment: String): String {
//        return if (tableComment.isNotEmpty() && tableComment.last() == '表') {
//            tableComment.dropLast(1) // 如果最后一个字符是 "表"，则将其删除
//        } else {
//            tableComment // 如果最后一个字符不是 "表"，则直接返回原字符串
//        }
//    }
//
//    /**
//     * 将字符串中的前缀替换为空字符串
//     *
//     * @param replacement 待替换的字符串
//     * @param prefixes 前缀数组
//     * @return 返回替换后的字符串
//     */
//    private fun replacePrefixes(replacement: String, prefixes: Array<String>): String {
//        // 遍历前缀数组，找到第一个匹配的前缀
//        prefixes.forEach { prefix ->
//            if (replacement.startsWith(prefix)) {
//                // 如果找到了匹配的前缀，就将其从字符串中删除
//                return replacement.removePrefix(prefix)
//            }
//        }
//        // 如果没有找到匹配的前缀，则返回原始字符串
//        return replacement
//    }
//
//    /**
//     * 获取列类型字符串中的类型名称
//     *
//     * @param columnType 列类型字符串，格式为 "类型(长度)"
//     * @return 返回类型名称
//     */
//    private fun getDbType(columnType: String): String {
//        // 使用 substringBefore 函数获取 "(" 前面的子字符串，即为类型名称
//        return columnType.substringBefore("(", columnType)
//    }
//
//    /**
//     * 获取列类型字符串中的长度
//     *
//     * @param columnType 列类型字符串，格式为 "类型(长度)"
//     * @return 返回长度，如果没有长度则返回 0
//     */
//    private fun getColumnLength(columnType: String): Int {
//        // 使用 substringAfter 和 substringBefore 函数获取长度字符串，并尝试将其转换为整数
//        val length = columnType.substringAfter("(", "")
//            .substringBefore(")", "")
//            .toIntOrNull()
//        // 如果转换成功，则返回长度；否则返回 0
//        return length ?: 0
//    }
//}
