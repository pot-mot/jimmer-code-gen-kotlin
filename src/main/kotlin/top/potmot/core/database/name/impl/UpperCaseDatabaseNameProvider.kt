package top.potmot.core.database.name.impl

import top.potmot.core.database.name.DatabaseNameProvider

object UpperCaseDatabaseNameProvider : DatabaseNameProvider {
    override fun default(base: String): String = base.uppercase()
}
