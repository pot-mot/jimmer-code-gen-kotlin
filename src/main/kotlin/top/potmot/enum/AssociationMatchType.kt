package top.potmot.enum

import top.potmot.core.association.AssociationMatch
import top.potmot.core.association.includeTableNamePkColumnMatch
import top.potmot.core.association.pkSuffixColumnMatch
import top.potmot.core.association.simplePkColumnMatch

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
