package top.potmot.core.database.generate.postgres

import top.potmot.core.database.generate.ColumnTypeDefiner
import top.potmot.core.database.meta.ColumnTypeMeta
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
        if (typeMeta.overwriteByType) return typeMeta.type

        when (typeMeta.typeCode) {
            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
            Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR,
            Types.CLOB, Types.NCLOB -> {
                typeMeta.type = "TEXT"
            }

            Types.BLOB,
            Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> {
                typeMeta.type = "BYTEA"
            }

            Types.BIT -> {
                typeMeta.type = "BIT"
            }
            Types.TINYINT, Types.SMALLINT -> {
                typeMeta.type = "SMALLINT"
            }
            Types.INTEGER -> {
                typeMeta.type = "INT"
            }
            Types.BIGINT -> {
                typeMeta.type = "BIGINT"
            }

            Types.REAL, Types.FLOAT -> {
                typeMeta.type = "REAL"

            }
            Types.DOUBLE -> {
                typeMeta.type = "DOUBLE PRECISION"
            }
            Types.NUMERIC, Types.DECIMAL -> {
                typeMeta.type = "NUMERIC"
            }

            Types.BOOLEAN -> {
                typeMeta.type = "BOOLEAN"
            }


            Types.DATE -> {
                typeMeta.type = "DATE"
            }

            Types.TIME_WITH_TIMEZONE -> {
                typeMeta.type = "TIMETZ"
            }
            Types.TIMESTAMP_WITH_TIMEZONE -> {
                typeMeta.type = "TIMESTAMPTZ"
            }

            Types.TIME -> {
                typeMeta.type = "TIME"
            }
            Types.TIMESTAMP -> {
                typeMeta.type = "TIMESTAMP"
            }
        }

        if (typeMeta.autoIncrement) {
            when (typeMeta.typeCode) {
                Types.TINYINT, Types.SMALLINT -> {
                    typeMeta.type = "SMALLSERIAL"
                }
                Types.INTEGER -> {
                    typeMeta.type = "SERIAL"
                }
                Types.BIGINT -> {
                    typeMeta.type = "BIGSERIAL"
                }
            }
        }

        return typeMeta.type
    }
}
