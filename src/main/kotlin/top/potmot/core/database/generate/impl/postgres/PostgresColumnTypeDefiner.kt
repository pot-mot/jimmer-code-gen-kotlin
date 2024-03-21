package top.potmot.core.database.generate.impl.postgres

import top.potmot.core.database.generate.columnType.ColumnTypeDefiner
import top.potmot.model.dto.ColumnTypeMeta
import java.sql.Types

// https://www.postgresql.org/docs/current/datatype.html
object PostgresColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDataSize(typeCode: Int): Boolean =
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

    override fun requiredDataSize(typeCode: Int): Boolean = false

    override fun requiredNumericPrecision(typeCode: Int): Boolean = false

    override fun defaultDataSize(typeCode: Int): Long? =
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

        if (typeMeta.autoIncrement) {
            return when (typeMeta.typeCode) {
                Types.TINYINT, Types.SMALLINT -> "SMALLSERIAL"

                Types.INTEGER -> "SERIAL"

                Types.BIGINT -> "BIGSERIAL"

                else -> typeMeta.rawType
            }
        }

        return when (typeMeta.typeCode) {
            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
            Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR,
            Types.CLOB, Types.NCLOB -> "TEXT"

            Types.BLOB,
            Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> "BYTEA"

            Types.BIT -> "BIT"

            Types.TINYINT, Types.SMALLINT -> "SMALLINT"

            Types.INTEGER -> "INT"

            Types.BIGINT -> "BIGINT"

            Types.REAL, Types.FLOAT -> "REAL"

            Types.DOUBLE -> "DOUBLE PRECISION"

            Types.NUMERIC, Types.DECIMAL -> "NUMERIC"

            Types.BOOLEAN -> "BOOLEAN"


            Types.DATE -> "DATE"

            Types.TIME_WITH_TIMEZONE -> "TIMETZ"

            Types.TIMESTAMP_WITH_TIMEZONE -> "TIMESTAMPTZ"

            Types.TIME -> "TIME"

            Types.TIMESTAMP -> "TIMESTAMP"

            else -> typeMeta.rawType
        }
    }
}
