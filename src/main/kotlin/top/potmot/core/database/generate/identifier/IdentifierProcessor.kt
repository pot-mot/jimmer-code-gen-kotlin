package top.potmot.core.database.generate.identifier

import top.potmot.context.getContextOrGlobal
import top.potmot.enumeration.DatabaseNamingStrategyType.LOWER_CASE
import top.potmot.enumeration.DatabaseNamingStrategyType.RAW
import top.potmot.enumeration.DatabaseNamingStrategyType.UPPER_CASE

/**
 * 标志符处理器，供应数据库定制化的标志符规则
 */
interface IdentifierProcessor {
    fun processIdentifier(identifier: String, type: IdentifierType): String

    fun escape(identifier: String): String

    fun process(identifier: String, type: IdentifierType) =
        changeCase(processIdentifier(identifier.trim(), type))

    fun processAndEscape(identifier: String, type: IdentifierType) =
        escape(process(identifier.trim(), type))

    fun changeCase(identifier: String): String = when (getContextOrGlobal().databaseNamingStrategy) {
        RAW -> identifier
        LOWER_CASE -> identifier.lowercase()
        UPPER_CASE -> identifier.uppercase()
    }

    fun default(identifier: String) = processAndEscape(identifier, IdentifierType.DEFAULT)

    fun tableName(identifier: String) = processAndEscape(identifier, IdentifierType.TABLE_NAME)

    fun columnName(identifier: String) = processAndEscape(identifier, IdentifierType.COLUMN_NAME)

    fun indexName(identifier: String) = processAndEscape(identifier, IdentifierType.INDEX_NAME)

    fun constraintName(identifier: String) = processAndEscape(identifier, IdentifierType.CONSTRAINT_NAME)

    fun primaryKeyName(identifier: String) = processAndEscape(identifier, IdentifierType.PRIMARY_KEY_NAME)

    fun foreignKeyName(identifier: String) = processAndEscape(identifier, IdentifierType.FOREIGN_KEY_NAME)
}
