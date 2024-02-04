package top.potmot.core.database.generate.columnTypeDefiner

import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.mysql.MysqlColumnTypeDefiner
import top.potmot.core.database.generate.postgres.PostgreColumnTypeDefiner
import top.potmot.enumeration.DataSourceType

fun DataSourceType?.getColumnTypeDefiner(): ColumnTypeDefiner =
    when (this ?: getContextOrGlobal().dataSourceType) {
        DataSourceType.MySQL -> MysqlColumnTypeDefiner
        DataSourceType.PostgreSQL -> PostgreColumnTypeDefiner
    }
