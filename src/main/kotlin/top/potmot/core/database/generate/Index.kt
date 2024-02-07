package top.potmot.core.database.generate

import top.potmot.core.database.generate.impl.h2.H2TableDefineGenerator
import top.potmot.core.database.generate.impl.mysql.MysqlTableDefineGenerator
import top.potmot.core.database.generate.impl.postgres.PostgresTableDefineGenerator
import top.potmot.enumeration.DataSourceType

fun DataSourceType.getTableDefineGenerator(): TableDefineGenerator =
    when (this) {
        DataSourceType.MySQL -> MysqlTableDefineGenerator
        DataSourceType.PostgreSQL -> PostgresTableDefineGenerator
        DataSourceType.H2 -> H2TableDefineGenerator
    }


