package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.columnType.ColumnTypeDefiner
import top.potmot.entity.dto.share.ColumnTypeMeta
import java.sql.Types

// https://mysql.net.cn/doc/refman/8.0/en/data-types.html
object MysqlColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDataSize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.CHAR, Types.VARCHAR,
            Types.NCHAR, Types.NVARCHAR,
            Types.BINARY, Types.VARBINARY -> true

            Types.REAL,
            Types.FLOAT, Types.DOUBLE,
            Types.DECIMAL, Types.NUMERIC -> true

            else -> false
        }

    override fun needNumericPrecision(typeCode: Int): Boolean =
        when (typeCode) {
            Types.REAL,
            Types.FLOAT, Types.DOUBLE,
            Types.DECIMAL, Types.NUMERIC -> true

            else -> false
        }

    override fun requiredDataSize(typeCode: Int): Boolean = needDataSize(typeCode)

    override fun requiredNumericPrecision(typeCode: Int): Boolean = needNumericPrecision(typeCode)

    override fun defaultDataSize(typeCode: Int): Int? = null

    override fun defaultNumericPrecision(typeCode: Int): Int? = null
    override fun getTypeName(typeMeta: ColumnTypeMeta): String {
        if (typeMeta.overwriteByRaw) return typeMeta.rawType

        if (typeMeta.rawType.uppercase().startsWith("ENUM") ||
            typeMeta.rawType.uppercase().startsWith("SET")
        ) {
            return typeMeta.rawType
        }

        return when (typeMeta.typeCode) {
            Types.CHAR, Types.NCHAR -> "CHAR"

            Types.VARCHAR, Types.NVARCHAR -> "VARCHAR"

            Types.LONGVARCHAR, Types.LONGNVARCHAR -> "LONGTEXT"

            Types.BINARY -> "BINARY"

            Types.VARBINARY, Types.LONGVARBINARY -> "VARBINARY"

            Types.BIT -> "BIT"

            Types.TINYINT -> "TINYINT"

            Types.SMALLINT -> "SMALLINT"

            Types.INTEGER -> "INT"

            Types.BIGINT -> "BIGINT"

            Types.REAL, Types.FLOAT -> "FLOAT"

            Types.DOUBLE -> "DOUBLE"

            Types.NUMERIC, Types.DECIMAL -> "DECIMAL"

            Types.BOOLEAN -> "BOOLEAN"

            Types.DATE -> "DATE"

            Types.TIME, Types.TIME_WITH_TIMEZONE -> "TIME"

            Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> "TIMESTAMP"

            else -> typeMeta.rawType
        }
    }
}
