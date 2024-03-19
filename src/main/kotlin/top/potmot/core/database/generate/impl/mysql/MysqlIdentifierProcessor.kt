package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.identifier.IdentifierProcessor

// https://dev.mysql.com/doc/refman/8.0/en/identifier-length.html
private const val MYSQL_IDENTIFIER_MAX_LENGTH = 64

object MysqlIdentifierProcessor : IdentifierProcessor(MYSQL_IDENTIFIER_MAX_LENGTH)
