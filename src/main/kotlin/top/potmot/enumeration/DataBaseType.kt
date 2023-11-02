package top.potmot.enumeration

import java.sql.Types

enum class DataBaseType(
    val code: Int
) {
    BOOLEAN(Types.BOOLEAN),
    TINYINT(Types.TINYINT),
    SMALLINT(Types.SMALLINT),
    INTEGER(Types.INTEGER),
    BIGINT(Types.BIGINT),
    DOUBLE(Types.DOUBLE),
    DECIMAL(Types.DECIMAL),
    VARCHAR(Types.VARCHAR),
    XML(Types.SQLXML),
    DATA(Types.DATE),
    TIME(Types.TIME),
    TIMESTAMP(Types.TIMESTAMP),
    BLOB(Types.BLOB);
}
