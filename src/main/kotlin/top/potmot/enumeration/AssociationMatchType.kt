package top.potmot.enumeration

import top.potmot.core.database.match.AssociationMatch
import top.potmot.core.database.match.includeTableNamePkColumnMatch
import top.potmot.core.database.match.pkSuffixColumnMatch
import top.potmot.core.database.match.simplePkColumnMatch

enum class AssociationMatchType {
    SIMPLE_PK,
    INCLUDE_TABLE_NAME,
    PK_SUFFIX;

    fun toMatchMethod(): AssociationMatch =
        when (this) {
            SIMPLE_PK -> simplePkColumnMatch
            INCLUDE_TABLE_NAME -> includeTableNamePkColumnMatch
            PK_SUFFIX -> pkSuffixColumnMatch
        }
}
