package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.builder.AssociationAnnotationBuilder
import top.potmot.core.entity.meta.JoinTableMeta

object JavaAssociationAnnotationBuilder: AssociationAnnotationBuilder("        ") {
    override fun build(meta: JoinTableMeta) =
        buildString {
            appendLine("@JoinTable(")
            appendLine("${indent}name = \"${meta.tableName}\",")

            val foreignKeyTypeProp = createForeignKeyType(meta.foreignKeyType)

            if (meta.columnNamePairs.size == 1 && foreignKeyTypeProp == null) {
                appendLine("${indent}joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                appendLine("${indent}inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
            } else {
                appendLine("${indent}joinColumns = {")
                meta.columnNamePairs.forEach {
                    appendLine("$indent${indent}@JoinColumn(name = \"${it.first}\", ${foreignKeyTypeProp ?: ""}),")
                }
                appendLine("$indent},")

                appendLine("${indent}inverseJoinColumns = {")
                meta.columnNamePairs.forEach {
                    appendLine("$indent${indent}@JoinColumn(name = \"${it.second}\", ${foreignKeyTypeProp ?: ""}),")
                }
                appendLine("$indent}")
            }

            append(")")
        }
}
