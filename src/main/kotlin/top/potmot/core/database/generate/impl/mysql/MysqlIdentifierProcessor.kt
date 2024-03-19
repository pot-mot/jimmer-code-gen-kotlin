package top.potmot.core.database.generate.impl.mysql

import top.potmot.core.database.generate.identifier.IdentifierTruncator
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType

// https://dev.mysql.com/doc/refman/8.0/en/identifier-length.html
private const val MYSQL_IDENTIFIER_MAX_LENGTH = 64

private val MysqlIdentifierTruncator = IdentifierTruncator(MYSQL_IDENTIFIER_MAX_LENGTH)

object MysqlIdentifierProcessor : IdentifierProcessor {
    override fun escape(identifier: String): String =
        "`${identifier.removePrefix("`").removeSuffix("`")}`"

    override fun processIdentifier(identifier: String, type: IdentifierType) =
        MysqlIdentifierTruncator.process(identifier)
}
