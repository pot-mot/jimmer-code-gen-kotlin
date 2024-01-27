package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.context.getContextGenConfig
import top.potmot.core.entity.meta.AssociationAnnotationMeta
import top.potmot.core.entity.meta.JoinColumnMeta
import top.potmot.core.entity.meta.JoinTableMeta
import top.potmot.utils.string.changeCase

open class AssociationAnnotationBuilder(
    val indent: String = "    "
) {
    open fun build(meta: JoinColumnMeta) =
        buildString {
            val onlyName = meta.referencedColumnName == null && meta.foreignKeyType == null

            if (onlyName) {
                append("@JoinColumn(name = \"${meta.joinColumnName}\")")
            } else {
                appendLine("@JoinColumn(")
                appendLine("${indent}name = \"${meta.joinColumnName}\",")

                if (meta.referencedColumnName != null) {
                    appendLine("${indent}referencedColumnName = \"${meta.referencedColumnName.changeCase()}\"")
                }
                if (meta.foreignKeyType != null) {
                    val realFk = getContextGenConfig().realFk

                    if (
                        (realFk && meta.foreignKeyType == ForeignKeyType.FAKE) ||
                        (!realFk && meta.foreignKeyType == ForeignKeyType.REAL)
                    ) {
                        appendLine("    foreignKeyType = ForeignKeyType.${meta.foreignKeyType.name}")
                    }
                }
                append(")")
            }
        }

    open fun build(meta: JoinTableMeta) =
        buildString {
            appendLine("@JoinTable(")
            appendLine("${indent}name = \"${meta.tableName}\",")

            if (meta.columnNamePairs.size == 1) {
                appendLine("${indent}joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                appendLine("${indent}inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
            } else {
                appendLine("${indent}joinColumns = [")
                meta.columnNamePairs.forEach {
                    appendLine("$indent${indent}JoinColumn(name = \"${it.first}\"),")
                }
                appendLine("$indent]")

                appendLine("${indent}inverseColumns = [")
                meta.columnNamePairs.forEach {
                    appendLine("$indent${indent}JoinColumn(name = \"${it.second}\"),")
                }
                appendLine("$indent]")
            }

            append(")")
        }

    open fun build(meta: AssociationAnnotationMeta) =
        buildString {
            append("@" + meta.type.toAnnotation().simpleName)
            if (meta.mappedBy.isNullOrBlank()) {
                if (getContextGenConfig().joinColumnAnnotation) {
                    meta.joinColumns.forEach {
                        append("\n${build(it)}")
                    }
                }

                if (getContextGenConfig().joinTableAnnotation) {
                    meta.joinTable?.let {
                        append("\n${build(it)}")
                    }
                }
            } else {
                append("(mappedBy = \"${meta.mappedBy}\")")
            }
        }
}
