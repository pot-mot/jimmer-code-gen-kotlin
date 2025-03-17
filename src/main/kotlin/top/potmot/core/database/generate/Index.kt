package top.potmot.core.database.generate

import top.potmot.core.database.generate.impl.h2.H2DDLGenerator
import top.potmot.core.database.generate.impl.mysql.MysqlDDLGenerator
import top.potmot.core.database.generate.impl.postgres.PostgresDDLGenerator
import top.potmot.enumeration.DataSourceType

fun DataSourceType.getDDLGenerator(): DDLGenerator =
    when (this) {
        DataSourceType.MySQL -> MysqlDDLGenerator
        DataSourceType.PostgreSQL -> PostgresDDLGenerator
        DataSourceType.H2 -> H2DDLGenerator
    }


