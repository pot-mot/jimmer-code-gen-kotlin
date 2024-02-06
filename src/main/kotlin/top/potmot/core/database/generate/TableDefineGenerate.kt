package top.potmot.core.database.generate

import top.potmot.core.database.generate.h2.H2TableDefineGenerator
import top.potmot.core.database.generate.mysql.MysqlTableDefineGenerator
import top.potmot.core.database.generate.postgres.PostgreTableDefineGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.utils.identifier.IdentifierFilter

fun DataSourceType.getTableDefineGenerator(): TableDefineGenerator =
    when (this) {
        DataSourceType.MySQL -> MysqlTableDefineGenerator
        DataSourceType.PostgreSQL -> PostgreTableDefineGenerator
        DataSourceType.H2 -> H2TableDefineGenerator
    }

private const val MYSQL_IDENTIFIER_MAX_LENGTH = 63
private val MYSQL_IDENTIFIER_FILTER = IdentifierFilter(MYSQL_IDENTIFIER_MAX_LENGTH)

private const val POSTGRE_IDENTIFIER_MAX_LENGTH = 63
private val POSTGRE_IDENTIFIER_FILTER = IdentifierFilter(POSTGRE_IDENTIFIER_MAX_LENGTH)

private const val H2_IDENTIFIER_MAX_LENGTH = 63
private val H2_IDENTIFIER_FILTER = IdentifierFilter(H2_IDENTIFIER_MAX_LENGTH)

fun DataSourceType.getIdentifierFilter(): IdentifierFilter =
    when (this) {
        DataSourceType.MySQL -> MYSQL_IDENTIFIER_FILTER
        DataSourceType.PostgreSQL -> POSTGRE_IDENTIFIER_FILTER
        DataSourceType.H2 -> H2_IDENTIFIER_FILTER
    }


