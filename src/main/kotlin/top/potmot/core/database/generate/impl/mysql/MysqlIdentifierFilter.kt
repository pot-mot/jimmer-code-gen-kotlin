package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.identifier.IdentifierFilter

// https://dev.mysql.com/doc/refman/8.0/en/identifier-length.html
private const val MYSQL_IDENTIFIER_MAX_LENGTH = 64

object MysqlIdentifierFilter: IdentifierFilter(MYSQL_IDENTIFIER_MAX_LENGTH)
