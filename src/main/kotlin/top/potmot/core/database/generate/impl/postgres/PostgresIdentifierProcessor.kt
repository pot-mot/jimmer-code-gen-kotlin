package top.potmot.core.database.generate.impl.postgres

import top.potmot.core.database.generate.identifier.IdentifierTruncator
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType

// https://www.postgresql.org/docs/current/sql-syntax-lexical.html#SQL-SYNTAX-IDENTIFIERS
private const val POSTGRES_IDENTIFIER_MAX_LENGTH = 63

private val PostgresIdentifierTruncator = IdentifierTruncator(POSTGRES_IDENTIFIER_MAX_LENGTH)

object PostgresIdentifierProcessor : IdentifierProcessor {
    override fun escape(identifier: String): String =
        "\"${identifier.removePrefix("\"").removeSuffix("\"")}\""

    override fun processIdentifier(identifier: String, type: IdentifierType) =
        PostgresIdentifierTruncator.process(identifier)
}
