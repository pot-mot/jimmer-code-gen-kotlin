package top.potmot.core.database.generate.impl.h2

import top.potmot.core.database.generate.identifier.IdentifierFilter

// https://www.h2database.com/html/advanced.html#limits_limitations
private const val H2_IDENTIFIER_MAX_LENGTH = 256

object H2IdentifierFilter: IdentifierFilter(H2_IDENTIFIER_MAX_LENGTH)
