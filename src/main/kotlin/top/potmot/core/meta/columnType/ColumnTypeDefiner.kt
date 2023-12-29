package top.potmot.core.meta.columnType

/**
 * 列类型定义者，基于 java.sql.Types 中的类型定义生产列定义
 * need 表示列类型可以添加该参数
 * required 表示列类型必须添加该参数
 * getType 即基于数据源类型规范化 type，同时修正 typeMeta 中的其他数据
 */
interface ColumnTypeDefiner {
    fun needDisplaySize(typeCode: Int): Boolean

    fun needNumericPrecision(typeCode: Int): Boolean

    fun requiredDisplaySize(typeCode: Int): Boolean

    fun requiredNumericPrecision(typeCode: Int): Boolean

    fun defaultDisplaySize(typeCode: Int): Long?

    fun defaultNumericPrecision(typeCode: Int): Long?

    fun getTypeDefine(typeMeta: ColumnTypeMeta): String =
        getTypeName(typeMeta) + getTypeDisplaySizeAndNumericPrecision(typeMeta.typeCode, typeMeta.displaySize, typeMeta.numericPrecision)

    fun getTypeName(typeMeta: ColumnTypeMeta): String

    fun getTypeDisplaySizeAndNumericPrecision(typeCode: Int, displaySize: Long? = null, numericPrecision: Long? = null): String =
        buildString {
            var tempDisplaySize: Long? = null

            if (needDisplaySize(typeCode)) {
                tempDisplaySize = displaySize ?: defaultDisplaySize(typeCode)

                if (requiredDisplaySize(typeCode) && tempDisplaySize == null) {
                    throw RuntimeException("displaySize is required")
                }
            }

            var tempNumericPrecision: Long? = null

            if (needNumericPrecision(typeCode)) {
                tempNumericPrecision = numericPrecision ?: defaultNumericPrecision(typeCode)

                if (requiredNumericPrecision(typeCode) && tempNumericPrecision == null) {
                    throw RuntimeException("numericPrecision is required")
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
