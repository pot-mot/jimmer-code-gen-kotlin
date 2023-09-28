package top.potmot.enum

import top.potmot.core.match.AssociationMatch
import top.potmot.core.match.includeTableNamePkColumnMatch
import top.potmot.core.match.pkSuffixColumnMatch
import top.potmot.core.match.simplePkColumnMatch

enum class AssociationMatchType {
    SIMPLE_PK,
    INCLUDE_TABLE_NAME,
    PK_SUFFIX,
}

fun getAssociationMatch(type: AssociationMatchType): AssociationMatch {
    return when (type) {
        AssociationMatchType.SIMPLE_PK -> simplePkColumnMatch
        AssociationMatchType.INCLUDE_TABLE_NAME -> includeTableNamePkColumnMatch
        AssociationMatchType.PK_SUFFIX -> pkSuffixColumnMatch
    }
}

fun AssociationMatchType.getMatch(): AssociationMatch {
    return getAssociationMatch(this)
}
