package top.potmot.core.database.generate.columnType

import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.impl.h2.H2ColumnTypeDefiner
import top.potmot.core.database.generate.impl.mysql.MysqlColumnTypeDefiner
import top.potmot.core.database.generate.impl.postgres.PostgresColumnTypeDefiner
import top.potmot.enumeration.DataSourceType

fun DataSourceType?.getColumnTypeDefiner(): ColumnTypeDefiner =
    when (this ?: getContextOrGlobal().dataSourceType) {
        DataSourceType.MySQL -> MysqlColumnTypeDefiner
        DataSourceType.PostgreSQL -> PostgresColumnTypeDefiner
        DataSourceType.H2 -> H2ColumnTypeDefiner
    }
