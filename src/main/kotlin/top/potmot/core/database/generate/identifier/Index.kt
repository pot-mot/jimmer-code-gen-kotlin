package top.potmot.core.database.generate.identifier

import top.potmot.core.database.generate.impl.h2.H2IdentifierProcessor
import top.potmot.core.database.generate.impl.mysql.MysqlIdentifierProcessor
import top.potmot.core.database.generate.impl.postgres.PostgresIdentifierProcessor
import top.potmot.enumeration.DataSourceType

fun DataSourceType.getIdentifierProcessor(): IdentifierProcessor =
    when (this) {
        DataSourceType.MySQL -> MysqlIdentifierProcessor
        DataSourceType.PostgreSQL -> PostgresIdentifierProcessor
        DataSourceType.H2 -> H2IdentifierProcessor
    }
