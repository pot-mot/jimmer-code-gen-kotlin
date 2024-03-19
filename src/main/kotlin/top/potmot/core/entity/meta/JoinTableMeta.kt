package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.core.database.meta.createMappingColumnName
import top.potmot.model.GenAssociation

data class JoinTableMeta(
    val tableName: String,
    val columnNamePairs: List<Pair<String, String>>,
    val foreignKeyType: ForeignKeyType? = null,
)

fun GenAssociation.toJoinTable(
    identifiers: IdentifierProcessor
) =
    JoinTableMeta(
        identifiers.process(name, IdentifierType.TABLE_NAME),
        columnReferences.map {
            val sourceColumnName = createMappingColumnName(sourceTable.name, it.sourceColumn.name)
            val targetColumnName = createMappingColumnName(targetTable.name, it.targetColumn.name)

            Pair(
                identifiers.process(sourceColumnName, IdentifierType.COLUMN_NAME),
                identifiers.process(targetColumnName, IdentifierType.COLUMN_NAME),
            )
        },
        foreignKeyType = if (fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
    )
