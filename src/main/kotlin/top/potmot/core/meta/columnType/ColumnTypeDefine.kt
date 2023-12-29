package top.potmot.core.meta.columnType

import top.potmot.config.GenConfig
import top.potmot.enumeration.DataSourceType

private val MYSQL_COLUMN_TYPE_DEFINER = MysqlColumnTypeDefiner()

private val POSTGRE_COLUMN_TYPE_DEFINER = PostgreColumnTypeDefiner()

fun DataSourceType?.getColumnTypeDefiner(): ColumnTypeDefiner =
    when (this ?: GenConfig.dataSourceType) {
        DataSourceType.MySQL -> MYSQL_COLUMN_TYPE_DEFINER
        DataSourceType.PostgreSQL -> POSTGRE_COLUMN_TYPE_DEFINER
    }

fun Set<DataSourceType>.getColumnTypeDefiner(): Map<DataSourceType, ColumnTypeDefiner> =
    associate { Pair(it, it.getColumnTypeDefiner()) }
