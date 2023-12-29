package top.potmot.core.meta.columnType

import java.sql.Types

class MysqlColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDisplaySize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.BIT, Types.BOOLEAN,
            Types.TINYINT,
            Types.SMALLINT,
            Types.INTEGER,
            Types.BIGINT -> true

            Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
            Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR,
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

            else -> false
        }

    override fun requiredDisplaySize(typeCode: Int): Boolean = needDisplaySize(typeCode)

    override fun requiredNumericPrecision(typeCode: Int): Boolean = needNumericPrecision(typeCode)

    override fun defaultDisplaySize(typeCode: Int): Long? = null

    override fun defaultNumericPrecision(typeCode: Int): Long? = null
}
