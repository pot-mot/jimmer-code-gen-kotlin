package top.potmot.model.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenColumn
import top.potmot.model.GenTable
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

fun GenTable.getColumn(id: Long): GenColumn? {
    if (!ImmutableObjects.isLoaded(this, "columns")) {
        return null
    }
    return this.columns.find { it.id == id }
}

fun GenTable.getColumn(name: String): GenColumn? {
    if (!ImmutableObjects.isLoaded(this, "columns")) {
        return null
    }
    return this.columns.find { it.name == name }
}

fun GenTableAssociationsView.pkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfPk }

fun GenTableAssociationsView.fkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfFk }

fun GenTableAssociationsView.outColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.outAssociations.isNotEmpty() }

fun GenTableAssociationsView.inColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.inAssociations.isNotEmpty() }

fun getFullType(typeCode: Int, type: String, displaySize: Long, numericPrecision: Long): String =
    when (typeCode) {
        Types.BIT, Types.BOOLEAN,
        Types.TINYINT,
        Types.SMALLINT,
        Types.INTEGER,
        Types.BIGINT -> "$type($displaySize)"

        Types.REAL,
        Types.FLOAT, Types.DOUBLE,
        Types.DECIMAL, Types.NUMERIC -> "$type($displaySize, $numericPrecision)"

        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR,
        Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR,
        Types.CLOB, Types.NCLOB,
        Types.SQLXML, Types.DATALINK -> "$type($displaySize)"

        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> "$type($displaySize)"

        Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> type

        Types.NULL, Types.JAVA_OBJECT -> type

        else -> type
    }
