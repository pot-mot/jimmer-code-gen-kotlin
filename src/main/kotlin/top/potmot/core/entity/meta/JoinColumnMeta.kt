package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierFilter
import top.potmot.model.GenAssociation
import top.potmot.utils.string.changeCase

data class JoinColumnMeta(
    var joinColumnName: String,
    var referencedColumnName: String? = null,
    var foreignKeyType: ForeignKeyType? = null,
) {
    fun reverse(): JoinColumnMeta {
        val temp = joinColumnName
        joinColumnName = referencedColumnName ?: ""
        referencedColumnName = temp
        return this
    }
}

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

