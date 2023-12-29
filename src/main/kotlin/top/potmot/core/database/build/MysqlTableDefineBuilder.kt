package top.potmot.core.database.build

import top.potmot.core.meta.MappingTableMeta
import top.potmot.core.meta.columnType.ColumnTypeDefiner
import top.potmot.core.meta.columnType.ColumnTypeMeta
import top.potmot.core.meta.columnType.getColumnTypeDefiner
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

class MysqlTableDefineBuilder : TableDefineBuilder() {
    override fun getColumnTypeDefiner(): ColumnTypeDefiner = DataSourceType.MySQL.getColumnTypeDefiner()

    override fun String.escape(): String =
        "`${removePrefix("`").removeSuffix("`")}`"

    private fun defaultParams(comment: String): String =
        """
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '${comment}'
  ROW_FORMAT = Dynamic"""

    override fun createTable(
        table: GenTableAssociationsView,
        otherLines: List<String>,
        append: String,
        withIndex: Boolean
    ): String =
        super.createTable(
            table,
            otherLines,
            append = append + defaultParams(table.comment),
            withIndex
        )

    override fun createMappingTable(
        meta: MappingTableMeta,
        otherLines: List<String>,
        append: String,
    ): String {
        return super.createMappingTable(
            meta,
            otherLines,
            append + defaultParams(meta.comment)
        )
    }

    override fun getColumnTypeDefine(typeMeta: ColumnTypeMeta): String =
        if (typeMeta.type.uppercase().startsWith("ENUM")) {
            "VARCHAR(255)"
        } else {
            when (typeMeta.typeCode) {
                Types.CHAR, Types.NCHAR -> {
                    if (typeMeta.displaySize >= 65535) {
                        typeMeta.type = "LONGTEXT"
                        typeMeta.displaySize = 0
                        typeMeta.numericPrecision = 0
                    } else {
                        typeMeta.type = "CHAR"
                    }
                }

                Types.VARCHAR, Types.LONGVARCHAR,
                Types.NVARCHAR, Types.LONGNVARCHAR -> {
                    if (typeMeta.displaySize >= 65535) {
                        typeMeta.type = "LONGTEXT"
                        typeMeta.displaySize = 0
                        typeMeta.numericPrecision = 0
                    } else {
                        typeMeta.type = "VARCHAR"
                    }
                }

                Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP_WITH_TIMEZONE -> {
                    typeMeta.type = "TIMESTAMP"
                }

                Types.TIME, Types.TIMESTAMP -> {
                    typeMeta.type = "DATETIME"
                }
            }

            super.getColumnTypeDefine(typeMeta)
        }

    override fun columnStringify(column: GenTableAssociationsView.TargetOf_columns): String =
        super.columnStringify(column) + if (column.autoIncrement) " AUTO_INCREMENT" else ""
}
