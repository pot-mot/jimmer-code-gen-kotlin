package top.potmot.core.database.generate.columnType

import top.potmot.error.ColumnTypeException
import top.potmot.entity.dto.ColumnTypeMeta

/**
 * 列类型定义者，基于 java.sql.Types 中的类型定义生产列定义
 * need 表示列类型可以添加该参数
 * required 表示列类型必须添加该参数
 * getType 即基于数据源类型规范化 type，同时修正 typeMeta 中的其他数据
 */
interface ColumnTypeDefiner {
    fun needDataSize(typeCode: Int): Boolean

    fun needNumericPrecision(typeCode: Int): Boolean

    fun requiredDataSize(typeCode: Int): Boolean

    fun requiredNumericPrecision(typeCode: Int): Boolean

    fun defaultDataSize(typeCode: Int): Long?

    fun defaultNumericPrecision(typeCode: Int): Long?

    @Throws(ColumnTypeException::class)
    fun getTypeDefine(typeMeta: ColumnTypeMeta): String =
        getTypeName(typeMeta) + getTypeDataSizeAndNumericPrecision(
            typeMeta.typeCode,
            typeMeta.dataSize,
            typeMeta.numericPrecision
        )

    fun getTypeName(typeMeta: ColumnTypeMeta): String

    @Throws(ColumnTypeException::class)
    fun getTypeDataSizeAndNumericPrecision(
        typeCode: Int,
        dataSize: Long? = null,
        numericPrecision: Long? = null
    ): String =
        buildString {
            var tempDataSize: Long? = null

            val isNeedDataSize = needDataSize(typeCode)
            val isRequiredDataSize = requiredDataSize(typeCode)

            if (isNeedDataSize || isRequiredDataSize) {
                tempDataSize = dataSize ?: defaultDataSize(typeCode)

                if (isRequiredDataSize && tempDataSize == null) {
                    throw ColumnTypeException.missRequiredParam("dataSize is required for type [${typeCode}]")
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


            val appendDataSize = isRequiredDataSize || (tempDataSize != null)

            val appendNumericPrecision = isRequiredNumericPrecision || (tempNumericPrecision != null)

            if (appendDataSize || appendNumericPrecision) {
                append("(")
            }

            if (appendDataSize) {
                append(tempDataSize)
            }

            if (appendNumericPrecision) {
                if (appendDataSize) {
                    append(",")
                }
                append(tempNumericPrecision)
            }

            if (appendDataSize || appendNumericPrecision) {
                append(")")
            }
        }
}
