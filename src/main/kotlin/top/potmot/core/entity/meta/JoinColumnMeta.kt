package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.model.GenAssociation
import top.potmot.utils.identifier.IdentifierFilter
import top.potmot.utils.string.changeCase

data class JoinColumnMeta(
    val joinColumnName: String,
    val referencedColumnName: String? = null,
    val foreignKeyType: ForeignKeyType? = null,
)

fun GenAssociation.toJoinColumns(
    identifierFilter: IdentifierFilter
) =
    columnReferences.map {
        JoinColumnMeta(
            joinColumnName = identifierFilter.getIdentifier(it.sourceColumn.name).changeCase(),
            referencedColumnName = identifierFilter.getIdentifier(it.targetColumn.name).changeCase(),
            foreignKeyType = if (fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
        )
    }

