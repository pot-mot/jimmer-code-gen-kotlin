package top.potmot.core.entity.meta

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.context.getContextGenConfig
import top.potmot.model.GenAssociation
import top.potmot.utils.identifier.IdentifierFilter
import top.potmot.utils.string.changeCase

data class JoinColumnMeta(
    val joinColumnName: String,
    val referencedColumnName: String? = null,
    val foreignKeyType: ForeignKeyType? = null,
) {
    fun toAnnotation() =
        buildString {
            val onlyName = referencedColumnName == null && foreignKeyType == null

            if (onlyName) {
                append("@JoinColumn(name = \"$joinColumnName\")")
            } else {
                appendLine("@JoinColumn(")
                appendLine("    name = \"$joinColumnName\",")

                if (referencedColumnName != null) {
                    appendLine("    referencedColumnName = \"${referencedColumnName.changeCase()}\"")
                }
                if (foreignKeyType != null) {
                    val realFk = getContextGenConfig().realFk

                    if (
                        (realFk && foreignKeyType == ForeignKeyType.FAKE) ||
                        (!realFk && foreignKeyType == ForeignKeyType.REAL)
                    ) {
                        appendLine("    foreignKeyType = ForeignKeyType.${foreignKeyType.name}")
                    }
                }
                append(")")
            }
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
