package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.generate.identifier.IdentifierFilter
import top.potmot.core.database.meta.createMappingColumnName
import top.potmot.model.GenAssociation
import top.potmot.utils.string.changeCase

data class JoinTableMeta(
    val tableName: String,
    val columnNamePairs: List<Pair<String, String>>,
    val foreignKeyType: ForeignKeyType? = null,
)

fun GenAssociation.toJoinTable(
    identifierFilter: IdentifierFilter
) =
    JoinTableMeta(
        identifierFilter.getIdentifier(name).changeCase(),
        columnReferences.map {
            Pair(
                identifierFilter.getIdentifier(
                    createMappingColumnName(sourceTable.name, it.sourceColumn.name)
                ).changeCase(),
                identifierFilter.getIdentifier(
                    createMappingColumnName(targetTable.name, it.targetColumn.name)
                ).changeCase(),
            )
        },
        foreignKeyType = if (fake) ForeignKeyType.FAKE else ForeignKeyType.REAL
    )
