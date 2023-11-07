package top.potmot.core.template.table

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.DissociateAction.*
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DataSourceType.*
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations_2 as OutAssociation
import java.sql.Types

private fun getFullType(typeCode: Int, type: String, displaySize: Long, numericPrecision: Long): String =
    when (typeCode) {
        Types.NULL -> type
        Types.JAVA_OBJECT -> type
        Types.BIT, Types.BOOLEAN,
        Types.TINYINT,
        Types.SMALLINT,
        Types.INTEGER,
        Types.BIGINT -> "$type($displaySize)"

        Types.REAL,
        Types.FLOAT, Types.DOUBLE,
        Types.DECIMAL, Types.NUMERIC -> "$type($displaySize, $numericPrecision)"

        Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> "$type($displaySize)"
        Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> type
        Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> "$type($displaySize)"
        else -> type
    }

fun GenTableAssociationsView.TargetOf_columns.fullType(): String =
    getFullType(typeCode, type, displaySize, numericPrecision)

fun GenTableAssociationsView.TargetOf_columns.createFkConstraint(
    indexName: String,
    outAssociation: OutAssociation,
    dataSourceType: DataSourceType
): String =
    "CONSTRAINT ${indexName.escape(dataSourceType)} FOREIGN KEY (${name.escape(dataSourceType)})" +
        " REFERENCES ${outAssociation.targetColumn.table.name.escape(dataSourceType)} (${outAssociation.targetColumn.name.escape(dataSourceType)})" +
        " ${outAssociation.dissociateAction?.toOnDeleteAction() ?: ""}" +
        " ON UPDATE RESTRICT"

fun String.escape(dataSourceType: DataSourceType): String =
    when (dataSourceType) {
        MySQL -> if (this.startsWith("`") && this.endsWith("`")) this else "`$this`"
        PostgreSQL -> if (this.startsWith("\"") && this.endsWith("\"")) this else "\"$this\""
    }

private fun DissociateAction.toOnDeleteAction(): String =
    when (this) {
        NONE -> ""
        SET_NULL -> "ON DELETE SET NULL"
        DELETE -> "ON DELETE CASCADE"
        LAX -> ""
        CHECK -> ""
    }
