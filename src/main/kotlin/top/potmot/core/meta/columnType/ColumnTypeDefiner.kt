package top.potmot.core.meta.columnType

/**
 * 列类型定义者，基于 java.sql.Types 中的类型定义生产列定义
 * need 表示列类型可以添加该参数
 * required 表示列类型必须添加该参数
 */
interface ColumnTypeDefiner {
    fun needDisplaySize(typeCode: Int): Boolean

    fun needNumericPrecision(typeCode: Int): Boolean

    fun requiredDisplaySize(typeCode: Int): Boolean

    fun requiredNumericPrecision(typeCode: Int): Boolean

    fun defaultDisplaySize(typeCode: Int): Long?

    fun defaultNumericPrecision(typeCode: Int): Long?

    fun getTypeDefine(typeCode: Int, type: String, displaySize: Long? = null, numericPrecision: Long? = null): String =
        buildString {
            append(type)

            var tempDisplaySize: Long? = null

            if (needDisplaySize(typeCode)) {
                tempDisplaySize = displaySize ?: defaultDisplaySize(typeCode)

                if (requiredDisplaySize(typeCode) && tempDisplaySize == null) {
                    throw RuntimeException("$type displaySize is required")
                }
            }

            var tempNumericPrecision: Long? = null

            if (needNumericPrecision(typeCode)) {
                tempNumericPrecision = numericPrecision ?: defaultNumericPrecision(typeCode)

                if (requiredNumericPrecision(typeCode) && tempNumericPrecision == null) {
                    throw RuntimeException("$type numericPrecision is required")
                }
            }


            val appendDisplaySize = tempDisplaySize != null && tempDisplaySize != 0L

            val appendNumericPrecision = tempNumericPrecision != null && tempNumericPrecision != 0L

            if (appendDisplaySize || appendNumericPrecision) {
                append("(")
            }

            if (appendDisplaySize) {
                append(tempDisplaySize)
            }

            if (appendNumericPrecision) {
                if (appendDisplaySize) {
                    append(",")
                }
                append(tempNumericPrecision)
            }

            if (appendDisplaySize || appendNumericPrecision) {
                append(")")
            }
        }
}
