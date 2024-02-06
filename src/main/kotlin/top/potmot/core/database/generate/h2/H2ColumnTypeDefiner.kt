package top.potmot.core.database.generate.h2

import top.potmot.core.database.generate.columnTypeDefiner.ColumnTypeDefiner
import top.potmot.model.dto.ColumnTypeMeta
import java.sql.Types

object H2ColumnTypeDefiner : ColumnTypeDefiner {
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

    override fun defaultDataSize(typeCode: Int): Long? = null

    override fun defaultNumericPrecision(typeCode: Int): Long? = null
    override fun getTypeName(typeMeta: ColumnTypeMeta): String {
        if (typeMeta.overwriteByRaw) return typeMeta.rawType

        if (typeMeta.rawType.uppercase().startsWith("ENUM") ||
            typeMeta.rawType.uppercase().startsWith("SET")
        ) {
            typeMeta.dataSize = 0
            typeMeta.numericPrecision = 0

            return typeMeta.rawType
        }

        when (typeMeta.typeCode) {
            Types.CHAR, Types.NCHAR -> {
                typeMeta.rawType = "CHAR"
            }

            Types.VARCHAR, Types.NVARCHAR -> {
                typeMeta.rawType = "VARCHAR"
            }

            Types.LONGVARCHAR, Types.LONGNVARCHAR -> {
                typeMeta.rawType = "LONGTEXT"
            }

            Types.BINARY -> {
                typeMeta.rawType = "BINARY"
            }

            Types.VARBINARY, Types.LONGVARBINARY -> {
                typeMeta.rawType = "VARBINARY"
            }

            Types.BIT -> {
                typeMeta.rawType = "BIT"
            }

            Types.TINYINT -> {
                typeMeta.rawType = "TINYINT"
            }

            Types.SMALLINT -> {
                typeMeta.rawType = "SMALLINT"
            }

            Types.INTEGER -> {
                typeMeta.rawType = "INT"
            }

            Types.BIGINT -> {
                typeMeta.rawType = "BIGINT"
            }

            Types.REAL, Types.FLOAT -> {
                typeMeta.rawType = "FLOAT"
            }

            Types.DOUBLE -> {
                typeMeta.rawType = "DOUBLE"
            }

            Types.NUMERIC, Types.DECIMAL -> {
                typeMeta.rawType = "DECIMAL"
            }

            Types.BOOLEAN -> {
                typeMeta.rawType = "BOOLEAN"
            }

            Types.DATE -> {
                typeMeta.rawType = "DATE"
            }

            Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP_WITH_TIMEZONE -> {
                typeMeta.rawType = "TIMESTAMP"
            }

            Types.TIME, Types.TIMESTAMP -> {
                typeMeta.rawType = "DATETIME"
            }
        }

        return typeMeta.rawType
    }
}
