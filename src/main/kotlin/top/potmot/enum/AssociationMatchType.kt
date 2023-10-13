package top.potmot.enum

import top.potmot.core.match.AssociationMatch
import top.potmot.core.match.includeTableNamePkColumnMatch
import top.potmot.core.match.pkSuffixColumnMatch
import top.potmot.core.match.simplePkColumnMatch

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
