package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
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
    identifierProcessor: IdentifierProcessor
) =
    columnReferences.map {
        JoinColumnMeta(
            joinColumnName = identifierProcessor.process(it.sourceColumn.name).changeCase(),
            referencedColumnName = identifierProcessor.process(it.targetColumn.name).changeCase(),
            foreignKeyType = if (fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
        )
    }

