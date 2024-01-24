package top.potmot.core.database.generate.columnTypeDefiner

import top.potmot.context.getContextGenConfig
import top.potmot.core.database.generate.mysql.MysqlColumnTypeDefiner
import top.potmot.core.database.generate.postgres.PostgreColumnTypeDefiner
import top.potmot.enumeration.DataSourceType

fun DataSourceType?.getColumnTypeDefiner(): ColumnTypeDefiner =
    when (this ?: getContextGenConfig().dataSourceType) {
        DataSourceType.MySQL -> MysqlColumnTypeDefiner
        DataSourceType.PostgreSQL -> PostgreColumnTypeDefiner
    }
