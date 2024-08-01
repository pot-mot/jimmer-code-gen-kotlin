package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.context.getContextOrGlobal
import top.potmot.core.entity.meta.JoinColumnMeta
import top.potmot.core.entity.meta.JoinTableMeta

open class AssociationAnnotationBuilder(
    val indent: String = "    "
) {
    protected fun createForeignKeyType(foreignKeyType: ForeignKeyType?): String? {
        val realFk = getContextOrGlobal().realFk

        return if (
            (realFk && foreignKeyType == ForeignKeyType.FAKE) ||
            (!realFk && foreignKeyType == ForeignKeyType.REAL)
        ) {
            "foreignKeyType = ForeignKeyType.${foreignKeyType.name}"
        } else null
    }

    open fun build(meta: JoinColumnMeta) =
        buildString {
            val onlyName = meta.referencedColumnName == null && meta.foreignKeyType == null

            if (onlyName) {
                append("@JoinColumn(name = \"${meta.joinColumnName}\")")
            } else {
                appendLine("@JoinColumn(")
                append("${indent}name = \"${meta.joinColumnName}\"")

                meta.referencedColumnName?.let {
                    append(",\n${indent}referencedColumnName = \"$it\"")
                }

                createForeignKeyType(meta.foreignKeyType)?.let {
                    append(",\n${indent}$it")
                }
                append("\n)")
            }
        }

    open fun build(meta: JoinTableMeta) =
        buildString {
            appendLine("@JoinTable(")
            appendLine("${indent}name = \"${meta.tableName}\",")

            val foreignKeyTypeProp = createForeignKeyType(meta.foreignKeyType)

            if (meta.columnNamePairs.size == 1 && foreignKeyTypeProp == null) {
                appendLine("${indent}joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                appendLine("${indent}inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
            } else {
                appendLine("${indent}joinColumns = [")
                meta.columnNamePairs.forEach {
                    appendLine("$indent${indent}JoinColumn(name = \"${it.first}\", ${foreignKeyTypeProp ?: ""}),")
                }
                appendLine("$indent],")

                appendLine("${indent}inverseJoinColumns = [")
                meta.columnNamePairs.forEach {
                    appendLine("$indent${indent}JoinColumn(name = \"${it.second}\", ${foreignKeyTypeProp ?: ""}),")
                }
                appendLine("$indent]")
            }

            append(")")
        }
}
