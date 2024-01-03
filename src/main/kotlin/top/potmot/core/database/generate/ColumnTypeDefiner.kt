package top.potmot.core.database.generate

import top.potmot.core.database.meta.ColumnTypeMeta
import top.potmot.error.ColumnTypeException

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

            val isNeedDisplaySize = needDisplaySize(typeCode)
            val isRequiredDisplaySize = requiredDisplaySize(typeCode)

            if (isNeedDisplaySize || isRequiredDisplaySize) {
                tempDisplaySize = displaySize ?: defaultDisplaySize(typeCode)

                if (isRequiredDisplaySize && tempDisplaySize == null) {
                    throw ColumnTypeException.missRequiredParam("displaySize is required for type [${typeCode}]")
                }
            }

            var tempNumericPrecision: Long? = null

            val isNeedNumericPrecision = needNumericPrecision(typeCode)
            val isRequiredNumericPrecision = requiredNumericPrecision(typeCode)

            if (isNeedNumericPrecision || isRequiredNumericPrecision) {
                tempNumericPrecision = numericPrecision ?: defaultNumericPrecision(typeCode)

                if (isRequiredNumericPrecision && tempNumericPrecision == null) {
                    throw ColumnTypeException.missRequiredParam("numericPrecision is required for type [${typeCode}]")
                }
            }


            val appendDisplaySize = isRequiredDisplaySize || (tempDisplaySize != null)

            val appendNumericPrecision = isRequiredNumericPrecision || (tempNumericPrecision != null)

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
