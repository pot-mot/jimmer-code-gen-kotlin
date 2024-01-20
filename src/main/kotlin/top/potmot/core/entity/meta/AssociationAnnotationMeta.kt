package top.potmot.core.entity.meta

import top.potmot.context.getContextGenConfig
import top.potmot.enumeration.AssociationType
import top.potmot.model.GenPropertyDraft

data class AssociationAnnotationMeta(
    val type: AssociationType,
    val mappedBy: String? = null,
    val joinColumns: List<JoinColumnMeta> = emptyList(),
    val joinTable: JoinTableMeta? = null,
) {
    fun toAnnotation() =
        buildString {
            append("@" + type.toAnnotation().simpleName)
            if (mappedBy.isNullOrBlank()) {
                if (getContextGenConfig().joinColumnAnnotation) {
                    joinColumns.forEach {
                        append("\n${it.toAnnotation()}")
                    }
                }

                if (getContextGenConfig().joinTableAnnotation) {
                    joinTable?.let {
                        append("\n${it.toAnnotation()}")
                    }
                }
            } else {
                append("(mappedBy = \"${mappedBy}\")")
            }
        }
}

fun GenPropertyDraft.setAssociation(
    meta: AssociationAnnotationMeta
) {
    this.associationType = meta.type
    this.associationAnnotation = meta.toAnnotation()
    meta.mappedBy?.takeIf { it.isNotBlank() }?.let {
        if (associationType == AssociationType.ONE_TO_ONE) {
            this.typeNotNull = false
        }
    }
}
