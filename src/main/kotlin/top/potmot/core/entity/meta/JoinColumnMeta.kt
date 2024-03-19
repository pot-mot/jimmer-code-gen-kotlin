package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.model.GenAssociation

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
    identifiers: IdentifierProcessor
) =
    columnReferences.map {
        JoinColumnMeta(
            joinColumnName = identifiers.process(it.sourceColumn.name, IdentifierType.COLUMN_NAME),
            referencedColumnName = identifiers.process(it.targetColumn.name, IdentifierType.COLUMN_NAME),
            foreignKeyType = if (fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
        )
    }

