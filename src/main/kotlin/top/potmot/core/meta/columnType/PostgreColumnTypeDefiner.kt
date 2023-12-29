package top.potmot.core.meta.columnType

import java.sql.Types


class PostgreColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDisplaySize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.BIT, Types.BOOLEAN,
            Types.TINYINT,
            Types.SMALLINT,
            Types.INTEGER,
            Types.BIGINT -> true

            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
            Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR -> true

            Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> true

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

            Types.DATE,
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
}
