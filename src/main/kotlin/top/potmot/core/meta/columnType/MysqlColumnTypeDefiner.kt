package top.potmot.core.meta.columnType

import java.sql.Types

// https://www.mysqlzh.com/doc/106.html
class MysqlColumnTypeDefiner : ColumnTypeDefiner {
    override fun needDisplaySize(typeCode: Int): Boolean =
        when (typeCode) {
            Types.BIT, Types.BOOLEAN,
            Types.TINYINT,
            Types.SMALLINT,
            Types.INTEGER,
            Types.BIGINT -> true

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

    override fun requiredDisplaySize(typeCode: Int): Boolean = needDisplaySize(typeCode)

    override fun requiredNumericPrecision(typeCode: Int): Boolean = needNumericPrecision(typeCode)

    override fun defaultDisplaySize(typeCode: Int): Long? = null

    override fun defaultNumericPrecision(typeCode: Int): Long? = null
    override fun getTypeName(typeMeta: ColumnTypeMeta): String {

        if (!typeMeta.type.uppercase().startsWith("ENUM") || !typeMeta.type.uppercase().startsWith("SET")) {
            when (typeMeta.typeCode) {
                Types.CHAR, Types.NCHAR -> {
                    typeMeta.type = "CHAR"
                }

                Types.VARCHAR, Types.NVARCHAR -> {
                    typeMeta.type = "VARCHAR"
                }

                Types.LONGVARCHAR, Types.LONGNVARCHAR -> {
                    typeMeta.type = "LONGTEXT"
                    typeMeta.displaySize = 0
                }

                Types.BINARY -> {
                    typeMeta.type = "BINARY"
                }

                Types.VARBINARY, Types.LONGVARBINARY -> {
                    typeMeta.type = "VARBINARY"
                }

                Types.BIT -> {
                    typeMeta.type = "BIT"
                }

                Types.TINYINT -> {
                    typeMeta.type = "TINYINT"
                }

                Types.SMALLINT -> {
                    typeMeta.type = "SMALLINT"
                }

                Types.INTEGER -> {
                    typeMeta.type = "INT"
                }

                Types.BIGINT -> {
                    typeMeta.type = "BIGINT"
                }

                Types.REAL, Types.FLOAT -> {
                    typeMeta.type = "FLOAT"
                }

                Types.DOUBLE -> {
                    typeMeta.type = "DOUBLE"
                }

                Types.NUMERIC, Types.DECIMAL -> {
                    typeMeta.type = "DECIMAL"
                }

                Types.BOOLEAN -> {
                    typeMeta.type = "BOOLEAN"
                }

                Types.DATE -> {
                    typeMeta.type = "DATE"
                }
                Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP_WITH_TIMEZONE -> {
                    typeMeta.type = "TIMESTAMP"
                }
                Types.TIME, Types.TIMESTAMP -> {
                    typeMeta.type = "DATETIME"
                }
            }
        }

        return typeMeta.type
    }
}
