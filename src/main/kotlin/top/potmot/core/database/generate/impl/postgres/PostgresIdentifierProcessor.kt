package top.potmot.core.database.generate.impl.postgres

import top.potmot.core.database.generate.identifier.IdentifierProcessor

// https://www.postgresql.org/docs/current/sql-syntax-lexical.html#SQL-SYNTAX-IDENTIFIERS
private const val POSTGRES_IDENTIFIER_MAX_LENGTH = 63

object PostgresIdentifierProcessor : IdentifierProcessor(POSTGRES_IDENTIFIER_MAX_LENGTH)
