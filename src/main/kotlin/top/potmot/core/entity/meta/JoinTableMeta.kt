package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.core.database.meta.createMappingColumnName

data class JoinTableMeta(
    val tableName: String,
    val columnNamePairs: List<Pair<String, String>>,
    val foreignKeyType: ForeignKeyType? = null,
) {
    fun realFk() = foreignKeyType == ForeignKeyType.REAL
}

fun AssociationMeta.toJoinTable(
    identifiers: IdentifierProcessor,
) =
    JoinTableMeta(
        identifiers.process(association.name, IdentifierType.TABLE_NAME),
        sourceColumns.mapIndexed { index, sourceColumn ->
            val targetColumn = targetColumns[index]

            val sourceColumnName = createMappingColumnName(sourceTable.name, sourceColumn.name)
            val targetColumnName = createMappingColumnName(targetTable.name, targetColumn.name)

            Pair(
                identifiers.process(sourceColumnName, IdentifierType.COLUMN_NAME),
                identifiers.process(targetColumnName, IdentifierType.COLUMN_NAME),
            )
        },
        foreignKeyType = if (association.fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
    )