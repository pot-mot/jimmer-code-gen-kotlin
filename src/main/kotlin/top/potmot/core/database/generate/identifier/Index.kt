package top.potmot.core.database.generate.identifier

import top.potmot.enumeration.DataSourceType

private const val MYSQL_IDENTIFIER_MAX_LENGTH = 63
val MYSQL_IDENTIFIER_FILTER = IdentifierFilter(MYSQL_IDENTIFIER_MAX_LENGTH)

private const val POSTGRES_IDENTIFIER_MAX_LENGTH = 63
val POSTGRES_IDENTIFIER_FILTER = IdentifierFilter(POSTGRES_IDENTIFIER_MAX_LENGTH)

private const val H2_IDENTIFIER_MAX_LENGTH = 63
val H2_IDENTIFIER_FILTER = IdentifierFilter(H2_IDENTIFIER_MAX_LENGTH)

fun DataSourceType.getIdentifierFilter(): IdentifierFilter =
    when (this) {
        DataSourceType.MySQL -> MYSQL_IDENTIFIER_FILTER
        DataSourceType.PostgreSQL -> POSTGRES_IDENTIFIER_FILTER
        DataSourceType.H2 -> H2_IDENTIFIER_FILTER
    }
