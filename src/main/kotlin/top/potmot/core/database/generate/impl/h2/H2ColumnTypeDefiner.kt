package top.potmot.core.database.generate.impl.h2

import top.potmot.core.database.generate.columnType.ColumnTypeDefiner
import top.potmot.model.dto.ColumnTypeMeta
import java.sql.Types

// https://h2database.com/html/datatypes.html
object H2ColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDataSize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.CHAR, Types.VARCHAR,
            Types.NCHAR, Types.NVARCHAR,
            Types.BINARY, Types.VARBINARY,
            Types.LONGVARCHAR, Types.LONGNVARCHAR -> true

            Types.REAL,
            Types.FLOAT,
            Types.DECIMAL, Types.NUMERIC -> true

            Types.JAVA_OBJECT -> true

            else -> false
        }

    override fun requiredDataSize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.CHAR, Types.VARCHAR,
            Types.NCHAR, Types.NVARCHAR,
            Types.BINARY, Types.VARBINARY,
            Types.LONGVARCHAR, Types.LONGNVARCHAR -> true

            Types.REAL,
            Types.FLOAT,
            Types.DECIMAL, Types.NUMERIC -> true

            else -> false
        }

    override fun needNumericPrecision(typeCode: Int): Boolean =
        when (typeCode) {
            Types.REAL,
            Types.FLOAT,
            Types.DECIMAL, Types.NUMERIC -> true

            Types.TIME, Types.TIME_WITH_TIMEZONE,
            Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> true

            else -> false
        }

    override fun requiredNumericPrecision(typeCode: Int): Boolean =
        when (typeCode) {
            Types.REAL,
            Types.FLOAT,
            Types.DECIMAL, Types.NUMERIC -> true

            else -> false
        }

    override fun defaultDataSize(typeCode: Int): Long? = null

    override fun defaultNumericPrecision(typeCode: Int): Long? = null
    override fun getTypeName(typeMeta: ColumnTypeMeta): String {
        if (typeMeta.overwriteByRaw) return typeMeta.rawType

        if (typeMeta.rawType.uppercase().startsWith("ENUM")) {
            typeMeta.dataSize = 0
            typeMeta.numericPrecision = 0

            return typeMeta.rawType
        }

        when (typeMeta.typeCode) {
            Types.CHAR, Types.NCHAR -> {
                typeMeta.rawType = "CHAR"
            }

            Types.VARCHAR, Types.NVARCHAR, Types.LONGVARCHAR, Types.LONGNVARCHAR -> {
                typeMeta.rawType = "CHAR VARYING"
            }

            Types.BINARY -> {
                typeMeta.rawType = "BINARY"
            }

            Types.VARBINARY, Types.LONGVARBINARY -> {
                typeMeta.rawType = "BINARY VARYING"
            }

            Types.BIT -> {
                typeMeta.rawType = "BOOLEAN"
            }

            Types.TINYINT -> {
                typeMeta.rawType = "TINYINT"
            }

            Types.SMALLINT -> {
                typeMeta.rawType = "SMALLINT"
            }

            Types.INTEGER -> {
                typeMeta.rawType = "INTEGER"
            }

            Types.BIGINT -> {
                typeMeta.rawType = "BIGINT"
            }

            Types.REAL, Types.FLOAT -> {
                typeMeta.rawType = "REAL"
            }

            Types.DOUBLE -> {
                typeMeta.rawType = "DOUBLE PRECISION"
            }

            Types.NUMERIC, Types.DECIMAL -> {
                typeMeta.rawType = "NUMERIC"
            }

            Types.BOOLEAN -> {
                typeMeta.rawType = "BOOLEAN"
            }

            Types.DATE -> {
                typeMeta.rawType = "DATE"
            }

            Types.TIME -> {
                typeMeta.rawType = "TIME"
            }

            Types.TIME_WITH_TIMEZONE -> {
                typeMeta.rawType = "TIME WITH TIME ZONE"
            }

            Types.TIMESTAMP -> {
                typeMeta.rawType = "TIMESTAMP"
            }

            Types.TIMESTAMP_WITH_TIMEZONE -> {
                typeMeta.rawType = "TIMESTAMP WITH TIME ZONE"
            }

            Types.JAVA_OBJECT -> {
                typeMeta.rawType = "JAVA_OBJECT"
            }
        }

        return typeMeta.rawType
    }
}
