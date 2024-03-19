package top.potmot.core.database.name.impl

import top.potmot.core.database.name.DatabaseNameProvider

object LowerCaseDatabaseNameProvider : DatabaseNameProvider {
    override fun default(base: String): String = base.lowercase()
}
