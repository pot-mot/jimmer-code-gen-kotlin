package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.core.database.meta.OutAssociationMeta
import top.potmot.entity.dto.GenTableConvertView

data class JoinColumnMeta(
    var joinColumnName: String,
    var referencedColumnName: String? = null,
    var foreignKeyType: ForeignKeyType? = null,
) {
    fun realFk() = foreignKeyType == ForeignKeyType.REAL
}

fun OutAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>.toJoinColumns(
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
