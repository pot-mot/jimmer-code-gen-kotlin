package top.potmot.core.database.generate.impl.h2

import top.potmot.core.database.generate.identifier.IdentifierTruncator
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType

// https://www.h2database.com/html/advanced.html#limits_limitations
private const val H2_IDENTIFIER_MAX_LENGTH = 256

private val H2IdentifierTruncator = IdentifierTruncator(H2_IDENTIFIER_MAX_LENGTH)

object H2IdentifierProcessor: IdentifierProcessor {
    override fun escape(identifier: String): String =
        "`${identifier.removePrefix("`").removeSuffix("`")}`"

    override fun processIdentifier(identifier: String, type: IdentifierType) =
        H2IdentifierTruncator.process(identifier)
}
