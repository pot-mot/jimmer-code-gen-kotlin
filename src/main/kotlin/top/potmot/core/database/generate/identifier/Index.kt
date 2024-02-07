package top.potmot.core.database.generate.identifier

import top.potmot.core.database.generate.impl.h2.H2IdentifierFilter
import top.potmot.core.database.generate.impl.mysql.MysqlIdentifierFilter
import top.potmot.core.database.generate.impl.postgres.PostgresIdentifierFilter
import top.potmot.enumeration.DataSourceType

fun DataSourceType.getIdentifierFilter(): IdentifierFilter =
    when (this) {
        DataSourceType.MySQL -> MysqlIdentifierFilter
        DataSourceType.PostgreSQL -> PostgresIdentifierFilter
        DataSourceType.H2 -> H2IdentifierFilter
    }
