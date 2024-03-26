package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType

data class JoinColumnMeta(
    var joinColumnName: String,
    var referencedColumnName: String? = null,
    var foreignKeyType: ForeignKeyType? = null,
)

fun OutAssociationMeta.toJoinColumns(
    identifiers: IdentifierProcessor
) =
    sourceColumns.mapIndexed { index, sourceColumn ->
        val targetColumn = targetColumns[index]
        JoinColumnMeta(
            joinColumnName = identifiers.process(sourceColumn.name, IdentifierType.COLUMN_NAME),
            referencedColumnName = identifiers.process(targetColumn.name, IdentifierType.COLUMN_NAME),
            foreignKeyType = if (association.fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
        )
    }

fun InAssociationMeta.toJoinColumns(
    identifiers: IdentifierProcessor
) =
    targetColumns.mapIndexed { index, targetColumn ->
        val sourceColumn = sourceColumns[index]
        JoinColumnMeta(
            joinColumnName = identifiers.process(targetColumn.name, IdentifierType.COLUMN_NAME),
            referencedColumnName = identifiers.process(sourceColumn.name, IdentifierType.COLUMN_NAME),
            foreignKeyType = if (association.fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
        )
    }
