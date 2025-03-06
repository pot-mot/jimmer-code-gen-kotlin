package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.builder.AssociationAnnotationBuilder
import top.potmot.entity.sub.JoinTableMeta
import top.potmot.utils.string.buildScopeString

object JavaAssociationAnnotationBuilder : AssociationAnnotationBuilder("        ") {
    override fun build(meta: JoinTableMeta) =
        buildScopeString(indent) {
            scopeEndNoLine("@JoinTable(", ")") {
                line("name = \"${meta.tableName}\",")

                val foreignKeyTypeProp = createForeignKeyType(meta.foreignKeyType)

                if (meta.columnNamePairs.size == 1 && foreignKeyTypeProp == null) {
                    line("joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                    line("inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
                } else {
                    scope("joinColumns = {", "},") {
                        meta.columnNamePairs.forEach {
                            line("@JoinColumn(name = \"${it.first}\", ${foreignKeyTypeProp ?: ""}),")
                        }
                    }

                    scope("inverseJoinColumns = {", "}") {
                        meta.columnNamePairs.forEach {
                            line("@JoinColumn(name = \"${it.second}\", ${foreignKeyTypeProp ?: ""}),")
                        }
                    }
                }
            }
        }
}
