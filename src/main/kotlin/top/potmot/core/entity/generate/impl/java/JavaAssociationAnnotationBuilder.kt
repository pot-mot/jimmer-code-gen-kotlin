package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.builder.AssociationAnnotationBuilder
import top.potmot.core.entity.meta.JoinTableMeta
import top.potmot.utils.string.buildScopeString

object JavaAssociationAnnotationBuilder : AssociationAnnotationBuilder("        ") {
    override fun build(meta: JoinTableMeta) =
        buildScopeString(indent) {
            line("@JoinTable(")

            scope {
                line("name = \"${meta.tableName}\",")

                val foreignKeyTypeProp = createForeignKeyType(meta.foreignKeyType)

                if (meta.columnNamePairs.size == 1 && foreignKeyTypeProp == null) {
                    line("joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                    line("inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
                } else {
                    line("joinColumns = {")
                    scope {
                        meta.columnNamePairs.forEach {
                            line("@JoinColumn(name = \"${it.first}\", ${foreignKeyTypeProp ?: ""}),")
                        }
                    }
                    line("},")

                    line("inverseJoinColumns = {")
                    scope {
                        meta.columnNamePairs.forEach {
                            line("@JoinColumn(name = \"${it.second}\", ${foreignKeyTypeProp ?: ""}),")
                        }
                    }
                    line("}")
                }
            }

            append(")")
        }
}
