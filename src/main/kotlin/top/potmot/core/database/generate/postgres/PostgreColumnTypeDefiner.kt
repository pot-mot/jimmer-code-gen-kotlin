package top.potmot.core.database.generate.postgres

import top.potmot.core.database.generate.ColumnTypeDefiner
import top.potmot.model.dto.ColumnTypeMeta
import java.sql.Types

// https://www.postgresql.org/docs/current/datatype.html
class PostgreColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDisplaySize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.BIT -> true

            Types.DECIMAL, Types.NUMERIC -> true

            else -> false
        }

    override fun needNumericPrecision(typeCode: Int): Boolean =
        when (typeCode) {
            Types.DECIMAL, Types.NUMERIC -> true

            Types.TIME, Types.TIME_WITH_TIMEZONE,
            Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> true

            else -> false
        }

    override fun requiredDisplaySize(typeCode: Int): Boolean = false

    override fun requiredNumericPrecision(typeCode: Int): Boolean = false

    override fun defaultDisplaySize(typeCode: Int): Long? =
        when (typeCode) {
            Types.CHAR, Types.BIT -> 1L

            else -> null
        }

    override fun defaultNumericPrecision(typeCode: Int): Long? =
        when (typeCode) {
            Types.REAL,
            Types.FLOAT, Types.DOUBLE,
            Types.DECIMAL, Types.NUMERIC -> 0L

            Types.DATE,
            Types.TIME, Types.TIME_WITH_TIMEZONE,
            Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> 6L

            else -> null
        }

    override fun getTypeName(typeMeta: ColumnTypeMeta): String {
        if (typeMeta.overwriteByRaw) return typeMeta.rawType

        when (typeMeta.typeCode) {
            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
            Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR,
            Types.CLOB, Types.NCLOB -> {
                typeMeta.rawType = "TEXT"
            }

            Types.BLOB,
            Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> {
                typeMeta.rawType = "BYTEA"
            }

            Types.BIT -> {
                typeMeta.rawType = "BIT"
            }
            Types.TINYINT, Types.SMALLINT -> {
                typeMeta.rawType = "SMALLINT"
            }
            Types.INTEGER -> {
                typeMeta.rawType = "INT"
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

            Types.TIME_WITH_TIMEZONE -> {
                typeMeta.rawType = "TIMETZ"
            }
            Types.TIMESTAMP_WITH_TIMEZONE -> {
                typeMeta.rawType = "TIMESTAMPTZ"
            }

            Types.TIME -> {
                typeMeta.rawType = "TIME"
            }
            Types.TIMESTAMP -> {
                typeMeta.rawType = "TIMESTAMP"
            }
        }

        if (typeMeta.autoIncrement) {
            when (typeMeta.typeCode) {
                Types.TINYINT, Types.SMALLINT -> {
                    typeMeta.rawType = "SMALLSERIAL"
                }
                Types.INTEGER -> {
                    typeMeta.rawType = "SERIAL"
                }
                Types.BIGINT -> {
                    typeMeta.rawType = "BIGSERIAL"
                }
            }
        }

        return typeMeta.rawType
    }
}
