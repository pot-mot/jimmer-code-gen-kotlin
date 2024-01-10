package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.core.database.meta.createMappingColumnName
import top.potmot.model.GenAssociation
import top.potmot.utils.identifier.IdentifierFilter
import top.potmot.utils.string.changeCase

data class JoinTableMeta(
    val tableName: String,
    val columnNamePairs: List<Pair<String, String>>
) {
    fun toAnnotation() =
        buildString {
            appendLine("@JoinTable(")
            appendLine("    name = \"$tableName\",")

            if (columnNamePairs.size == 1) {
                appendLine("    joinColumnName = \"${columnNamePairs[0].first}\",")
                appendLine("    inverseJoinColumnName = \"${columnNamePairs[0].second}\"")
            } else {
                appendLine("    joinColumns = {")

                columnNamePairs.forEach {
                    JoinColumnMeta(it.first, it.second, ForeignKeyType.REAL)
                        .toAnnotation()
                        .split("\n")
                        .forEach {line ->
                            appendLine("        $line")
                        }
                }
                appendLine("    }")
            }

            appendLine(")")
        }
}

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
        }
    )
