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
            return typeMeta.rawType
        }

        return when (typeMeta.typeCode) {
            Types.CHAR, Types.NCHAR -> "CHARACTER"

            Types.VARCHAR, Types.NVARCHAR, Types.LONGVARCHAR, Types.LONGNVARCHAR -> "CHARACTER VARYING"

            Types.BINARY -> "BINARY"

            Types.VARBINARY, Types.LONGVARBINARY -> "BINARY VARYING"

            Types.BIT -> "BOOLEAN"

            Types.TINYINT -> "TINYINT"

            Types.SMALLINT -> "SMALLINT"

            Types.INTEGER -> "INTEGER"

            Types.BIGINT -> "BIGINT"

            Types.REAL, Types.FLOAT -> "REAL"

            Types.DOUBLE -> "DOUBLE PRECISION"

            Types.NUMERIC, Types.DECIMAL -> "NUMERIC"

            Types.BOOLEAN -> "BOOLEAN"

            Types.DATE -> "DATE"

            Types.TIME -> "TIME"

            Types.TIME_WITH_TIMEZONE -> "TIME WITH TIME ZONE"

            Types.TIMESTAMP -> "TIMESTAMP"

            Types.TIMESTAMP_WITH_TIMEZONE -> "TIMESTAMP WITH TIME ZONE"

            Types.JAVA_OBJECT -> "JAVA_OBJECT"

            else -> typeMeta.rawType
        }
    }
}
