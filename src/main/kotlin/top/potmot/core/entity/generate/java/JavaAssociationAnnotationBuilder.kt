package top.potmot.core.entity.generate.java

import top.potmot.core.entity.generate.builder.AssociationAnnotationBuilder
import top.potmot.core.entity.meta.JoinTableMeta

object JavaAssociationAnnotationBuilder: AssociationAnnotationBuilder("        ") {
    override fun build(meta: JoinTableMeta) =
        buildString {
            appendLine("@JoinTable(")
            appendLine("${indent}name = \"${meta.tableName}\",")

            if (meta.columnNamePairs.size == 1) {
                appendLine("${indent}joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                appendLine("${indent}inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
            } else {
                appendLine("${indent}joinColumns = {")
                meta.columnNamePairs.forEach {
                    appendLine("${indent}${indent}@JoinColumn(name = \"${it.first}\"),")
                }
                appendLine("${indent}}")

                appendLine("${indent}inverseColumns = {")
                meta.columnNamePairs.forEach {
                    appendLine("${indent}${indent}@JoinColumn(name = \"${it.second}\"),")
                }
                appendLine("${indent}}")
            }

            append(")")
        }
}
