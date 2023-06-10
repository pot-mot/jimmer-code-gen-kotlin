package top.potmot.constant

/**
 * 代码生成通用常量
 *
 * @author potmot
 */
object GenConstants {
    /**
     * 数据库命名分割符
     */
    const val SEPARATOR = "_"

    /**
     * 数据库字符串类型
     */
    val COLUMN_TYPE_STR = arrayOf("char", "varchar", "nvarchar", "varchar2")

    /**
     * 数据库文本类型
     */
    val COLUMN_TYPE_TEXT = arrayOf("tinytext", "text", "mediumtext", "longtext")

    /**
     * 数据库时间类型
     */
    val COLUMN_TYPE_TIME = arrayOf("datetime", "time", "date", "timestamp")

    /**
     * 数据库数字类型
     */
    val COLUMN_TYPE_NUMBER = arrayOf(
        "tinyint", "smallint", "mediumint", "int", "number", "integer",
        "bit", "bigint", "float", "double", "decimal"
    )

    /**
     * 页面不需要编辑字段
     */
    val COLUMN_NAME_NOT_EDIT = arrayOf("id", "create_by", "create_time", "del_flag")

    /**
     * 页面不需要显示的列表字段
     */
    val COLUMN_NAME_NOT_LIST = arrayOf(
        "id", "create_by", "create_time", "del_flag", "update_by",
        "update_time"
    )

    /**
     * 页面不需要查询字段
     */
    val COLUMN_NAME_NOT_QUERY = arrayOf(
        "id", "create_by", "create_time", "del_flag", "update_by",
        "update_time", "remark"
    )

    /**
     * Entity基类字段
     */
    val BASE_ENTITY = arrayOf("createBy", "createTime", "updateBy", "updateTime", "remark")

    /**
     * 文本框
     */
    const val HTML_INPUT = "input"

    /**
     * 文本域
     */
    const val HTML_TEXTAREA = "textarea"

    /**
     * 下拉框
     */
    const val HTML_SELECT = "select"

    /**
     * 单选框
     */
    const val HTML_RADIO = "radio"

    /**
     * 复选框
     */
    const val HTML_CHECKBOX = "checkbox"

    /**
     * 日期控件
     */
    const val HTML_DATETIME = "datetime"

    /**
     * 图片上传控件
     */
    const val HTML_IMAGE_UPLOAD = "imageUpload"

    /**
     * 文件上传控件
     */
    const val HTML_FILE_UPLOAD = "fileUpload"

    /**
     * 富文本控件
     */
    const val HTML_EDITOR = "editor"

    /**
     * 字符串类型
     */
    const val TYPE_STRING = "String"

    /**
     * 整型
     */
    const val TYPE_INTEGER = "Integer"

    /**
     * 长整型
     */
    const val TYPE_LONG = "Long"

    /**
     * 浮点型
     */
    const val TYPE_DOUBLE = "Double"

    /**
     * 高精度计算类型
     */
    const val TYPE_BIG_DECIMAL = "BigDecimal"

    /**
     * 时间类型
     */
    const val TYPE_DATE = "Date"

    /**
     * 模糊查询
     */
    const val QUERY_LIKE = "LIKE"

    /**
     * 相等查询
     */
    const val QUERY_EQ = "EQ"

    /**
     * 范围查询
     */
    const val QUERY_BETWEEN = "BETWEEN"

    /**
     * 需要
     */
    const val REQUIRE = "1"
}
