package top.potmot.core.database.generate

import top.potmot.context.getContextGenConfig
import top.potmot.core.database.generate.mysql.MysqlColumnTypeDefiner
import top.potmot.core.database.generate.postgres.PostgreColumnTypeDefiner
import top.potmot.enumeration.DataSourceType

private val MYSQL_COLUMN_TYPE_DEFINER = MysqlColumnTypeDefiner()

private val POSTGRE_COLUMN_TYPE_DEFINER = PostgreColumnTypeDefiner()

fun DataSourceType?.getColumnTypeDefiner(): ColumnTypeDefiner =
    when (this ?: getContextGenConfig().dataSourceType) {
        DataSourceType.MySQL -> MYSQL_COLUMN_TYPE_DEFINER
        DataSourceType.PostgreSQL -> POSTGRE_COLUMN_TYPE_DEFINER
    }
