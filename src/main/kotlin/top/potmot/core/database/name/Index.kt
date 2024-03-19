package top.potmot.core.database.name

import top.potmot.core.database.name.impl.LowerCaseDatabaseNameProvider
import top.potmot.core.database.name.impl.RawDatabaseNameProvider
import top.potmot.core.database.name.impl.UpperCaseDatabaseNameProvider
import top.potmot.enumeration.DatabaseNamingStrategyType

fun DatabaseNamingStrategyType.getNameProvider(): DatabaseNameProvider =
    when (this) {
        DatabaseNamingStrategyType.RAW -> RawDatabaseNameProvider
        DatabaseNamingStrategyType.LOWER_CASE -> LowerCaseDatabaseNameProvider
        DatabaseNamingStrategyType.UPPER_CASE -> UpperCaseDatabaseNameProvider
    }
