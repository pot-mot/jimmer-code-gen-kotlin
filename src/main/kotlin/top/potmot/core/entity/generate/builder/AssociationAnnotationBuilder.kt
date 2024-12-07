package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.context.getContextOrGlobal
import top.potmot.core.entity.meta.JoinColumnMeta
import top.potmot.core.entity.meta.JoinTableMeta
import top.potmot.utils.string.buildScopeString

open class AssociationAnnotationBuilder(
    val indent: String = "    ",
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
        buildScopeString(indent) {
            val onlyName = meta.referencedColumnName == null && meta.foreignKeyType == null

            if (onlyName) {
                append("@JoinColumn(name = \"${meta.joinColumnName}\")")
            } else {
                line("@JoinColumn(")
                scope {
                    append("name = \"${meta.joinColumnName}\"")

                    meta.referencedColumnName?.let {
                        append(",\n")
                        append("referencedColumnName = \"$it\"")
                    }

                    createForeignKeyType(meta.foreignKeyType)?.let {
                        append(",\n")
                        append(it)
                    }
                }
                line()
                append(")")
            }
        }

    open fun build(meta: JoinTableMeta) =
        buildScopeString(indent) {
            line("@JoinTable(")
            scope {
                line("name = \"${meta.tableName}\",")

                val foreignKeyTypeProp = createForeignKeyType(meta.foreignKeyType)

                if (meta.columnNamePairs.size == 1 && foreignKeyTypeProp == null) {
                    line("joinColumnName = \"${meta.columnNamePairs[0].first}\",")
                    line("inverseJoinColumnName = \"${meta.columnNamePairs[0].second}\"")
                } else {
                    line("joinColumns = [")
                    scope {
                        meta.columnNamePairs.forEach {
                            line("JoinColumn(name = \"${it.first}\", ${foreignKeyTypeProp ?: ""}),")
                        }
                    }
                    line("],")

                    line("inverseJoinColumns = [")
                    scope {
                        meta.columnNamePairs.forEach {
                            line("JoinColumn(name = \"${it.second}\", ${foreignKeyTypeProp ?: ""}),")
                        }
                    }
                    line("]")
                }
            }
            append(")")
        }
}
